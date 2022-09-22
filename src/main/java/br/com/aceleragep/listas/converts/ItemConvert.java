package br.com.aceleragep.listas.converts;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.aceleragep.listas.dto.inputs.ItemInput;
import br.com.aceleragep.listas.dto.outputs.ItemOutput;
import br.com.aceleragep.listas.entities.ItemEntity;

@Component
public class ItemConvert {
	
	@Autowired
	private ModelMapper modelMapper;

	public ItemEntity inputToEntity(@Valid ItemInput item) {
		return modelMapper.map(item, ItemEntity.class);
	}

	public ItemOutput entityToOutput(ItemEntity itemCriado) {
		return modelMapper.map(itemCriado, ItemOutput.class);
	}

	public List<ItemOutput> entityToOutput(List<ItemEntity> listaItens) {
		return listaItens.stream().map(lista -> this.entityToOutput(lista)).collect(Collectors.toList());
	}

}
