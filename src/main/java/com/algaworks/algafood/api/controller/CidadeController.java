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
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

//@Controller //se já tem RestController, não precisa, o RestController tem controller na classe
//@ResponseBody //indica que a resposta dos métodos do controlador devem ir pra resposta da requisição
@RestController //controlador q tb tem o ResponseBody dentro dela. não precisa do @REsponseBody
@RequestMapping(value = "/cidades")//, produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	
	@GetMapping (produces = MediaType.APPLICATION_JSON_VALUE) //produz um só formato especifico - default: json
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}

	@ResponseStatus(HttpStatus.ACCEPTED) //somente visualizar no postman, mesmo não sendo esse status, é só pra ver no postman
	@GetMapping("/{cidadeId}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId) { //ResponseEntity - manipular resposta
		Optional<Cidade> cidade = cidadeRepository.findById(cidadeId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/restaurantes");
		
		if (cidade.isPresent()) {
			return ResponseEntity.ok(cidade.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
		try {
			cidade = cadastroCidadeService.salvar(cidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		} catch (EntidadeNaoEncontradaException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
	}
	
	@PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId,
            @RequestBody Cidade cidade) {

		try {
			Optional<Cidade> cidadeAtual = cidadeRepository.findById(cidadeId);
			
			if(cidadeAtual != null) {
				BeanUtils.copyProperties(cidade, cidadeAtual.get(), "id");
				Cidade cidadeSalva = cadastroCidadeService.salvar(cidadeAtual.get());
				return ResponseEntity.ok(cidadeSalva);
			}
				return ResponseEntity.notFound().build(); //404
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());//400
		}

	}
	

    @DeleteMapping("/{cidadeAId}")
	public ResponseEntity<?> remover(@PathVariable Long cidadeAId) {
		try {
			cadastroCidadeService.excluir(cidadeAId);	
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		}
	}
    
	
}


// GET /cozinhas HTTP/1.1