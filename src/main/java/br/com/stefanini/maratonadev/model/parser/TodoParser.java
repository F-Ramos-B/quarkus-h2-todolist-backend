package br.com.stefanini.maratonadev.model.parser;

import br.com.stefanini.maratonadev.dto.TodoDTO;
import br.com.stefanini.maratonadev.model.Todo;
import br.com.stefanini.maratonadev.model.dominio.EnumStatus;

public class TodoParser extends BaseParser<Todo, TodoDTO> {

	public static TodoParser get() {
		return new TodoParser();
	}

	@Override
	public Todo toEntity(TodoDTO dto) {
		Todo entidade = new Todo();

		entidade.setId(dto.getId());
		entidade.setNome(dto.getNome());
		entidade.setDataCriacao(dto.getDataCriacao());

		return entidade;
	}

	@Override
	public TodoDTO toDTO(Todo entidade) {
		TodoDTO dto = new TodoDTO();

		dto.setId(entidade.getId());
		dto.setNome(entidade.getNome());
		dto.setDataCriacao(entidade.getDataCriacao());
		
		EnumStatus status = entidade.getStatus().stream()
					.sorted((s1, s2) -> s2.getData().compareTo(s1.getData()))
					.findFirst()
					.get()
					.getStatus();
		
		dto.setStatus(status.getTitulo());
		dto.setIdStatus(status.getId());
		
		return dto;
	}
}
