package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

//@Controller //se já tem RestController, não precisa, o RestController tem controller na classe
//@ResponseBody //indica que a resposta dos métodos do controlador devem ir pra resposta da requisição
@RestController //controlador q tb tem o ResponseBody dentro dela. não precisa do @REsponseBody
@RequestMapping(value = "/cozinhas")//, produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	
	@GetMapping (produces = MediaType.APPLICATION_JSON_VALUE) //produz um só formato especifico - default: json
	public List<Cozinha> listar() {
		System.out.println("Listar 1");
		return cozinhaRepository.findAll();
	}

	@ResponseStatus(HttpStatus.ACCEPTED) //somente visualizar no postman, mesmo não sendo esse status, é só pra ver no postman
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) { //ResponseEntity - manipular resposta
		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/cozinhas");
		
		if (cozinha.isPresent()) { //se tem cozinha dentro do Optional 
			return ResponseEntity.ok(cozinha.get()); // pego a cozinha q tá dentro de Optional;
		}
		
		return ResponseEntity.notFound().build();

		
//		return ResponseEntity.status(HttpStatus.OK).body(cozinha);
//		return ResponseEntity.ok(cozinha);
//		
//		return ResponseEntity
//				.status(HttpStatus.FOUND)
//				.headers(headers)
//				.build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) //faz sentido colocar o CREATED pra informar q foi criado
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		System.out.println(cozinha);
		return cadastroCozinhaService.salvar(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) { //as duas coisas no mesmo método
		Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);
		//cozinhaAtual.setNome(cozinha.getNome());
		
		if (cozinhaAtual.isEmpty()) {
			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
			Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinhaAtual.get());
			return ResponseEntity.ok(cozinhaSalva);
		}		

		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId){
		try {
			cadastroCozinhaService.excluir(cozinhaId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}
	
}


// GET /cozinhas HTTP/1.1