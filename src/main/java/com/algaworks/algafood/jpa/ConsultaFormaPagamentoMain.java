package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodJpaApiApplication;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

public class ConsultaFormaPagamentoMain {

	public static void main(String[] args) {
		//interface pra desenvolver em spring gerencia o contexto da aplicação não web
		//para iniciar a aplicação não web
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodJpaApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		//a partir do ApplicationContext, consigo pegar o Bean
		
		FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);
		List<FormaPagamento> formasPagamento = formaPagamentoRepository .listar();
		
		for(FormaPagamento formaPagamento : formasPagamento) {
			System.out.println(formaPagamento.getDescricao());
		}
	}
	
	
	
}


//iniciar aplicação a partir dessa classe - Run