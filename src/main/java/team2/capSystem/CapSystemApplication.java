package team2.capSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


import team2.capSystem.services.SeedDBService;

@SpringBootApplication
public class CapSystemApplication {


	public static void main(String[] args) {
		SpringApplication.run(CapSystemApplication.class, args);		
	}

	@Component
	public class CommandLineAppStartupRunner implements CommandLineRunner {
	
		@Autowired
		private SeedDBService seedDBService;

		@Override
		public void run(String...args) throws Exception {
			seedDBService.databaseInit();
	
		}
	}
}
