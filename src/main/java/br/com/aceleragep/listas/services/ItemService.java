package br.com.aceleragep.listas.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.aceleragep.listas.entities.ItemEntity;
import br.com.aceleragep.listas.exceptions.BadRequestBussinessException;
import br.com.aceleragep.listas.exceptions.NotFoundBussinessException;
import br.com.aceleragep.listas.repositories.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;

	@Transactional
	public ItemEntity cria(ItemEntity itemEntity) {
		return itemRepository.save(itemEntity);
	}
	
	public ItemEntity marcaComoConcluido(ItemEntity itemEntity) {
		ItemEntity itemEncontrado = this.buscaPeloId(itemEntity.getId());
		itemEncontrado.setConcluido(true);
		return itemEncontrado;
	}
	
	public ItemEntity desmarcaComoConcluido(ItemEntity itemEntity) {
		ItemEntity itemEncontrado = this.buscaPeloId(itemEntity.getId());
		itemEncontrado.setConcluido(false);
		return itemEncontrado;
	}
	
	public ItemEntity buscaPeloId(Long id) {
		return itemRepository.findById(id).orElseThrow(
				() -> new NotFoundBussinessException("Não foi encontrado o item pelo id: " + id));
	}

	@Transactional
	public ItemEntity alterar(ItemEntity itemEntity) {
		if (itemEntity.getId() == null || itemEntity.getId() <= 0) {
			throw new BadRequestBussinessException("O campo Id é obrigatório para alterar um autor!");
		}

		this.buscaPeloId(itemEntity.getId());

		return itemRepository.save(itemEntity);
	}

	@Transactional
	public void remover(ItemEntity itemEntity) {
		itemRepository.delete(itemEntity);
	}

	public List<ItemEntity> listaPorIdLista(Long id) {
		return itemRepository.findAllByLista(id);
	}

	@Transactional
	public void removeListaDeItens(List<ItemEntity> listaItens) {
		for (ItemEntity item : listaItens) {
			this.remover(item);
		}
		
	}

}
