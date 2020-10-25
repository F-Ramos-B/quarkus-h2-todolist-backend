package br.com.stefanini.maratonadev.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;

import br.com.stefanini.maratonadev.dao.TodoStatusDAO;
import br.com.stefanini.maratonadev.dto.FiltroPaginacaoDTO;
import br.com.stefanini.maratonadev.dto.ResultadoPaginadoDTO;
import br.com.stefanini.maratonadev.dto.TodoStatusDTO;
import br.com.stefanini.maratonadev.model.Todo;
import br.com.stefanini.maratonadev.model.TodoStatus;
import br.com.stefanini.maratonadev.model.User;
import br.com.stefanini.maratonadev.model.dominio.EnumStatus;
import br.com.stefanini.maratonadev.model.parser.TodoStatusParser;

@RequestScoped
public class TodoStatusService {
	@Inject
	TodoStatusDAO todoStatusDAO;

	@Inject
	UserService userService;

	private void validar(TodoStatus todoStatus) {
		if (EnumStatus.isInvalido(todoStatus.getStatus().toString())) {
			throw new NotFoundException();
		}
	}

	private void validarAtualizacao(TodoStatus todoStatusBanco, TodoStatus todoStatusTela) {
		validar(todoStatusTela);
		if (todoStatusBanco.getStatus().equals(EnumStatus.DONE)) {
			throw new NotAllowedException("Tarefa com status que não permiti modificação");
		}

	}

	@Transactional(rollbackOn = Exception.class)
	public void inserir(Long id, EnumStatus enumTexto, User usuario) {
		TodoStatus status = new TodoStatus(enumTexto);
		status.setTodo(new Todo(id));
		status.setUser(usuario);
		validar(status);
		todoStatusDAO.inserir(status);
	}

	@Transactional(rollbackOn = Exception.class)
	public void atualizar(Long id, String enumTexto, String emailLogado) {
		TodoStatus statusTela = new TodoStatus(EnumStatus.valueOf(enumTexto));
		statusTela.setTodo(new Todo(id));
		TodoStatus statusBanco = todoStatusDAO.buscarStatusPorTarefa(id).get(0);
		validarAtualizacao(statusBanco, statusTela);

		statusTela.setTodo(new Todo(id));
		statusTela.setUser(userService.buscarUsuarioPorEmail(emailLogado));

		todoStatusDAO.inserir(statusTela);
	}

	public List<TodoStatusDTO> buscarTodosStatusPorTarefa(Long idTarefa) {
		List<TodoStatus> statusBanco = todoStatusDAO.buscarStatusPorTarefa(idTarefa);
		return statusBanco.stream().map(TodoStatusParser.get()::toDTO).collect(Collectors.toList());
	}

	public ResultadoPaginadoDTO<TodoStatusDTO> listarHistoricoPaginado(FiltroPaginacaoDTO filtro) {
		return todoStatusDAO.buscarHistoricoPaginado(filtro);
	}
}
