package io.github.ygormacedo.Payments.authorizer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AuthorizeServer {

    private final int port;
    private boolean running;

    public AuthorizeServer(int port){
        this.port = port;
        this.running = true;
    }

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Authorizer server started on port " + port);

            while (running){
                Socket clientSocket = serverSocket.accept();
                new Thread(new AuthorizerHandler(clientSocket)).start();
            }
        } catch (IOException e){
            System.err.println("Error in Authorizer Server: " + e.getMessage());
        }
    }

    public void stop(){
        running = false;
    }

}
