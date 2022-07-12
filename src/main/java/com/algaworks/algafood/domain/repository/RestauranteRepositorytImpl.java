package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositorytImpl implements RestauranteRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		
		//fabrica de construir elementos para a consulta
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		//Criteria compõe as clausulas de uma query		
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		
		//a raiz do from
		Root<Restaurante> root =  criteria.from(Restaurante.class); //from Restaurante
		
		Predicate nomePredicate = builder.like(root.get("nome"), "%" + nome + "%");
		Predicate taxaInicialPredicate = builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial);
		Predicate taxaFinalPredicate = builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal);
		criteria.where(nomePredicate, taxaInicialPredicate, taxaFinalPredicate);
		
		
		TypedQuery<Restaurante> query = manager.createQuery(criteria);
		return query.getResultList();
}
		//jpql
//		var jpql = new StringBuilder();
//		jpql.append("from Restaurante where 0 = 0 ");
//		
//		var parametros = new HashMap<String, Object>();
//		
//		if (StringUtils.hasLength(nome)) {
//			jpql.append("and nome like :nome ");
//			parametros.put("nome", "%" + nome + "%");
//		}
//		
//		if (taxaFreteInicial != null) {
//			jpql.append("and taxaFrete >= :taxaInicial ");
//			parametros.put("taxaInicial", taxaFreteInicial);
//		}
//		
//		if (taxaFreteFinal != null) {
//			jpql.append("and taxaFrete <= :taxaFinal ");
//			parametros.put("taxaFinal", taxaFreteFinal);
//		}
////		
//		TypedQuery<Restaurante> query = manager.
//				createQuery(jpql.toString(), Restaurante.class);
//		
//		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
//		
//		return query.getResultList();
//	}
	
}

///com o jpql precisamos fazer concatenação de strings.





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
