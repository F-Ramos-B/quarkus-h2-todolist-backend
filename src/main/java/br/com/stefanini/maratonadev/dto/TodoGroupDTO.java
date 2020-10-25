package br.com.stefanini.maratonadev.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoGroupDTO {

	private Integer total = 0;
	private Map<Long, List<TodoDTO>> tarefas = new HashMap<>();
	
}
