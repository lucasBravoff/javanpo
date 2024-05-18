package controllers;

import service.ServidorService;

import java.io.IOException;
import java.net.*;
import java.rmi.RemoteException;

public class ServidorController {

    public static void main() throws RemoteException {
        final int PORT = 6000;
        byte[] dados = new byte[1024];
        DatagramSocket serverSocket;
        ServidorService m = new ServidorService();

        try {
            serverSocket = new DatagramSocket(PORT);
            System.out.println("Server online ;D");
            while (true) {
                DatagramPacket pacote = new DatagramPacket(dados, dados.length);
                serverSocket.receive(pacote);

                String mensagem = new String(pacote.getData(), 0, pacote.getLength());
                String resposta = m.jokenpo(mensagem);
                System.out.println(resposta);

                // Criar um novo DatagramPacket para enviar a resposta de volta para o cliente
                byte[] respostaCliente = resposta.getBytes();
                DatagramPacket pacoteEnviar = new DatagramPacket(respostaCliente, respostaCliente.length, pacote.getAddress(), pacote.getPort());
                serverSocket.send(pacoteEnviar);
            }
        } catch (SocketException e) {
            System.out.println("Erro ao criar socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error");
        }

    }
}
