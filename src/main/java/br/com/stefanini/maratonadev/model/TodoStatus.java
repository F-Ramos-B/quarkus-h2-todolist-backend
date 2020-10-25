package br.com.stefanini.maratonadev.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import br.com.stefanini.maratonadev.model.dominio.EnumStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todostatus")
public class TodoStatus extends PanacheEntity {

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumStatus status;

	@ManyToOne(optional = false)
	@JoinColumn(name = "todo_id", updatable = false)
	private Todo todo;

	@Column(name = "data")
	@UpdateTimestamp
	private LocalDateTime data;

	@ManyToOne(optional = false)
	private User user;

	@Override
	public String toString() {
		// TODO
		return status.name();
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public TodoStatus(EnumStatus statusEnum) {
		this.status = statusEnum;
	}

}
