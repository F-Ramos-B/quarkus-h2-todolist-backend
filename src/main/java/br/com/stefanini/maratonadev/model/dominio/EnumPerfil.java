package br.com.stefanini.maratonadev.model.dominio;

import java.util.stream.Stream;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@Getter
public enum EnumPerfil {

	ADMIN(1L, Roles.ADMIN, "Administrador"),
	USER(2L, Roles.USER, "Usuário");
	
	private Long id;
	private String role;
	private String descricao;

	private EnumPerfil(Long id, String role, String descricao) {
		this.id = id;
		this.role = role;
		this.descricao = descricao;
	}
	
	public static EnumPerfil valueOfId(Long id) {
		return Stream.of(EnumPerfil.values()).filter(e -> e.getId().equals(id)).findFirst().orElseThrow(NullPointerException::new);
	}
	
	@UtilityClass
	public class Roles {
		public static final String ADMIN = "ADMIN";
		public static final String USER = "USER";
	}
	
}
