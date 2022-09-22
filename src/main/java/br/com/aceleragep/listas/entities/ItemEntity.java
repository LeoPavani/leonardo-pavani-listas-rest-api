package br.com.aceleragep.listas.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_itens")
@Getter
@Setter
public class ItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100, name = "titulo")
	private String titulo;
	
	@Column(name = "concluido")
	private boolean concluido;
	
	@Column(name = "lista")
	private Long lista;
}
