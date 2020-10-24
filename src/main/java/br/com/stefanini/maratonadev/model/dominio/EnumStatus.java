package br.com.stefanini.maratonadev.model.dominio;

import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum EnumStatus {
	
	TODO(1L, "TODO", "info", "Para Executar"),
	DOING(2L, "DOING", "warning", "Executando"),
	DONE(3L, "DONE", "success", "Feito"),
	BLOCK(4L, "BLOCK", "danger", "Bloqueado");

	private Long id;
	private String titulo;
	private String severity;
	private String descricao;

	EnumStatus(Long id, String titulo, String severity, String descricao) {
		this.id = id;
		this.titulo = titulo;
		this.severity = severity;
		this.descricao = descricao;
	}

	public static Boolean isInvalido(String teste) {
		for (EnumStatus status : EnumStatus.values()) {
			if (status.name().equals(teste)) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;

	}
	
	public static EnumStatus valueOfId(Long id) {
		return Stream.of(EnumStatus.values()).filter(e -> e.getId().equals(id)).findFirst().orElseThrow(NullPointerException::new);
	}

}
