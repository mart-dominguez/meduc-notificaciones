package ar.gov.santafe.meduc.services.notificaciones;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import modelo.administracion.Usuario;
import modelo.notificaciones.NTConfiguracion;
import modelo.notificaciones.NTEstado;
import modelo.notificaciones.NTMensaje;
import modelo.notificaciones.NTMensajeXUsuario;
import modelo.notificaciones.NTTipo;
import modelo.personas.Persona;
import ar.gov.santafe.meduc.services.notificaciones.util.Queries;
import dao.GenericDao;



@Stateless
public class NotificacionesRest implements Notificaciones {

	@Inject	private GenericDao<Usuario> usuarioDao ;
	@Inject	private GenericDao<NTMensaje> mensajeDao ;
	@Inject private GenericDao<NTTipo> tipoDao ;
	@Inject private GenericDao<NTMensajeXUsuario> msgUsuDao ;
	@Inject private GenericDao<NTEstado> estadoDao ;
	@Inject private GenericDao<NTConfiguracion> configuracionDao ;
	
	@Inject
	private Queries queries ;
	
	@Override
	public NTMensaje getMensaje(Long id) {
		NTMensaje mensaje = mensajeDao.find(id);
		return mensaje;
	}
	
	
	
	@Override
	public NTMensaje putMensaje(String usuario, Long idConfiguracion,MultivaluedMap<String, String>  parametros) {

		String jpql = "select u from Usuario u where usuario=:p_usuario";
		Map<String,Object> p = new HashMap<String, Object>();
		p.put("p_usuario", usuario);
		
		List<Usuario> usuarios = usuarioDao.query(jpql, p); 
		Usuario usu= usuarios.get(0);
		
		    NTConfiguracion conf = configuracionDao.find(idConfiguracion);
			
			String mensaje = conf.getMensaje();
			
			for (String key:parametros.keySet()){
				String value = parametros.getFirst(key);
				
				mensaje = mensaje.replaceAll("<"+key+">", value);
			}
			
			NTMensaje msg = new NTMensaje();
			msg.setMensaje(mensaje);
			msg.setConfiguracion(conf);
			
			msg = mensajeDao.create(msg,usuario);
			
			asignarUsuarios(msg,usu);
			
			return msg;
	}
	
		
	private void asignarUsuarios(NTMensaje msg,Usuario usuarioAlta) {
		//todo: cambiar para que se asignen segun los roles y el area de gestion
		//ahora, de prueba inserta el usuario que lo dio de alta
		NTMensajeXUsuario msgxusu = new NTMensajeXUsuario();
		msgxusu.setMensaje(msg);
		msgxusu.setUsuario(usuarioAlta);
		
		msgUsuDao.create(msgxusu);
		
	}



	@Override
	public List<NTMensaje> listarMensajes(String usuario) {
		List<NTTipo> tipos = tipoDao.list();
		List<NTMensaje> msg = this.listarMensajes(usuario,tipos);
		return msg;
	}


	@Override
	public List<NTMensaje> listarAvisos(String usuario) {
		List<NTTipo> tipos = new ArrayList<NTTipo>();
		NTTipo aviso = tipoDao.find(1l);
		tipos.add(aviso);
		List<NTMensaje> msg = this.listarMensajes(usuario,tipos);
		return msg;
	}

	


	@Override
	public List<NTMensaje> listarAlertas(String usuario) {
		List<NTTipo> tipos = new ArrayList<NTTipo>();
		NTTipo alerta = tipoDao.find(2l);
		tipos.add(alerta);
		List<NTMensaje> msg = this.listarMensajes(usuario,tipos);
		return msg;
	}


	@Override
	public Long totalPendientes(String usuario) {
		String sql = queries.get("mensajesPendientes");
		Map<String, Object> params = queries.paramMap();
		params.put("P_USUARIO", usuario);
		
		BigDecimal total = (BigDecimal)mensajeDao.nativeSingleResult(sql, params);

		return total.longValue(); 
		
	}


	@Override
	public NTMensaje  leer(Long id, String usuario) {
		NTEstado estado = estadoDao.find(1l);//leida
		NTMensaje mensaje = this.modificarEstado(id, usuario, estado);
		return mensaje;
	}


	@Override
	public NTMensaje  aprobar(Long id, String usuario) {
		NTEstado estado = estadoDao.find(3l);//aceptada
		NTMensaje mensaje = this.modificarEstado(id, usuario, estado);
		return mensaje;
	}


	@Override
	public NTMensaje  rechazar(Long id, String usuario) {
		NTEstado estado = estadoDao.find(4l);//rechazada
		NTMensaje mensaje = this.modificarEstado(id, usuario, estado);
		return mensaje;
		
	}


	@Override
	public NTMensaje  eliminar(Long id, String usuario) {
		NTEstado estado = estadoDao.find(2l);//eliminada
		NTMensaje mensaje = this.modificarEstado(id, usuario, estado);
		return mensaje;
	}
	
	/*----------------------------------------------------------------------------*/
	
	private List<NTMensaje> listarMensajes(String usuario,List<NTTipo> tipos) {
		String jpql = queries.get("mensajesXUsuario");
		Map<String, Object> params = queries.paramMap();
		params.put("p_usuario", usuario);
		params.put("pTipos", tipos);
		
		List<NTMensaje> msg = mensajeDao.query(jpql, params);
		return msg;
	}


	private NTMensaje  modificarEstado(Long id, String usuario,NTEstado estado) {
		String jpql = queries.get("mensajesXUsuarioDetalle");
		Map<String, Object> params = queries.paramMap();
		
		NTMensaje mensaje = mensajeDao.find(id);
		params.put("p_mensaje", mensaje);
		params.put("p_usuario", usuario);
		
		List<NTMensajeXUsuario> msgxusu = msgUsuDao.query(jpql , params);
		
		for (NTMensajeXUsuario mu:msgxusu){
			mu.setEstado(estado);
			mu.setFechaCambioEstado (new Date());
			msgUsuDao.update(mu);
		}
		return mensaje;
	}

	
}