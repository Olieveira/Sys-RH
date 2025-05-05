package sisrh.rest;

import java.util.List;
import java.util.Optional;

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
import sisrh.dto.Empregado;

@Api
@Path("empregado")
public class EmpregadoRest {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarTodos() throws Exception {
		List<Empregado> lista = Banco.listarEmpregados();
		GenericEntity<List<Empregado>> empregados = new GenericEntity<List<Empregado>>(lista) {
		};
		return Response.ok().entity(empregados).build();
	}

	@GET
	@Path("{matricula}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response empregadoPorMatricula(@PathParam("matricula") String matricula) throws Exception {
		Empregado empregado = Banco.buscarEmpregadoPorMatricula(matricula);

		try {
			if (empregado != null) {
				return Response.ok().entity(empregado).build();
			} else {
				return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Empregado nao encontrado!\" }")
						.build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(
					"{ \"mensagem\" : \"Falha para obter empregado!\" , \"detalhe\" :  \"" + e.getMessage() + "\"  }")
					.build();
		}
	}

	@GET
	@Path("ativos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarAtivos() throws Exception {
		List<Empregado> lista = Banco.listarEmpregados(Optional.of(false));
		GenericEntity<List<Empregado>> empregados = new GenericEntity<List<Empregado>>(lista) {
		};
		return Response.ok().entity(empregados).build();
	}

	@GET
	@Path("inativos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarInativos() throws Exception {
		List<Empregado> lista = Banco.listarEmpregados(Optional.of(true));
		GenericEntity<List<Empregado>> empregados = new GenericEntity<List<Empregado>>(lista) {
		};
		return Response.ok().entity(empregados).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserirEmpregado(Empregado empregado) {

		try {
			Empregado novoEmpregado = Banco.incluirEmpregado(empregado);
			return Response.ok().entity(novoEmpregado).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na inclusao do empregado!\" , \"detalhe\" :  \"" + e.getMessage()
							+ "\"  }")
					.build();
		}

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{matricula}")
	public Response alterarEmpregado(@PathParam("matricula") String matricula, Empregado empregado) {
		try {

			if (Banco.buscarEmpregadoPorMatricula(matricula) == null) {
				return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Empregado nao encontrado!\" }")
						.build();
			}

			Empregado editado = Banco.alterarEmpregado(matricula, empregado);
			return Response.ok().entity(editado).build();

		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na alteracao do empregado!\" , \"detalhe\" :  \"" + e.getMessage()
							+ "\"  }")
					.build();
		}
	}

	@DELETE
	@Path("{matricula}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removerEmpregado(@PathParam("matricula") String matricula) throws Exception {
		try {
			if (Banco.buscarEmpregadoPorMatricula(matricula) == null) {
				return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Empregado nao encontrado!\" }")
						.build();
			}

			Banco.excluirEmpregado(matricula);

			return Response.ok().entity("{ \"mensagem\" : \"Empregado " + matricula + " excluido!\" }").build();

		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na exclusao do empregado!\" , \"detalhe\" :  \"" + e.getMessage()
							+ "\"  }")
					.build();

		}
	}

}
