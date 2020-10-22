package br.com.stefanini.maratonadev.dao;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import br.com.stefanini.maratonadev.model.TodoStatus;

@RequestScoped
public class TodoStatusDao {

	@Transactional
	public void inserir(TodoStatus status) {
//		status.persistAndFlush();
		TodoStatus.persist(status);
	}
}
