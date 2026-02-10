package br.com.hendersonkleber.movie_catalog;

import br.com.hendersonkleber.movie_catalog.repository.ReviewRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = { ReviewRepository.class })
public class MovieCatalogApplication {
	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogApplication.class, args);
	}
}
