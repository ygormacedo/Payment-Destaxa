package io.github.ygormacedo.Payments;

import io.github.ygormacedo.Payments.authorizer.AuthorizeServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentsApplication.class, args);

		new Thread(() -> {
			AuthorizeServer server = new AuthorizeServer(3000);
			server.start();
		}).start();
	}

}
