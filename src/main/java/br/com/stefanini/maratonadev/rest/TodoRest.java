package br.com.stefanini.maratonadev.rest;

import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import br.com.stefanini.maratonadev.dto.TodoDTO;
import br.com.stefanini.maratonadev.dto.TodoGroupDTO;
import br.com.stefanini.maratonadev.model.dominio.EnumPerfil;
import br.com.stefanini.maratonadev.service.TodoService;

@Path("todo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ EnumPerfil.Roles.ADMIN, EnumPerfil.Roles.USER })
public class TodoRest {

	@Inject
	TodoService service;

	@Inject
	Validator validator;

	@GET
	@Operation(summary = "Listar tarefas a fazer", description = "Retorna uma lista de Todo.class")
	@APIResponse(responseCode = "200", description = "lista de tarefas", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class, type = SchemaType.ARRAY)) })
	@PermitAll
	public Response listar() {
		return Response.status(Response.Status.OK).entity(service.listar()).build();
	}
	
	@GET
	@Path("/agrupado")
	@Operation(summary = "Buscar agrupado por status", description = "Buscar agrupado por status")
	@APIResponse(responseCode = "200", description = "tarefa", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TodoGroupDTO.class, type = SchemaType.OBJECT)) })
	@PermitAll
	public Response buscarAgrupado() {
		return Response.status(Response.Status.OK).entity(service.listarAgrupado()).build();
	}

	@POST
	@Operation(summary = "Incluir uma tarefa", description = "Incluir uma tarefa")
	@APIResponse(responseCode = "201", description = "tarefa", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class)) })
	public Response incluir(TodoDTO todo, @Context SecurityContext securityContext) {

		Set<ConstraintViolation<TodoDTO>> erros = validator.validate(todo);
		
		if (erros.isEmpty()) {
			service.inserir(todo, securityContext.getUserPrincipal().getName());
		} else {
			throw new NotFoundException(erros.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", ")).toString());
		}

		return Response.status(Response.Status.CREATED).build();
	}

	@DELETE
	@Path("/{id}")
	@Operation(summary = "Excluir uma tarefa", description = "Excluir uma tarefa")
	@APIResponse(responseCode = "202", description = "tarefa", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class)) })
	@RolesAllowed({ EnumPerfil.Roles.ADMIN })
	public Response excluir(@PathParam("id") Long id) {
		service.excluir(id);
		return Response.status(Response.Status.ACCEPTED).build();
	}

	@GET
	@Path("/{id}")
	@Operation(summary = "Buscar uma tarefa por ID", description = "Buscar uma tarefa por ID")
	@APIResponse(responseCode = "200", description = "tarefa", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class)) })
	public Response buscarPorID(@PathParam("id") Long id) {
		return Response.status(Response.Status.OK).entity(service.buscar(id)).build();
	}
	
	@PUT
	@Operation(summary = "Editar uma tarefa com base no ID", description = "Editar uma tarefa com base no ID")
	@APIResponse(responseCode = "200", description = "tarefa", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class)) })
	public Response atualizar(TodoDTO todo, @Context SecurityContext securityContext) {
		service.atualizar(todo, securityContext.getUserPrincipal().getName());
		return Response.status(Response.Status.OK).build();
	}
	
	@PUT
	@Path("/{id}/{idStatus}")
	@Operation(summary = "Editar status", description = "Editar uma tarefa com base no ID")
	@APIResponse(responseCode = "200", description = "tarefa", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class)) })
	public Response atualizar(@PathParam("id") Long id, @PathParam("idStatus") Long idStatus, @Context SecurityContext securityContext) {
		service.atualizarStatus(id, idStatus, securityContext.getUserPrincipal().getName());
		return Response.status(Response.Status.OK).build();
	}
}
