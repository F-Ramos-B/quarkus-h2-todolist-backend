package br.com.stefanini.maratonadev.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.opentracing.Traced;

import br.com.stefanini.maratonadev.dao.TodoDAO;
import br.com.stefanini.maratonadev.dto.TodoDTO;
import br.com.stefanini.maratonadev.dto.TodoGroupDTO;
import br.com.stefanini.maratonadev.model.Todo;
import br.com.stefanini.maratonadev.model.User;
import br.com.stefanini.maratonadev.model.dominio.EnumStatus;
import br.com.stefanini.maratonadev.model.parser.TodoParser;

@RequestScoped
@Traced
public class TodoService {

	@Inject
	TodoDAO todoDAO;

	@Inject
	TodoStatusService statusService;

	@Inject
	UserService userService;
	
	native float getVariance() throws Exception;

	private void validar(Todo todo) {
		if (todoDAO.isnomeRepetido(todo.getNome())) {
			throw new NotFoundException();
		}
	}

	@Transactional(rollbackOn = Exception.class)
	public void inserir(@Valid TodoDTO todoDto, String emailLogado) {
		Todo todo = TodoParser.get().toEntity(todoDto);
		validar(todo);
		User usuario = userService.buscarUsuarioPorEmail(emailLogado);
		Long id = todoDAO.inserir(todo);
		statusService.inserir(id, EnumStatus.TODO, usuario);
	}

	public List<TodoDTO> listar() {
		return TodoParser.get().toDTOList(todoDAO.listar());
	}
	
	public TodoGroupDTO listarAgrupado() {
		TodoGroupDTO dto = new TodoGroupDTO();
		List<TodoDTO> listar = this.listar();
		
		Map<Long, List<TodoDTO>> grouped = listar.stream().collect(Collectors.groupingBy(TodoDTO::getIdStatus));
		
		dto.setTarefas(grouped);
		dto.setTotal(listar.size());
		return dto;
	}

	public void excluir(Long id) {
		if (todoDAO.buscarPorId(id) == null) {
			throw new NotFoundException();
		}
		todoDAO.excluir(id);
	}

	public TodoDTO buscar(Long id) {
		return TodoParser.get().toDTO(buscarPorId(id));
	}

	@Transactional(rollbackOn = Exception.class)
	public void atualizar(TodoDTO dto, String emailLogado) {
		Todo todo = TodoParser.get().toEntity(dto);
		Todo todoBanco = buscarPorId(dto.getId());
		
		todoBanco.setNome(todo.getNome());
		todoDAO.atualizar(todoBanco);
		statusService.atualizar(dto.getId(), dto.getStatus(), emailLogado);
	}

	private Todo buscarPorId(Long id) {
		Todo todo = todoDAO.buscarPorId(id);
		if (todo == null) {
			throw new NotFoundException();
		}
		return todo;
	}

	public void atualizarStatus(Long id, Long idStatus, String name) {
		Todo todoBanco = buscarPorId(id);
		todoDAO.atualizar(todoBanco);
		statusService.atualizar(id, EnumStatus.valueOfId(idStatus).getTitulo(), name);
		
	}

}
