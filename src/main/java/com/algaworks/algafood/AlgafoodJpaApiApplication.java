package com.algaworks.algafood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.algaworks.algafood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class) //ative os repositories JPA; senão, não funciona o CustomJpaRepository
public class AlgafoodJpaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgafoodJpaApiApplication.class, args);
	}

}
