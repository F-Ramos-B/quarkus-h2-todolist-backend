package br.com.stefanini.maratonadev.model.parser;

import br.com.stefanini.maratonadev.dto.TodoStatusDTO;
import br.com.stefanini.maratonadev.model.TodoStatus;

public class TodoStatusParser extends BaseParser<TodoStatus, TodoStatusDTO> {

	public static TodoStatusParser get() {
		return new TodoStatusParser();
	}
	
	@Override
	public TodoStatusDTO toDTO(TodoStatus entidade) {
		TodoStatusDTO dto = new TodoStatusDTO();
		dto.setId(entidade.getId());
		dto.setData(entidade.getData());
		dto.setStatus(entidade.getStatus().name());
		dto.setNomeUsuario(entidade.getUser().getNome());
		
		return dto;
	}

	@Override
	public TodoStatus toEntity(TodoStatusDTO dto) {
		TodoStatus entidade = new TodoStatus();
		dto.setId(entidade.getId());
		dto.setData(entidade.getData());
		dto.setStatus(entidade.getStatus().name());
		dto.setNomeUsuario(entidade.getUser().getNome());
		return entidade;
	}
	
}
