package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositorytImpl implements RestauranteRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> find (String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		var jpql = "from Restaurante where nome lile :nome "
				+ "and taxaFrete between :taxaInicial and :taxaFinal";
		
		return manager.createQuery(jpql, Restaurante.class)
				.setParameter("nome", "%" + nome + "%")
				.setParameter("taxaInicial", taxaFreteFinal)
				.setParameter("taxaFinal", taxaFreteFinal)
				.getResultList();
	}
	
}







//package com.algaworks.algafood.infrastructure.repository;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.algaworks.algafood.domain.model.Restaurante;
//import com.algaworks.algafood.domain.repository.RestauranteRepository;
//
//@Component
//public class RestauranteRepositorytImpl implements RestauranteRepository{
//
//	//Autowired do JPA
//	@PersistenceContext
//	private EntityManager manager; 
//	
//	@Override
//	public List<Restaurante> listar() {
//		return manager.createQuery("from Restaurante", Restaurante.class)
//											.getResultList();
//		//só criou a consulta
//		//não é SQL, é um JPQL, consulta em objetos e não em tabelas
//		//todos os objetos de cozinha
//		//.getResultList = traz a lista
//	}
//	
//	@Override
//	@Transactional
//	public Restaurante salvar(Restaurante restaurante) {
//		//insert into cozinha(nome) values ..
//		
//		return manager.merge(restaurante);
//		//merge não altera
//	}
//	
//	@Override
//	@Transactional
//	public Restaurante buscar(Long id) {
//		return manager.find(Restaurante.class, id);
//	}
//	
//	@Override
//	@Transactional
//	public void remover(Long id) {
//		Restaurante restaurante = buscar(id);
//		manager.remove(restaurante);
//	}
//
//	
//	
//}
