package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepositoryQueries;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries{

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal); //entre inicial e final
	//pode-se usar "query" no lugar do find. 
	
//	@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long idCozinha);
	
//	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long idCozinha);
	
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	//entre find e By - pode-se usar prefixos	
	
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	int countByCozinhaId(Long cozinha);
	
//	List<Restaurante> restaurantesPorNomeFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	//mesmo não implementando esse Repository, ele sabe q tem a implemnetação customizada
	//tem q ter o sufixo do Repository + "Impl"
}


//armazenar as nossas cozinhas
//DDD aggregate
//repositorio orientado a persistência
//não se cria um repositório por entidade. Repositórios por Agregados - padrão AGGREGATE do DDD



//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
//palavras para criação de sql no JPA - facilita o trabalho