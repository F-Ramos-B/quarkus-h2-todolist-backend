package br.com.stefanini.maratonadev.dto;

import java.io.Serializable;

import br.com.stefanini.maratonadev.model.dominio.EnumPerfil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissaoDTO implements Serializable {

	private static final long serialVersionUID = 8294385372844151736L;
	
	private Long id;
	private String role;
	private String descricao;
	
	public PermissaoDTO(EnumPerfil enumPerfil) {
		this.id = enumPerfil.getId();
		this.role = enumPerfil.getRole();
		this.descricao = enumPerfil.getDescricao();
	}

}
