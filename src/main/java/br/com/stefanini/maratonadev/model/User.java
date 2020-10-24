package br.com.stefanini.maratonadev.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.PasswordType;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@UserDefinition
public class User extends PanacheEntityBase {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Username
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Password(PasswordType.CLEAR)
	@Column(name = "senha", nullable = false)
	private String senha;

	@Roles
	@Column(name = "permissao", nullable = false)
	private String permissao;
	
	@CreationTimestamp
	@Column(name = "dataCriacao", nullable = false, updatable = false)
	private LocalDateTime dataCriacao;

}
