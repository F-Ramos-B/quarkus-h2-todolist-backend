package br.com.stefanini.maratonadev.model.parser;

import br.com.stefanini.maratonadev.dto.TodoStatusDTO;
import br.com.stefanini.maratonadev.model.TodoStatus;

public class TodoStatusParser {

	public static TodoStatusParser get() {
		return new TodoStatusParser();
	}
	
	public TodoStatusDTO dto(TodoStatus entidade) {
		TodoStatusDTO dto = new TodoStatusDTO();
		dto.setId(entidade.getId());
		dto.setData(entidade.getData());
		dto.setStatus(entidade.getStatus().name());
		dto.setNomeUsuario(entidade.getUser().getNome());
		
		return dto;
	}
	
	//metodo que retorna uma entidade com base no DTO
}
