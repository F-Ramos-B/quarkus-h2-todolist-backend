package br.com.stefanini.maratonadev.rest;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import br.com.stefanini.maratonadev.dto.FiltroPaginacaoDTO;
import br.com.stefanini.maratonadev.dto.ResultadoPaginadoDTO;
import br.com.stefanini.maratonadev.dto.PermissaoDTO;
import br.com.stefanini.maratonadev.dto.UserDTO;
import br.com.stefanini.maratonadev.model.dominio.EnumPerfil;
import br.com.stefanini.maratonadev.service.UserService;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ EnumPerfil.Roles.ADMIN, EnumPerfil.Roles.USER })
public class UserRest {

	@Inject
	UserService userService;

	@GET
	@Operation(summary = "Listar usuarios", description = "Listar usuarios")
	@APIResponse(responseCode = "200", description = "Usuarios", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class, type = SchemaType.OBJECT)) })
	@PermitAll
	public Response listarUsuarios() {
		return Response.status(Response.Status.OK).entity(userService.buscarTodos()).build();
	}
	
	@GET
	@Path("/roles")
	@Operation(summary = "Listar roles", description = "Listar roles")
	@APIResponse(responseCode = "200", description = "Roles", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PermissaoDTO.class, type = SchemaType.OBJECT)) })
	@PermitAll
	public Response listarPermissoes() {
		return Response.status(Response.Status.OK).entity(userService.listarPermissoes()).build();
	}
	
	@GET
	@Path("/paginado")
	@Operation(summary = "Listar roles", description = "Listar roles")
	@APIResponse(responseCode = "200", description = "Roles", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResultadoPaginadoDTO.class, type = SchemaType.ARRAY)) })
	public Response consultarPaginado(@BeanParam FiltroPaginacaoDTO filtro) {
		return Response.status(Response.Status.OK).entity(userService.consultarPaginado(filtro)).build();
	}
	
	@GET
	@Path("/{id}")
	@Operation(summary = "Listar por id", description = "Listar por id")
	@APIResponse(responseCode = "200", description = "Usuarios", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class, type = SchemaType.OBJECT)) })
	public Response listarPorId(@PathParam("id") String id) {
		return Response.status(Response.Status.OK).entity(userService.buscarPorId(id)).build();
	}

	@POST
	@Operation(summary = "Criar usuario", description = "Criar usuario")
	@APIResponse(responseCode = "201", description = "Usuarios", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class, type = SchemaType.ARRAY)) })
	@RolesAllowed({ EnumPerfil.Roles.ADMIN })
	public Response criarUsuario(UserDTO usuario) {
		return Response.status(Response.Status.CREATED).entity(userService.criarUsuario(usuario)).build();
	}
	
	@PUT
	@Operation(summary = "Alterar usuario", description = "Alterar usuario")
	@APIResponse(responseCode = "200", description = "Usuarios", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class, type = SchemaType.ARRAY)) })
	@RolesAllowed({ EnumPerfil.Roles.ADMIN })
	public Response alterarUsuario(UserDTO usuario) {
		return Response.status(Response.Status.CREATED).entity(userService.alterarUsuario(usuario)).build();
	}

	@DELETE
	@Path("/{id}")
	@Operation(summary = "Remover usuario", description = "Remover usuario")
	@APIResponse(responseCode = "200", description = "Usuarios", content = { @Content(mediaType = "application/json") })
	@RolesAllowed({ EnumPerfil.Roles.ADMIN })
	public Response removerUsuario(@PathParam("id") String id) {
		userService.removerUsuario(id);
		return Response.status(Response.Status.OK).build();
	}

}
