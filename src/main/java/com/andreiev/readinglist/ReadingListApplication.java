package com.andreiev.readinglist;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReadingListApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReadingListApplication.class, args);
	}


	@Bean
	public CommandLineRunner dataLoader(ReaderRepository repo) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				var reader = new Reader();
				reader.setUsername("craig");
				reader.setFullname("Craig Walls");
				reader.setPassword("$2a$12$tdBEv6D4use9G6lg0eJ.M.DvlouaHSbeLQ/U9vt.oD9n7RG74BdTa");
				repo.save(reader);
			}
		};
	}
}
