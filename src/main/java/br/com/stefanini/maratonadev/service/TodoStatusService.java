package br.com.stefanini.maratonadev.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import br.com.stefanini.maratonadev.dao.TodoStatusDao;
import br.com.stefanini.maratonadev.model.TodoStatus;
import br.com.stefanini.maratonadev.model.dominio.StatusEnum;

@RequestScoped
public class TodoStatusService {
	@Inject
	TodoStatusDao dao;

	private void validar(TodoStatus status) {
		if(StatusEnum.isInvalido(status.toString())) {
			throw new NotFoundException();
		}
	}
	
	public void inserir(Long id, StatusEnum enumTexto) {
		TodoStatus status = new TodoStatus(enumTexto);
		validar(status);
		dao.inserir(status);
		
	}
}
