package io.github.ygormacedo.Payments.authorizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AuthorizerHandler implements Runnable {

    private final Socket clientSocket;

    public AuthorizerHandler(Socket socket){
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {

                ISO8583Message request = new ISO8583Message(inputLine);
                ISO8583Message response = processAuthorization(request);

                out.println(response.toString());

            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }

        private ISO8583Message processAuthorization(ISO8583Message request) {

            String mti = request.getMTI();
            String amountStr = request.getField(4);
            double amount = Double.parseDouble(amountStr);

            ISO8583Message response = new ISO8583Message();
            response.setMTI("0210");

            response.setField(4, request.getField(4)); // Valor
            response.setField(7, request.getField(7)); // Data/Hora GMT
            response.setField(11, request.getField(11)); // NSU
            response.setField(12, request.getField(12)); // Hora local
            response.setField(13, request.getField(13)); // Data local
            response.setField(42, request.getField(42)); // Código do estabelecimento


            if (amount > 1000) {
                try {
                    Thread.sleep(10000); // Simula timeout
                    return null; // Não responde
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }


            if (amount % 2 == 0) {
                response.setField(38, generateAuthCode()); // Código de autorização
                response.setField(39, "000"); // Código de resposta (aprovado)
            }

            else {
                response.setField(39, "051"); // Código de resposta (negado)
            }

            response.setField(127, generatePaymentId()); // NSU Host

            return response;
        }

        private String generateAuthCode() {
            return String.format("%06d", (int)(Math.random() * 1000000));
        }

        private String generatePaymentId() {
            return "PAY" + System.currentTimeMillis();

    }
}
