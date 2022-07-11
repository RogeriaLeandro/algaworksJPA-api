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
//import com.algaworks.algafood.domain.model.Cidade;
//import com.algaworks.algafood.domain.repository.CidadeRepository;
//
//@Component
//public class CidadeRepositorytImpl implements CidadeRepository{
//
//	//Autowired do JPA
//	@PersistenceContext
//	private EntityManager manager; 
//	
//	@Override
//	public List<Cidade> listar() {
//		return manager.createQuery("from Cidade", Cidade.class)
//											.getResultList();
//		//só criou a consulta
//		//não é SQL, é um JPQL, consulta em objetos e não em tabelas
//		//todos os objetos de cozinha
//		//.getResultList = traz a lista
//	}
//	
//	@Override
//	@Transactional
//	public Cidade salvar(Cidade cidade) {
//		//insert into cozinha(nome) values ..
//		
//		return manager.merge(cidade);
//		//merge não altera
//	}
//	
//	@Override
//	@Transactional
//	public Cidade buscar(Long id) {
//		return manager.find(Cidade.class, id);
//	}
//	
//	@Override
//	@Transactional
//	public void remover(Long cidadeId) {
//		Cidade cidade = buscar(cidadeId);
//		
//		if (cidade == null) {
//			throw new EmptyResultDataAccessException(1);
//		}
//		
//		manager.remove(cidade);
//	}
//
//	
//	
//}
