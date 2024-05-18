package controllers;

import service.ServidorService;

import java.io.IOException;
import java.net.*;
import java.rmi.RemoteException;

public class ServidorController {

    public static void main(String[] args) throws RemoteException {
        final int PORT = 6000;
        byte[] dados = new byte[1024];
        DatagramSocket serverSocket;
        InetAddress endereco;
        ServidorService m = new ServidorService();

        try {
            serverSocket = new DatagramSocket(PORT);
            endereco = InetAddress.getByName("127.0.0.1");
            System.out.println("Server online ;D");
            while (true) {
                DatagramPacket pacote = new DatagramPacket(dados, dados.length);
                serverSocket.receive(pacote);

                String mensagem = new String(pacote.getData(), 0, pacote.getLength());
                String resposta = m.jokenpo(mensagem);
                System.out.println(resposta);

                byte[] respostaCliente = resposta.getBytes();
                DatagramPacket pacoteEnviar = new DatagramPacket(respostaCliente, respostaCliente.length, pacote.getAddress(), pacote.getPort());
                serverSocket.send(pacoteEnviar);
            }
        } catch (SocketException e) {
            System.out.println("Erro ao criar socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error");
            return;
        }

    }
}
