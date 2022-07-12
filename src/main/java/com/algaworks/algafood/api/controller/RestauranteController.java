package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Controller //se já tem RestController, não precisa, o RestController tem controller na classe
//@ResponseBody //indica que a resposta dos métodos do controlador devem ir pra resposta da requisição
@RestController //controlador q tb tem o ResponseBody dentro dela. não precisa do @REsponseBody
@RequestMapping(value = "/restaurantes")//, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	
	@GetMapping (produces = MediaType.APPLICATION_JSON_VALUE) //produz um só formato especifico - default: json
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	@ResponseStatus(HttpStatus.ACCEPTED) //somente visualizar no postman, mesmo não sendo esse status, é só pra ver no postman
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) { //ResponseEntity - manipular resposta
		Optional <Restaurante> restaurante = restauranteRepository.findById(restauranteId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/restaurantes");
		
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
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
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestauranteService.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
	}
	
	@PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
            @RequestBody Restaurante restaurante) {

		try {
			Restaurante restauranteAtual = restauranteRepository.findById(restauranteId).orElse(null);
			
			if(restauranteAtual!= null) {
				BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
				restauranteAtual = cadastroRestauranteService.salvar(restauranteAtual);
				return ResponseEntity.ok(restauranteAtual);
			}
				return ResponseEntity.notFound().build(); //404
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());//400
		}

	}
	
	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId){
		try {
			cadastroRestauranteService.excluir(restauranteId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
			@RequestBody Map<String, Object> campos) {
		Restaurante restauranteAtual = restauranteRepository
				.findById(restauranteId).orElse(null);
		
		if (restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos, restauranteAtual);
		
		return atualizar(restauranteId, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		
		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
//			System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
	
}
	
//	@PatchMapping("/{restauranteId}")
//	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) {
//		
//		Optional <Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
//		
//		if(restauranteAtual == null) {
//			return ResponseEntity.notFound().build();
//		}
//		
//		merge(campos, restauranteAtual);
//		
//		return atualizar(restauranteId, restauranteAtual);
//	}
//
//	
//	//ASSISTIR NOVAMENTE A AULA 4.34
//	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
//		ObjectMapper objectMapper = new ObjectMapper();
//		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
//		
//		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade); // variável do tipo Field (lang.reflect) representa um atributo da classe. ReflectionUtils.findField atribuindo o nomePropriedade ao campo da classe
//			field.setAccessible(true); //mesmo o atributo na classe REstaurante sendo privado, esse metodo quebra o private! Qdo setField, sendo o campo private, ele não aceita. por isso, quebra-se esse private.
//			
//			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem); // buscando valor do campo e atribuindo a instancia de restauranteOrigem
//			
//			ReflectionUtils.setField(field, restauranteDestino, novoValor); // atribuindo o novo valor convertido pelo ObjectMapper. Antes, ele não sabia o tipo do campo (String, BigDecimal...)
//			
//			System.out.println(nomePropriedade + "=" + valorPropriedade + " = " + novoValor);
//			//Reflections - inspecionar objetos e mudar em tempo de execução
//			
//		});
//	}
	



/*
 * REFLECTION
 * Quandop não sabemos as propriedades, não sabemos o noms dos métodos, fazer algo mais Generico
 * Modificar de forma dinâmica
 * inspecionar e programar para utilizar em tempo de execução
 */


// GET /cozinhas HTTP/1.1