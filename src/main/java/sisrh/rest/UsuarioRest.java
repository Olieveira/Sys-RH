package sisrh.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import sisrh.banco.Banco;
import sisrh.dto.Usuario;

@Api
@Path("usuario")
public class UsuarioRest {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTodos() throws Exception {
        List<Usuario> lista = Banco.listarUsuarios();
        GenericEntity<List<Usuario>> usuarios = new GenericEntity<List<Usuario>>(lista) {};
        return Response.ok().entity(usuarios).build();
    }

    @GET
    @Path("{matricula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response usuarioPorMatricula(@PathParam("matricula") String matricula) throws Exception {
        Usuario usuario = Banco.buscarUsuarioPorMatricula(matricula);

        try {
            if (usuario != null) {
                return Response.ok().entity(usuario).build();
            } else {
                return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Usuario nao encontrado!\" }").build();
            }
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("{ \"mensagem\" : \"Falha para obter usuario!\" , \"detalhe\" :  \"" + e.getMessage() + "\"  }")
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inserirUsuario(Usuario usuario) {
        try {
            Usuario novoUsuario = Banco.incluirUsuario(usuario);
            return Response.ok().entity(novoUsuario).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("{ \"mensagem\" : \"Falha na inclusao do usuario!\" , \"detalhe\" :  \"" + e.getMessage() + "\"  }")
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{matricula}")
    public Response alterarUsuario(@PathParam("matricula") String matricula, Usuario usuario) {
        try {
            if (Banco.buscarUsuarioPorMatricula(matricula) == null) {
                return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Usuario nao encontrado!\" }").build();
            }

            Usuario editado = Banco.alterarUsuario(matricula, usuario);
            return Response.ok().entity(editado).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("{ \"mensagem\" : \"Falha na alteracao do usuario!\" , \"detalhe\" :  \"" + e.getMessage() + "\"  }")
                    .build();
        }
    }

    @DELETE
    @Path("{matricula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerUsuario(@PathParam("matricula") String matricula) throws Exception {
        try {
            if (Banco.buscarUsuarioPorMatricula(matricula) == null) {
                return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Usuario nao encontrado!\" }").build();
            }

            Banco.excluirUsuario(matricula);
            return Response.ok().entity("{ \"mensagem\" : \"Usuario " + matricula + " excluido!\" }").build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("{ \"mensagem\" : \"Falha na exclusao do usuario!\" , \"detalhe\" :  \"" + e.getMessage() + "\"  }")
                    .build();
        }
    }
}
