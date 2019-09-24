package by.clevertec.WebApplication;

import by.clevertec.WebApplication.configs.YAMLConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApplication implements CommandLineRunner {

	private final YAMLConfig yamlConfig;

	public WebApplication(YAMLConfig yamlConfig) {
		this.yamlConfig = yamlConfig;
	}

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	public void run(String... args){
	}
}
