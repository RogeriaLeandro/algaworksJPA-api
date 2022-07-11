//package com.algaworks.algafood.infrastructure.repository;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.algaworks.algafood.domain.model.Estado;
//import com.algaworks.algafood.domain.repository.EstadoRepository;
//
//@Component
//public class EstadoRepositorytImpl implements EstadoRepository{
//
//	//Autowired do JPA
//	@PersistenceContext
//	private EntityManager manager; 
//	
//	@Override
//	public List<Estado> listar() {
//		return manager.createQuery("from Estado", Estado.class)
//											.getResultList();
//		//só criou a consulta
//		//não é SQL, é um JPQL, consulta em objetos e não em tabelas
//		//todos os objetos de cozinha
//		//.getResultList = traz a lista
//	}
//	
//	@Override
//	@Transactional
//	public Estado salvar(Estado estado) {
//		//insert into cozinha(nome) values ..
//		
//		return manager.merge(estado);
//		//merge não altera
//	}
//	
//	@Override
//	@Transactional
//	public Estado buscar(Long id) {
//		return manager.find(Estado.class, id);
//	}
//	
//	@Override
//	@Transactional
//	public void remover(Long estadoId) {
//		Estado estado = buscar(estadoId);
//		
//		if (estado == null) {
//			throw new EmptyResultDataAccessException(1);
//		}
//		
//		manager.remove(estado);
//	}
//
//	
//	
//}
