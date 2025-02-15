package Services;

import java.util.List;

import Gestion.GestionContratos;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.edu.ec.demo65.Contrato;

@Path("/contratos")
public class ContratoService {
	
	@Inject
	private GestionContratos gestionContrato;
	
	@POST 
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createContrato(Contrato contrato) { 
		try {
			gestionContrato.agregarContrato(contrato);
			return Response.ok(contrato).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@PUT
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateContrato(Contrato contrato) {
		try {
			gestionContrato.modificarContrato(contrato);
			return Response.ok(contrato).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@GET
	@Produces("application/json")
	public Response listContratos(){
		try {
			List<Contrato> listContrato = gestionContrato.listHorario();
			return Response.ok(listContrato).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@GET
	@Path("/contrato/{id}")
	@Produces("application/json")
	public Response buscarContratoIdPersona(@PathParam("id") int id){
		try {
			List<Contrato> listContrato = gestionContrato.buscarContratosIdPersona(id);
			return Response.ok(listContrato).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContrato(@PathParam("id") int id) {
		try {
			Contrato contrato = gestionContrato.readHorario(id);
			return Response.ok(contrato).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@DELETE
	@Path("/deletePersona/{id}")
	public Response deleteContrato(@PathParam("id") int id) {
		try {
			gestionContrato.eliminarContrato(id);
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
}