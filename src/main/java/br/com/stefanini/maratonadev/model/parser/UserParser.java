package br.com.stefanini.maratonadev.model.parser;

import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import br.com.stefanini.maratonadev.dto.UserDTO;
import br.com.stefanini.maratonadev.model.User;
import br.com.stefanini.maratonadev.model.dominio.EnumPerfil;

public class UserParser extends BaseParser<User, UserDTO> {

	public static UserParser get() {
		return new UserParser();
	}
	
	@Override
	public User toEntity(UserDTO dto) {
		User entidade = new User();
		
		if (StringUtils.isNotBlank(dto.getId())) {
			entidade.setId(UUID.fromString(dto.getId()));			
		}
		entidade.setNome(dto.getNome());
		if (Objects.nonNull(dto.getIdRole())) {
			entidade.setPermissao(EnumPerfil.valueOfId(dto.getIdRole()).getRole());			
		} else if (StringUtils.isNotBlank(dto.getPermissao())) {
			entidade.setPermissao(dto.getPermissao());
		}
		entidade.setEmail(dto.getEmail());
		entidade.setSenha(dto.getSenha());
		entidade.setDataCriacao(dto.getDataCriacao());
		
		return entidade;
	}
	
	@Override
	public UserDTO toDTO(User entidade) {
		UserDTO dto = new UserDTO();
		
		if (Objects.nonNull(entidade.getId())) {
			dto.setId(entidade.getId().toString());
		}
		dto.setNome(entidade.getNome());
		dto.setPermissao(entidade.getPermissao());
		dto.setEmail(entidade.getEmail());
		dto.setSenha(entidade.getSenha());
		dto.setDataCriacao(entidade.getDataCriacao());
		if (StringUtils.isNotBlank(entidade.getPermissao())) {
			dto.setIdRole(EnumPerfil.valueOf(entidade.getPermissao()).getId());			
		}
		
		return dto;
	}
	
}
