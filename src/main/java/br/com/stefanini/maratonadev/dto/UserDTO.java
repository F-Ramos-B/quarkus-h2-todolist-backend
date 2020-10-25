package br.com.stefanini.maratonadev.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbDateFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 5729393082493740870L;

	private String id;
	private String nome;
	private Long idRole;
	private String email;
	private String senha;
	private String permissao;
	
	@JsonbDateFormat("dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataCriacao;

}
