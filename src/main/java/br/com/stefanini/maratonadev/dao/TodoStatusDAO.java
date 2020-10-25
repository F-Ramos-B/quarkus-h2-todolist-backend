package br.com.stefanini.maratonadev.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import br.com.stefanini.maratonadev.dto.FiltroPaginacaoDTO;
import br.com.stefanini.maratonadev.dto.ResultadoPaginadoDTO;
import br.com.stefanini.maratonadev.dto.TodoStatusDTO;
import br.com.stefanini.maratonadev.model.Todo;
import br.com.stefanini.maratonadev.model.TodoStatus;
import br.com.stefanini.maratonadev.model.parser.TodoStatusParser;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

@RequestScoped
public class TodoStatusDAO {

	@Transactional
	public void inserir(TodoStatus status) {
//		status.persistAndFlush();
		TodoStatus.persist(status);
	}
	
	private Sort getSortData() {
		return Sort.by("data").descending();
	}

	public List<TodoStatus> buscarStatusPorTarefa(Long idTarefa) {
		return TodoStatus.list("todo", this.getSortData(), new Todo(idTarefa));
	}
	
	public ResultadoPaginadoDTO<TodoStatusDTO> buscarHistoricoPaginado(FiltroPaginacaoDTO filtro) {
		PanacheQuery<TodoStatus> historico = TodoStatus.find("todo.id", this.getSortData(), filtro.getId());
		
		List<TodoStatusDTO> historicoPaginado = TodoStatusParser.get().toDTOList(historico.page(Page.of(filtro.getPageNumber(), filtro.getPageSize())).list());
		
		return new ResultadoPaginadoDTO<>(historico.count(), historico.pageCount(), historicoPaginado);
	}
}
