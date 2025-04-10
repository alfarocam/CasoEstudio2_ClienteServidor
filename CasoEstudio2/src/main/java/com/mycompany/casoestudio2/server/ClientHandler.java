package com.mycompany.casoestudio2.server;

import com.mycompany.casoestudio2.farm.Animal;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {
    private Socket clienteSocket;
    private AnimalController animalController = new AnimalController();
    
    public ClientHandler (Socket socket) {
        this.clienteSocket = socket;
    }
    
    @Override
    public void run () {
        try (PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()))) {
            
            String inputLine;
            while ((inputLine = in.readLine ()) != null) {
                 System.out.println("La peticion del cliente es: " + inputLine);
                 String response = processRequest(inputLine);
                 out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clienteSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
  }
}
    
