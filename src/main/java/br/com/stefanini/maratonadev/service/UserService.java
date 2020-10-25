package br.com.stefanini.maratonadev.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.stefanini.maratonadev.dao.UserDAO;
import br.com.stefanini.maratonadev.dto.FiltroPaginacaoDTO;
import br.com.stefanini.maratonadev.dto.ResultadoPaginadoDTO;
import br.com.stefanini.maratonadev.dto.PermissaoDTO;
import br.com.stefanini.maratonadev.dto.UserDTO;
import br.com.stefanini.maratonadev.model.User;
import br.com.stefanini.maratonadev.model.dominio.EnumPerfil;
import br.com.stefanini.maratonadev.model.parser.UserParser;

@RequestScoped
public class UserService {
	
	@Inject
	UserDAO userDAO;
	
	public List<UserDTO> buscarTodos() {
		return UserParser.get().toDTOList(userDAO.consultarTudo());
	}
	
	public ResultadoPaginadoDTO<UserDTO> consultarPaginado(FiltroPaginacaoDTO filtro) {
		return userDAO.consultarPaginado(filtro);
	}
	
	public UserDTO buscarPorId(String id) {
		return UserParser.get().toDTO(userDAO.buscarUsuarioPorId(UUID.fromString(id)));
	}
	
	public User buscarUsuarioPorEmail(String email) {
		return userDAO.buscarUsuarioPorEmail(email);
	}

	@Transactional(rollbackOn = Exception.class)
	public List<UserDTO> criarUsuario(UserDTO usuario) {
		List<User> usuarios = userDAO.persist(UserParser.get().toEntity(usuario));
		return UserParser.get().toDTOList(usuarios);
	}
	
	@Transactional(rollbackOn = Exception.class)
	public UserDTO alterarUsuario(UserDTO usuario) {
		User entidade = userDAO.buscarUsuarioPorId(UUID.fromString(usuario.getId()));
		entidade.setSenha(usuario.getSenha());
		entidade.setEmail(usuario.getEmail());
		entidade.setNome(usuario.getNome());
		entidade.setPermissao(usuario.getPermissao());
		
		userDAO.persist(entidade);
		
		return UserParser.get().toDTO(entidade);
	}

	@Transactional(rollbackOn = Exception.class)
	public void removerUsuario(String id) {
		userDAO.removerUsuario(UUID.fromString(id));
	}

	public List<PermissaoDTO> listarPermissoes() {
		return Stream.of(EnumPerfil.values()).map(PermissaoDTO::new).collect(Collectors.toList());
	}
	
}
