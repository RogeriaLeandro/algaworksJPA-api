package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepositorytImpl;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private RestauranteRepositorytImpl restauranteRepositorytImpl;
	
	@GetMapping("/cozinhas/por-nome") //passar por QueryString - QueryParams no Postman - @RequestParam / por-nome?nome=Tailandesa?
	public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome) {
		return cozinhaRepository.findTodasByNomeContaining(nome);
	}
	
	@GetMapping("/cozinhas/primeira") //passar por QueryString - QueryParams no Postman - @RequestParam / por-nome?nome=Tailandesa?
	public Optional<Cozinha> cozinhaPrimeiro() {
		return cozinhaRepository.buscarPrimeiro();
	}
	
	@GetMapping("/cozinhas/unico-por-nome") //passar por QueryString - QueryParams no Postman - @RequestParam / por-nome?nome=Tailandesa?
	public Optional<Cozinha> cozinhaPorNome(@RequestParam("nome") String nome) {
		return cozinhaRepository.findByNome(nome);
	}
	
	@GetMapping("/restaurantes/por-taxa-frete") //passar por QueryString - QueryParams no Postman - @RequestParam / por-nome?nome=Tailandesa?
	public List<Restaurante> restaurantePorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/por-nome") //passar por QueryString - QueryParams no Postman - @RequestParam / por-nome?nome=Tailandesa?
	public List<Restaurante> restaurantePorNomeAndCozinha(String nome, Long cozinhaId) {
		return restauranteRepository.consultarPorNome(nome, cozinhaId);
	}
	
	@GetMapping("/restaurantes/primeiro-por-nome") //passar por QueryString - QueryParams no Postman - @RequestParam / por-nome?nome=Tailandesa?
	public Optional<Restaurante> restaurantePrimeiroPorNome(String nome) {
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
	}
	
	
	@GetMapping("/restaurantes/top2-por-nome") //passar por QueryString - QueryParams no Postman - @RequestParam / por-nome?nome=Tailandesa?
	public List<Restaurante> restauranteTop2PorNome(String nome) {
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}
	//os dois primeiros
	
	
	@GetMapping("/cozinhas/exists")
	public boolean cozinhaExists(String nome) {
		return cozinhaRepository.existsByNome(nome);
	}
	
	@GetMapping("/restaurantes/count-por-cozinha") 
	public int restaurantesCountPorCozinha(Long cozinhaId) {
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
	
	@GetMapping("/restaurantes/por-nome-e-frete")
	public  List<Restaurante> restaurantesPorNomeFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		return restauranteRepositorytImpl.find(nome, taxaFreteInicial, taxaFreteFinal);
	}
	
	@GetMapping("/restaurantes/com-frete-gratis")
	public  List<Restaurante> restaurantesComFreteGratis(String nome) {
		
//		var comFreteGratis = new RestauranteComFreteGratisSpec(); //classe q representa um filtro
//		var comNomeSemelhante = new RestauranteComNomeSemelhanteSpec(nome);
//		ou usa o RestauranteSpecs.
		return restauranteRepository.findAll(RestauranteSpecs.comFreteGratis()
											.and(RestauranteSpecs.comNomeSemelhante(nome)));
		//passando os specifications como parâmetros!  
	}
	
	@GetMapping("/restaurantes/priemiro")
	public  Optional<Restaurante> restaurantesPrimeiro() {
		return restauranteRepository.buscarPrimeiro();
	}
}

//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
//palavras para criação de sql no JPA - facilita o trabalho


