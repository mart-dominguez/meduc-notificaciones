package ar.gov.santafe.meduc.services.notificaciones;

import javax.ejb.Stateless;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;


@Path("/inasistencias")
@Stateless
public class NotificacionesRest {

	
	@Inject
	private Gson gsonObj ;
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getXXXX(@PathParam(value="id") Long id){
		return Response.ok().build();
	}
	
}