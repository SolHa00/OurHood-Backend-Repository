package hello.photo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PhotoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoApplication.class, args);
	}

}
