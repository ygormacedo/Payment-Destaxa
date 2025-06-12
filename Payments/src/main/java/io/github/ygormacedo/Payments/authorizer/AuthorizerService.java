package io.github.ygormacedo.Payments.authorizer;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Service
public class AuthorizerService {

    private static final String AUTHORIZER_HOST = "localhost";
    private static final int AUTHORIZER_PORT = 3000;

    public ISO8583Message sendToAuthorizer(ISO8583Message message) {
        try (Socket socket = new Socket(AUTHORIZER_HOST, AUTHORIZER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Envia a mensagem para o autorizador
            out.println(message.toString());

            // Espera a resposta (com timeout)
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < 5000) { // 5 segundos de timeout
                if (in.ready()) {
                    String response = in.readLine();
                    return new ISO8583Message(response);
                }
                Thread.sleep(100);
            }

            return null; // Timeout
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}
