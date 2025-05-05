package sisrh.rest;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import sisrh.dto.Login;

@Api
@Path("loginunico")
public class LoginUnicoRest {

	@POST
	@Path("jwt")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response gerarToken(Login login) {
		try {
			String token = LoginUnico.geraToken(login.getUsuario(), login.getSenha());
			if (token != null) {
				return Response.ok().entity(token).build();
			} else {
				return Response.status(Status.FORBIDDEN).entity(Map.of("mensagem", "Usuário ou senha inválido!"))
						.build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(Map.of("mensagem", "Falha para gerar token JWT!", "detalhe", e.getMessage())).build();
		}
	}

}
