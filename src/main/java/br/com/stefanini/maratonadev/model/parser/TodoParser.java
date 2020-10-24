package br.com.stefanini.maratonadev.model.parser;

import br.com.stefanini.maratonadev.dto.TodoDTO;
import br.com.stefanini.maratonadev.model.Todo;

public class TodoParser {

	public static TodoParser get() {
		return new TodoParser();
	}
	public Todo entidade(TodoDTO dto) {
		Todo entidade = new Todo();
		
		entidade.setId(dto.getId());
		entidade.setNome(dto.getNome());
		entidade.setDataCriacao(dto.getDataCriacao());
		
		return entidade;
	}
	
	public TodoDTO dto(Todo entidade) {
		
		TodoDTO dto = new TodoDTO();
		
		dto.setId(entidade.getId());
		dto.setNome(entidade.getNome());
		dto.setDataCriacao(entidade.getDataCriacao());
		
		dto.setStatus(entidade.getStatus()
				.get(0).toString());
		
		return dto;
	}
}
