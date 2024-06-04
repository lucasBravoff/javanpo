package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import interfaces.IClientHandler;

public class ClientHandlerService extends Thread implements IClientHandler{
    private Socket clientSocket;

    public ClientHandlerService(Socket clientSocket) {
    }

    public void ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Recebido do cliente: " + inputLine);
                out.println("Echo: " + inputLine); // Envia de volta uma resposta ao cliente
            }
        } catch (IOException e) {
            System.out.println("Erro ao comunicar com o cliente: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
