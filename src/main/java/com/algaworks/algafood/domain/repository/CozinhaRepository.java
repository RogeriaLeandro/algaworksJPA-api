package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> { // herdando JPA Repository

	List<Cozinha> findTodasByNomeContaining(String nome); //nome de uma propriedade da Cozinha, então funciona.
	//Containing - coloca percent antes e depois simulando like
	//usando Keywords para definir critérios de query methods - https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
	
	Optional<Cozinha> findByNome(String nome);
	
	boolean existsByNome(String nome);

}


//armazenar as nossas cozinhas
//DDD aggregate
//repositorio orientado a persistência
//não se cria um repositório por entidade. Repositórios por Agregados - padrão AGGREGATE do DDD


//pode-se usar o nome da propriedade da classe para nomear o método de procura do JPA / ou findBy + nome da propriedade 
