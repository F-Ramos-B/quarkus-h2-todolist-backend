package br.com.stefanini.maratonadev.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO implements Serializable {

	private static final long serialVersionUID = -9067592608684415105L;

	private Long id;

	@NotNull(message = "Nome é Obrigatorio")
	@NotBlank(message = "Não é permito nome vazio")
	@Length(min = 3, max = 250, message = "Não é permido nomes menores que 3 caracteres ou maiores que 250")
	private String nome;

	@JsonbDateFormat("dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataCriacao;

	private String status;
	private Long idStatus;

}
