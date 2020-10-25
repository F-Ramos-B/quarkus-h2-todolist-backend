package br.com.stefanini.maratonadev.dto;

import java.io.Serializable;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiltroPaginacaoDTO implements Serializable {
	
	private static final long serialVersionUID = -6804350513800993423L;

	@QueryParam("pageSize")
	@DefaultValue("10")
	private Integer pageSize;
	
	@QueryParam("pageNumber")
	private Integer pageNumber;
	
	@QueryParam("id")
	private Long id;

}
