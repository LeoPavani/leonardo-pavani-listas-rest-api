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

import br.com.aceleragep.listas.converts.ListaConvert;
import br.com.aceleragep.listas.dto.inputs.ListaInput;
import br.com.aceleragep.listas.dto.outputs.ListaOutput;
import br.com.aceleragep.listas.entities.ItemEntity;
import br.com.aceleragep.listas.entities.ListaEntity;
import br.com.aceleragep.listas.services.ItemService;
import br.com.aceleragep.listas.services.ListaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Lista")
@RestController
@RequestMapping("/api/listas")
@CrossOrigin(origins = "*")
public class ListaController {
	
	@Autowired
	private ListaConvert listaConvert;

	@Autowired
	private ListaService listaService;
	
	@Autowired
	private ItemService itemService;

	@Operation(summary = "Cadastra lista", description = "Cadastra uma lista")
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ListaOutput criaLista(@Parameter(description = "Representação de uma Lista") 
			@Valid @RequestBody ListaInput lista) throws URISyntaxException {
		ListaEntity listaEntity = listaConvert.inputToEntity(lista);
		ListaEntity listaCriada = listaService.cria(listaEntity);
		return listaConvert.entityToOutput(listaCriada);
	}
	
	@Operation(summary = "Altera lista", description = "Altera uma lista existente")
	@PutMapping("/{id}")
	public ListaOutput alteraLista(@Parameter(description = "Id da lista", example = "1") 
			@PathVariable Long id, @Parameter(description = "Representação de uma lista")  
			@Valid @RequestBody ListaInput listaInput) {
		ListaEntity listaEntity = listaConvert.inputToEntity(listaInput);
		listaEntity.setId(id);
		ListaEntity listaAlterado = listaService.alterar(listaEntity);
		return listaConvert.entityToOutput(listaAlterado);
	}
	
	@Operation(summary = "Lista listas", description = "Lista todas as listas cadastradas")
	@GetMapping
	public List<ListaOutput> listaListas() {
		List<ListaEntity> listaTodos = listaService.listaTodos();
		return listaConvert.entityToOutput(listaTodos);
	}
	
	@Operation(summary = "Busca lista", description = "Busca lista por ID")
	@GetMapping("/{id}")
	public ListaOutput buscaListaPorId(@Parameter(description = "Id da lista", example = "1") @PathVariable Long id) {
		ListaEntity listaEntity = listaService.buscaPeloId(id);
		return listaConvert.entityToOutput(listaEntity);
	}
	
	@Operation(summary = "Exclui lista", description = "Exclui uma lista")
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeLista(@Parameter(description = "Id da lista", example = "1") @PathVariable Long id) {
		ListaEntity listaEntity = listaService.buscaPeloId(id);
		List<ItemEntity> listaItens = itemService.listaPorIdLista(id);
		itemService.removeListaDeItens(listaItens);
		listaService.remover(listaEntity);
	}
}
