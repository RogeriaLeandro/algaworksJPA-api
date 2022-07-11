//package com.algaworks.algafood.infrastructure.repository;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.algaworks.algafood.domain.model.Cozinha;
//import com.algaworks.algafood.domain.repository.CozinhaRepository;
//@Repository
////@Component // funciona, mas o ideal é @Repository - por conta das Exceptions, principalmente - tradução de exception de Component para Repository
//public class CozinhaRepositorytImplJPQL implements CozinhaRepository{
//
//	//Autowired do JPA
//	@PersistenceContext
//	private EntityManager manager; 
//	
//	@Override
//	public List<Cozinha> listar() {
//		return manager.createQuery("from Cozinha", Cozinha.class)
//											.getResultList();
//		//só criou a consulta
//		//não é SQL, é um JPQL, consulta em objetos e não em tabelas
//		//todos os objetos de cozinha
//		//.getResultList = traz a lista
//	}
//	
//	@Override
//	@Transactional
//	public Cozinha salvar(Cozinha cozinha) {
//		//insert into cozinha(nome) values ..
//		
//		return manager.merge(cozinha);
//		//merge não altera
//	}
//	
//	@Override
//	@Transactional
//	public Cozinha buscar(Long id) {
//		return manager.find(Cozinha.class, id);
//	}
//	
//	@Override
//	@Transactional
//	public void remover(Long id) {
//		Cozinha cozinha = buscar(id);
//		
//		if (cozinha == null) {
//			throw new EmptyResultDataAccessException(1); //esperando pelo menos 1 cozinha
//		}
//		
//		manager.remove(cozinha);
//	}
//
//	@Override
//	public List<Cozinha> consultarPorNome(String nome) {
//
//		return manager.createQuery("from Cozinha where nome like :nome", Cozinha.class)
//				.setParameter("nome","%" + nome + "%")
//				.getResultList();
//	}
//
//}

//usando JPQL primeiro. manager.createQuery (...)

