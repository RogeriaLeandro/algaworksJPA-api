  package com.algaworks.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonRootName("cozinha") //aparece no objeto do xml, ao inves de cozinha, aparece gastronomia
@Data // getter, setter, toString, equalsAndHashCode q usa todos os atributos, ideal é só com o id. por isso o q está abaixo
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@Table(name = "tab_cozinhas")
public class Cozinha {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //banco q gera a chave, o provedor q gera, no caso o mysql
	private Long id;
	
	//@JsonIgnore //ignorar esse titulo na saida dos dados
//	@JsonProperty("titulo") //nome a especificar no json gerado
	@Column(nullable = false)
	private String nome;
	
	@OneToMany(mappedBy = "cozinha") //uma cozinha tem muitos restaurantes; Many indica coleção; MappedBy qual é o nome da propriedade inversa lá em Restaurante
	private List<Restaurante> restaurantes = new ArrayList<>();
	//está mapeado em Restaurante, dono da associação.

}
