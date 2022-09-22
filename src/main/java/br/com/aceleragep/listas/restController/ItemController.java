package br.com.aceleragep.listas.restController;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.aceleragep.listas.converts.ItemConvert;
import br.com.aceleragep.listas.dto.inputs.ItemInput;
import br.com.aceleragep.listas.dto.outputs.ItemOutput;
import br.com.aceleragep.listas.entities.ItemEntity;
import br.com.aceleragep.listas.services.ItemService;
import br.com.aceleragep.listas.services.ListaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Item")
@RestController
@RequestMapping("/api/itens")
@CrossOrigin(origins = "*")
public class ItemController {
	
	@Autowired
	private ItemConvert itemConvert;

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ListaService listaService;

	@Operation(summary = "Cadastra item", description = "Cadastra um item")
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ItemOutput criaItem(@Parameter(description = "Representação de um Item") 
			@Valid @RequestBody ItemInput item) throws URISyntaxException {
		listaService.buscaPeloId(item.getLista());
		ItemEntity itemEntity = itemConvert.inputToEntity(item);
		ItemEntity itemCriado = itemService.cria(itemEntity);
		return itemConvert.entityToOutput(itemCriado);
	}
	
	@Operation(summary = "Altera item", description = "Altera um item existente")
	@PutMapping("/{id}")
	public ItemOutput alteraItem(@Parameter(description = "Id do item", example = "1") 
			@PathVariable Long id, @Parameter(description = "Representação de um item")  
			@Valid @RequestBody ItemInput itemInput) {
		listaService.buscaPeloId(itemInput.getLista());
		ItemEntity itemEntity = itemConvert.inputToEntity(itemInput);
		itemEntity.setId(id);
		ItemEntity itemAlterado = itemService.alterar(itemEntity);
		return itemConvert.entityToOutput(itemAlterado);
	}
	
	@Operation(summary = "Exclui item", description = "Exclui um item")
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeItem(@Parameter(description = "Id do item", example = "1") @PathVariable Long id) {
		ItemEntity itemEntity = itemService.buscaPeloId(id);
		itemService.remover(itemEntity);
	}
	
	@Operation(summary = "Conclui item", description = "Altera a propriedade 'concluido' de um item existente para TRUE")
	@PutMapping("/marca-concluido/{id}")
	public ItemOutput marcaItemComoConcluido(@Parameter(description = "Id do item", example = "1") 
			@PathVariable Long id) {
		ItemEntity itemEntity = itemService.buscaPeloId(id);
		itemService.marcaComoConcluido(itemEntity);
		ItemEntity itemAlterado = itemService.alterar(itemEntity);
		return itemConvert.entityToOutput(itemAlterado);
	}
	
	@Operation(summary = "Desmarca item concluído", description = "Altera a propriedade 'concluido' de um item existente para FALSE")
	@PutMapping("/desmarca-concluido/{id}")
	public ItemOutput desmarcaItemComoConcluido(@Parameter(description = "Id do item", example = "1") 
			@PathVariable Long id) {
		ItemEntity itemEntity = itemService.buscaPeloId(id);
		itemService.desmarcaComoConcluido(itemEntity);
		ItemEntity itemAlterado = itemService.alterar(itemEntity);
		return itemConvert.entityToOutput(itemAlterado);
	}
	
	@Operation(summary = "Lista itens por Lista", description = "Lista todos os itens de uma Lista cadastrada")
	@GetMapping("/{id}")
	public List<ItemOutput> listaItensPorLista(@Parameter(description = "Id da lista", example = "1") 
	@PathVariable Long id) {
		listaService.buscaPeloId(id);
		List<ItemEntity> listaItens = itemService.listaPorIdLista(id);
		return itemConvert.entityToOutput(listaItens);
	}
	
	
}
