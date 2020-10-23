package br.com.stefanini.maratonadev.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;

import br.com.stefanini.maratonadev.dao.TodoStatusDao;
import br.com.stefanini.maratonadev.dto.TodoStatusDto;
import br.com.stefanini.maratonadev.model.Todo;
import br.com.stefanini.maratonadev.model.TodoStatus;
import br.com.stefanini.maratonadev.model.User;
import br.com.stefanini.maratonadev.model.dominio.StatusEnum;
import br.com.stefanini.maratonadev.model.parser.TodoStatusParser;

@RequestScoped
public class TodoStatusService {
	@Inject
	TodoStatusDao dao;

	private void validar(TodoStatus todoStatus) {
		if(StatusEnum
				.isInvalido(
						todoStatus.getStatus().toString()
						)) {
			throw new NotFoundException();
		}
	}
	private void validarAtualizacao(TodoStatus todoStatusBanco,
			TodoStatus todoStatusTela) {
		validar(todoStatusTela);
		if(todoStatusBanco.getStatus().equals(StatusEnum.DONE)) {
			throw new NotAllowedException(
					"Tarefa com status que não permiti modificação"
					);
		}
		
	}
	
	@Transactional(rollbackOn = Exception.class)
	public void inserir(Long id, StatusEnum enumTexto) {
		TodoStatus status = new TodoStatus(enumTexto);
		status.setTodo(new Todo(id));
		/**
		 * inserir usuario manual (temporario) 
		 * enquanto estamos autenticação
		 */
		User userTest = new User(1L);
//		userTest.persist();
		
//		if(userTest.isPersistent()) {
		//	System.out.println("Estou no Banco");
		//}
//		status.setUser();
		validar(status);
		dao.inserir(status);
		
	}
	@Transactional(rollbackOn = Exception.class)
	public void atualizar(Long id, String enumTexto) {
		TodoStatus statusTela = new TodoStatus(StatusEnum.valueOf(enumTexto));
		statusTela.setTodo(new Todo(id));
		TodoStatus statusBanco = dao.buscarStatusPorTarefa(id).get(0);
		validarAtualizacao(statusBanco, statusTela);
		dao.inserir(statusTela);
	}
	
	public List<TodoStatusDto> buscarTodosStatusPorTarefa(Long idTarefa){
		List<TodoStatus> statusBanco = dao.buscarStatusPorTarefa(idTarefa);
		return statusBanco
				.stream()
				.map(TodoStatusParser.get()::dto)
				.collect(Collectors.toList());
		
	}
}
