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
import sisrh.dto.Solicitacao;

@Api
@Path("solicitacao")
public class SolicitacaoRest {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarTodas() throws Exception {
		try {
			List<Solicitacao> lista = Banco.listarSolicitacoes();
			GenericEntity<List<Solicitacao>> solicitacoes = new GenericEntity<List<Solicitacao>>(lista) {
			};

			return Response.ok().entity(solicitacoes).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha para obter solicitações!\" , \"detalhe\" :  \"" + e.getMessage()
							+ "\"  }")
					.build();
		}
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response solicitacaoById(@PathParam("id") String id) throws Exception {

		Solicitacao solicitacao = Banco.buscarSolicitacaoPorId(Integer.parseInt(id));

		try {

			if (solicitacao == null) {
				return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Solicitação não encontrada!\" }")
						.build();
			}

			return Response.ok().entity(solicitacao).build();

		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(
					"{ \"mensagem\" : \"Falha ao obter solicitação!\" , \"detalhe\" :  \"" + e.getMessage() + "\"  }")
					.build();
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserirSolicitacao(Solicitacao solicitacao) throws Exception {
		try {
			Solicitacao novaSolicitacao = Banco.incluirSolicitacao(solicitacao);
			return Response.ok().entity(novaSolicitacao).build();

		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(
					"{ \"mensagem\" : \"Falha ao criar solicitação!\" , \"detalhe\" :  \"" + e.getMessage() + "\"  }")
					.build();
		}
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editarSolicitacao(@PathParam("id") String id, Solicitacao solicitacao) throws Exception {

		if (Banco.buscarSolicitacaoPorId(Integer.parseInt(id)) == null) {
			return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Solicitação não encontrada!\" }")
					.build();
		}

		try {
			Solicitacao alterado = Banco.alterarSolicitacao(Integer.parseInt(id), solicitacao);
			return Response.ok().entity(alterado).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(
					"{ \"mensagem\" : \"Falha ao alterar solicitação!\" , \"detalhe\" :  \"" + e.getMessage() + "\"  }")
					.build();
		}
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirSolicitacao(@PathParam("id") String id) throws Exception {

		if (Banco.buscarSolicitacaoPorId(Integer.parseInt(id)) == null) {
			return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Solicitação não encontrada!\" }")
					.build();
		}

		try {
			Banco.excluirSolicitacao(Integer.parseInt(id));
			return Response.ok().entity("{ \"mensagem\" : \"Solicitação " + id + " excluida!\" }").build();
		
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na exclusao da solicitação!\" , \"detalhe\" :  \""
							+ e.getMessage() + "\"  }")
					.build();
		}
	}

}