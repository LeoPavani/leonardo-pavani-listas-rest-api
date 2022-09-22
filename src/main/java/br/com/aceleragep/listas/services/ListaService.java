package br.com.aceleragep.listas.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.aceleragep.listas.entities.ListaEntity;
import br.com.aceleragep.listas.exceptions.BadRequestBussinessException;
import br.com.aceleragep.listas.exceptions.NotFoundBussinessException;
import br.com.aceleragep.listas.repositories.ListaRepository;

@Service
public class ListaService {
	
	@Autowired
	private ListaRepository listaRepository;

	@Transactional
	public ListaEntity cria(ListaEntity listaEntity) {
		return listaRepository.save(listaEntity);
	}

	@Transactional
	public ListaEntity alterar(ListaEntity lista) {
		if (lista.getId() == null || lista.getId() <= 0) {
			throw new BadRequestBussinessException("O campo Id é obrigatório para alterar um autor!");
		}

		this.buscaPeloId(lista.getId());

		return listaRepository.save(lista);
	}
	
	public ListaEntity buscaPeloId(Long id) {
		return listaRepository.findById(id)
				.orElseThrow(() -> new NotFoundBussinessException("Não foi encontrado a lista pelo id: " + id));
	}

	public List<ListaEntity> listaTodos() {
		return listaRepository.findAll();
	}

	@Transactional
	public void remover(ListaEntity listaEntity) {
		listaRepository.delete(listaEntity);
	}

	
	
}
