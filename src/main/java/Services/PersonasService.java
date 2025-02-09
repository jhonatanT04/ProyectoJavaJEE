package Services;

import java.util.List;

import Gestion.GestionPersonas;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.edu.ec.demo65.Persona;

@Path("/personas")
public class PersonasService {

    @Inject
    private GestionPersonas gPersonas;

    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Persona persona) {
        try {
            gPersonas.agregarPersona(persona);
            return Response.ok(persona).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Ocurrió un error inesperado")).build();
        }
    }

    @PUT
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Persona persona) {
        try {
            if (persona != null && persona.getCedula() != null) {
                gPersonas.actualizarPersona(persona);
                return Response.ok(new Respuesta(Respuesta.OK, "Persona actualizada con éxito")).build();
            } else {
                return Response.status(400).entity(new Respuesta(Respuesta.ERROR, "Datos inválidos para la actualización")).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al actualizar la persona")).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response read(@PathParam("id") int id) {
        try {
            Persona persona = gPersonas.buscarPersona(id);
            if (persona == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(persona).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al buscar la persona")).build();
        }
    }
    
    @GET
    @Path("/buscarid/{email}")
    @Produces("application/json")
    public Response readEmail(@PathParam("email") String email) {
        try {
            Persona persona = gPersonas.buscarPersonaEmail(email);
            if (persona == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(persona).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al buscar la persona")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            gPersonas.eliminarPersona(id);
            return Response.ok(new Respuesta(Respuesta.OK, "Persona eliminada con éxito")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al eliminar la persona")).build();
        }
    }
    
    @GET
    @Produces("application/json")
    public Response listaPersonas() {
    	try {
			List<Persona> listaPersonas = gPersonas.listarPersonas();
			return Response.ok(listaPersonas).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).build();
		}
    }
    
    @GET
    @Path("/getPersona/{email}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response readEmail(@HeaderParam("Authorization") String authHeader, @PathParam("email") String email) {
        try {
            System.out.println("Iniciando búsqueda de persona...");

            
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Token no proporcionado").build();
            }

            
            String token = authHeader.substring("Bearer".length()).trim();
            String secretKey = System.getenv("JWT_SECRET_KEY"); 
            
            
            Claims claims;
            try {
                claims = Jwts.parser()
                		.setSigningKey(Keys.hmacShaKeyFor("mi_clave_secreta_que_tiene_256_bits!!!!!".getBytes()))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
            } catch (ExpiredJwtException e) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Token expirado").build();
            } catch (Exception e) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Error al validar el token").build();
            }

            
            String emailFromToken = claims.getSubject();  
            if (!emailFromToken.equals(email)) {
                return Response.status(Response.Status.FORBIDDEN).entity("Acceso denegado").build();
            }

            
            Persona persona = gPersonas.buscarPersonaEmail(email);
            if (persona == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Persona no encontrada").build();
            }

            return Response.ok(persona).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Respuesta(Respuesta.ERROR, "Error al buscar la persona"))
                    .build();
        }
    }
}
