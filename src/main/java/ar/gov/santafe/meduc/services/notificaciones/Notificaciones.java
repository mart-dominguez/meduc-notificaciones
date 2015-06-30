package ar.gov.santafe.meduc.services.notificaciones;


import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import modelo.administracion.Usuario;
import modelo.notificaciones.NTMensaje;


@Path("/mensajes")
public interface Notificaciones {

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public NTMensaje getMensaje(@PathParam(value="id") Long id);
	
	@POST
	@Path("/nuevo/{usuario}/{idConfiguracion}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	public NTMensaje putMensaje(
			                    @PathParam(value="usuario") String usuario,
			                    @PathParam(value="idConfiguracion") Long idConfiguracion,
			                    MultivaluedMap<String, String> parametros
			                    );


	@GET
	@Path("/listar/{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<NTMensaje> listarMensajes(@PathParam(value="usuario") String usuario);

	
	@GET
	@Path("/listar/avisos/{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<NTMensaje> listarAvisos(@PathParam(value="usuario") String usuario);

	
	
	@GET
	@Path("/listar/alertas/{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<NTMensaje> listarAlertas(@PathParam(value="usuario") String usuario);
	
	
	@GET
	@Path("/totalPendientes/{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Long totalPendientes(@PathParam(value="usuario") String usuario);


	@GET
	@Path("/leer/{id}/{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public NTMensaje  leer(@PathParam(value="id") Long id,@PathParam(value="usuario") String usuario);

	@GET
	@Path("/aprobar/{id}/{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public NTMensaje  aprobar(@PathParam(value="id") Long id,@PathParam(value="usuario") String usuario);

	@GET
	@Path("/rechazar/{id}/{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public NTMensaje  rechazar(@PathParam(value="id") Long id,@PathParam(value="usuario") String usuario);

	@GET
	@Path("/eliminar/{id}/{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public NTMensaje  eliminar(@PathParam(value="id") Long id,@PathParam(value="usuario") String usuario);

				
	
	
}
