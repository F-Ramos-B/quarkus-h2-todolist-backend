package br.com.stefanini.maratonadev.dao;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;

import br.com.stefanini.maratonadev.dto.FiltroPaginacaoDTO;
import br.com.stefanini.maratonadev.dto.PaginadoDTO;
import br.com.stefanini.maratonadev.dto.UserDTO;
import br.com.stefanini.maratonadev.model.User;
import br.com.stefanini.maratonadev.model.parser.UserParser;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

@RequestScoped
public class UserDAO {
	
	public User buscarUsuarioPorEmail(String email) {
		return User.find("email", email).firstResult();
	}
	
	public User buscarUsuarioPorId(UUID id) {	
		return User.findById(id);
	}
	
	public List<User> persist(User user) {
		User.persist(user);
		return this.consultarTudo();
	}
	
	public List<User> consultarTudo() {
		return User.listAll();
	}
	
	public PaginadoDTO<UserDTO> consultarPaginado(FiltroPaginacaoDTO filtro) {
		PanacheQuery<User> usuarios = User.findAll();
		
		List<User> usuariosPaginados = usuarios.page(Page.of(filtro.getPageNumber(), filtro.getPageSize())).list();
		
		return new PaginadoDTO<>(usuarios.count(), UserParser.get().toDTOList(usuariosPaginados));
	}

	public void removerUsuario(UUID id) {
		User.deleteById(id);
	}
}
