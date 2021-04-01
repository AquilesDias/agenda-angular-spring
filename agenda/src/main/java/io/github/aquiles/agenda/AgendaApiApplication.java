package io.github.aquiles.agenda;

import io.github.aquiles.agenda.model.entity.Contato;
import io.github.aquiles.agenda.model.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.NoRepositoryBean;

@SpringBootApplication
public class AgendaApiApplication {

	@Bean
	public CommandLineRunner commandLineRunner(@Autowired ContatoRepository contatoRepository){
		return args -> {
			Contato contato = new Contato();
			contato.setNome("Huck");
			contato.setEmail("huck@gmail.com");
			contato.setFavorito(false);

			contatoRepository.save(contato);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(AgendaApiApplication.class, args);
	}

}
