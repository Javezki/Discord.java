package factionai.java.discord;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DiscordApplication {

	public static void main(String[] args) {
		// Start spring application
		new SpringApplicationBuilder(DiscordApplication.class)
				.build()
				.run(args);
	}

}
