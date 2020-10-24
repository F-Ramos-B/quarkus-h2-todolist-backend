package br.com.stefanini.maratonadev.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.opentracing.Traced;

import br.com.stefanini.maratonadev.dao.TodoDAO;
import br.com.stefanini.maratonadev.dto.TodoDTO;
import br.com.stefanini.maratonadev.model.Todo;
import br.com.stefanini.maratonadev.model.dominio.EnumStatus;
import br.com.stefanini.maratonadev.model.parser.TodoParser;

@RequestScoped
@Traced
public class TodoService {

	@Inject
	TodoDAO dao;

	@Inject
	TodoStatusService statusService;

	@Inject
	UserService userService;

	private void validar(Todo todo) {
		// validar regra de negocio

		if (dao.isnomeRepetido(todo.getNome())) {
			throw new NotFoundException();
		}
//		Agora o Hibernate Validator cuida disso
//		if(todo.getNome() == null) {
//			throw new NotFoundException();
//		}
	}

	@Transactional(rollbackOn = Exception.class)
	/**
	 * regra de criação Toda tarefa criada vem por padrão na lista TODO e com a data
	 * corrente
	 */
	public void inserir(@Valid TodoDTO todoDto, String emailLogado) {
		// validação
		Todo todo = TodoParser.get().entidade(todoDto);
		validar(todo);

		/**
		 * logica abaixo substituida por
		 * 
		 * @CreationTimestamp no modelo
		 */
		// todo.setDataCriacao(LocalDateTime.now());
		// chamada da dao

		Long id = dao.inserir(todo);

		statusService.inserir(id, EnumStatus.TODO, emailLogado);
	}

	public List<TodoDTO> listar() {
		return dao.listar().stream().map(TodoParser.get()::dto).collect(Collectors.toList());
	}

	public void excluir(Long id) {
		// DESAFIO: VALIDAR SE ID é valido
		if (dao.buscarPorId(id) == null) {
			throw new NotFoundException();
		}
		dao.excluir(id);
	}

	public TodoDTO buscar(Long id) {
		return TodoParser.get().dto(buscarPorId(id));
	}

	@Transactional(rollbackOn = Exception.class)
	public void atualizar(Long id, TodoDTO dto, String emailLogado) {
		Todo todo = TodoParser.get().entidade(dto);
		Todo todoBanco = buscarPorId(id);
		todoBanco.setNome(todo.getNome());
		dao.atualizar(todoBanco);
		statusService.atualizar(id, dto.getStatus(), emailLogado);
	}

	private Todo buscarPorId(Long id) {
		Todo todo = dao.buscarPorId(id);
		if (todo == null) {
			throw new NotFoundException();
		}
		return todo;
	}

}
