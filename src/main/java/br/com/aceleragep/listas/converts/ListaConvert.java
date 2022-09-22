package br.com.aceleragep.listas.converts;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.aceleragep.listas.dto.inputs.ListaInput;
import br.com.aceleragep.listas.dto.outputs.ListaOutput;
import br.com.aceleragep.listas.entities.ListaEntity;

@Component
public class ListaConvert {
	
	@Autowired
	private ModelMapper modelMapper;

	public ListaEntity inputToEntity(ListaInput lista) {
		return modelMapper.map(lista, ListaEntity.class);
	}

	public ListaOutput entityToOutput(ListaEntity listaCriada) {
		return modelMapper.map(listaCriada, ListaOutput.class);
		
	}

	public List<ListaOutput> entityToOutput(List<ListaEntity> listaTodos) {
		return listaTodos.stream().map(lista -> this.entityToOutput(lista)).collect(Collectors.toList());
	}

}
