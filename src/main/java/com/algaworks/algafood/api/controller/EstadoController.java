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
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

//@Controller //se já tem RestController, não precisa, o RestController tem controller na classe
//@ResponseBody //indica que a resposta dos métodos do controlador devem ir pra resposta da requisição
@RestController //controlador q tb tem o ResponseBody dentro dela. não precisa do @REsponseBody
@RequestMapping(value = "/estados")//, produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	
	@GetMapping (produces = MediaType.APPLICATION_JSON_VALUE) //produz um só formato especifico - default: json
	public List<Estado> listar() {
		System.out.println("Listar 1");
		return estadoRepository.findAll();
	}

	@ResponseStatus(HttpStatus.ACCEPTED) //somente visualizar no postman, mesmo não sendo esse status, é só pra ver no postman
	@GetMapping("/{estadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) { //ResponseEntity - manipular resposta
		Optional<Estado> estado = estadoRepository.findById(estadoId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/estados");
		
		if (estado.isPresent()) {
			return ResponseEntity.ok(estado.get());
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
	public Estado adicionar(@RequestBody Estado estado) {
		System.out.println(estado);
		return cadastroEstadoService.salvar(estado);
	}
	
	@PutMapping("/{estadoId}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId, @RequestBody Estado estado) { //as duas coisas no mesmo método
		Optional<Estado> estadoAtual = estadoRepository.findById(estadoId);
		//cozinhaAtual.setNome(cozinha.getNome());
		
		if (estadoAtual.isEmpty()) {
			BeanUtils.copyProperties(estado, estadoAtual, "id");
			Estado estadoSalvo = cadastroEstadoService.salvar(estadoAtual.get());
			return ResponseEntity.ok(estadoSalvo);
		}		

		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{estadoId}")
	public ResponseEntity<Estado> remover(@PathVariable Long estadoId){
		try {
			cadastroEstadoService.excluir(estadoId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
}


// GET /cozinhas HTTP/1.1