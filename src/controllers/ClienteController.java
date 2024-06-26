package controllers;

import java.io.IOException;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

public class ClienteController {
    public static void main(String[] args) throws IOException {
        Random random = new Random();
        final int PORT = random.nextInt(6001, 15000);
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
            System.out.print("Escreva o IP: ");
            ip = scanner.nextLine();
            
            endereco = InetAddress.getByName(ip);

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
                    vitorias = derrotas = empates = 0;
                }
                
                System.out.println("\n\nSelecione o modo de jogo \n1-Contra máquina\n2-PvP");

                mensagem = scanner.nextLine();
                byte[] msgByte = mensagem.getBytes();
                DatagramPacket PacoteModoJogo = new DatagramPacket(msgByte, msgByte.length, endereco, 6000);
                clientSocket.send(PacoteModoJogo);

                if (mensagem.equals("1")) {
                    
                    byte[] msgPort = String.valueOf(PORT).getBytes();
                    DatagramPacket porta = new DatagramPacket(msgPort, msgPort.length, endereco, 6000);
                    clientSocket.send(porta);

                    while (!mensagem.equalsIgnoreCase("exit") && !mensagem.equalsIgnoreCase("trocar")) {

                        System.out.print("\nSua jogada: ");
                        mensagem = scanner.nextLine();

                        byte[] jogada = mensagem.getBytes();
                        DatagramPacket pacote = new DatagramPacket(jogada, jogada.length, endereco, PORT);
                        clientSocket.send(pacote);
                        
                        byte[] dadosRecebidos = new byte[1024];
                        pacote = new DatagramPacket(dadosRecebidos, dadosRecebidos.length, endereco, PORT);
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

                    System.out.print("Escolha a porta: ");
                    String portPVP = scanner.next();
                    msgByte = String.valueOf(portPVP).getBytes();
                    DatagramPacket portaParaJogar = new DatagramPacket(msgByte, msgByte.length, endereco, 6000);
                    clientSocket.send(portaParaJogar);

                    while(!mensagem.equalsIgnoreCase("exit") && !mensagem.equalsIgnoreCase("trocar")) {
                        mensagem = scanner.nextLine();

                        msgByte = String.valueOf(mensagem).getBytes();
                        DatagramPacket jogada = new DatagramPacket(msgByte, msgByte.length, endereco, Integer.parseInt(portPVP));
                        clientSocket.send(jogada);

                    }
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        scanner.close();
        clientSocket.close();
    }
}