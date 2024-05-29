package controllers;

import java.io.IOException;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

public class ClienteController {
    public static void main(String[] args) throws IOException {
        Random random = new Random();
        final int PORT = random.nextInt(6001, 7000);
        byte[] dados = new byte[1024];
        DatagramSocket clientSocket;
        InetAddress endereco;
        Scanner scanner = new Scanner(System.in);
        int vitorias = 0;
        int derrotas = 0;
        int empates = 0;

        try {
            clientSocket = new DatagramSocket();
            String ip = "";
            System.out.print("Escreve o IPae vagabssssss: ");
            ip = scanner.nextLine();
            
            endereco = InetAddress.getByName(ip);

            System.out.println(PORT);

            byte[] msgByte = String.valueOf(PORT).getBytes();
            DatagramPacket portaParaJogar = new DatagramPacket(msgByte, msgByte.length, endereco, 6000);
            clientSocket.send(portaParaJogar);

        } catch (SocketException | UnknownHostException e){
            e.printStackTrace();
            scanner.close();
            return;
        }
        String mensagem = " ";
        System.out.println("Seja bem vindo ao jogo javampô, escreva 'exit' para sair");

        while (!mensagem.equalsIgnoreCase("exit")) {
            try {
                if (mensagem.equalsIgnoreCase("trocar")) {
                    System.out.print("Jogo finalizado\nVitorias: " + vitorias + "\nDerrotas: " + derrotas + "\nEmpates: " + empates);
                }
                
                System.out.println("\n\nSelecione o modo de jogo \n1-Contra máquina\n2-PvP");

                mensagem = scanner.nextLine();
                byte[] msgByte = mensagem.getBytes();
                DatagramPacket PacoteModoJogo = new DatagramPacket(msgByte, msgByte.length, endereco, PORT);
                clientSocket.send(PacoteModoJogo);

                if (mensagem.equals("1")) {
                    while (!mensagem.equalsIgnoreCase("exit") && !mensagem.equalsIgnoreCase("trocar")) {
                        System.out.print("\nSua jogada: ");
                        mensagem = scanner.nextLine();

                        dados = mensagem.getBytes();
                        byte[] dadosRecebidos = new byte[1024];
                        DatagramPacket pacote = new DatagramPacket(dados, dados.length, endereco, PORT);
                        clientSocket.send(pacote);
                        pacote = new DatagramPacket(dadosRecebidos, dadosRecebidos.length);
                        clientSocket.receive(pacote);
                        String respostaServidor = new String(pacote.getData(), 0, pacote.getLength());

                        if (mensagem.equalsIgnoreCase("trocar") || mensagem.equalsIgnoreCase("Jogada não encontrada \ntente novamente")) {
                            // faz nada sô
                        }
                        else if ("---Você ganhou!".equals(respostaServidor.split("\n")[1])) {
                            vitorias++;
                        } 
                        else if ("---Você perdeu!".equals(respostaServidor.split("\n")[1])) {
                            derrotas++;
                        } 
                        else if ("---EMPATE!".equals(respostaServidor.split("\n")[1])) {
                            empates++;
                        }

                        System.out.println(respostaServidor);
                    }
                } 
                
                else if (mensagem.equals("2")) {
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        scanner.close();
        clientSocket.close();
    }
}