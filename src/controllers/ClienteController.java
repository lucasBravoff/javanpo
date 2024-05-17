package controllers;

import java.io.IOException;
import java.net.*;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteController {
    public static void main(String args[]) {
        final int PORT = 6000;
        DatagramSocket clientSocket;
        InetAddress endereco;
        Scanner scanner = new Scanner(System.in);


        try{
            clientSocket = new DatagramSocket();
            endereco = InetAddress.getByName("127.0.0.1");
        }catch (SocketException e){
            e.printStackTrace();
            scanner.close();
            return;
        }
        catch (UnknownHostException e){
            e.printStackTrace();
            scanner.close();
            return;
        }
        String mensagem = " ";
        System.out.println("Seja bem vindo ao jogo javampô, escreva 'exit' para sair");
        
        try {
            while (!mensagem.equalsIgnoreCase("exit")) {
                System.out.print("\nSua jogada: ");
                mensagem = scanner.nextLine();

                byte[] dados = mensagem.getBytes();
                byte[] dadosRecebidos = new byte[1024];
                DatagramPacket pacote = new DatagramPacket(dados, dados.length, endereco, PORT);
                clientSocket.send(pacote);
                pacote = new DatagramPacket(dadosRecebidos, dadosRecebidos.length);
                clientSocket.receive(pacote);
                String respostaServidor = new String(pacote.getData(), 0, pacote.getLength());
                System.out.println(respostaServidor);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        scanner.close();
        clientSocket.close();
    }
}