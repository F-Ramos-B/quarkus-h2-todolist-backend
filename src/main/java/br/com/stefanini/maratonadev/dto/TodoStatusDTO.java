package br.com.stefanini.maratonadev.dto;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbDateFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoStatusDTO {

	private Long id;
	private String status;
	private String nomeUsuario;

	@JsonbDateFormat("dd/MM/yyyy HH:mm:ss")
	private LocalDateTime data;
}
