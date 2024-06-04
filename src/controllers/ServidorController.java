package controllers;

import interfaces.ICommon;
import interfaces.IServidor;
import java.io.IOException;
import java.net.*;
import java.rmi.RemoteException;
import service.CommonService;
import service.ServidorService;

public class ServidorController {

    private IServidor _servidor;

    public ServidorController(IServidor servidor) {
        _servidor = servidor;
    }

    public static void main(String[] args) throws RemoteException {
        final int PORT = 6000;
        byte[] dados = new byte[1024];
        DatagramSocket serverSocket;
        ICommon _common = new CommonService();
        IServidor _servidor = new ServidorService();

        try {
            serverSocket = new DatagramSocket(PORT);
            System.out.println("Server online ;D");

            while (true) {       
                DatagramPacket pacoteModoJogo = new DatagramPacket(dados, dados.length);
                serverSocket.receive(pacoteModoJogo);
                String mensagem = new String(pacoteModoJogo.getData(), 0, pacoteModoJogo.getLength());
                
                if (mensagem.equals("1")) {
                    DatagramPacket porta = new DatagramPacket(dados, dados.length);
                    serverSocket.receive(porta);
                    String port = new String(porta.getData(), 0, porta.getLength());

                    System.out.println(port);

                    ServidorService newGame = new ServidorService();
                    new Thread(() -> {
                        newGame.createGame(Integer.parseInt(port), dados, mensagem, _common, _servidor);
                }).start();
                }
                else if (mensagem.equals("2")) {
                    DatagramPacket pacote = new DatagramPacket(dados, dados.length);
                    serverSocket.receive(pacote);
                    int portaParaJogar = Integer.parseInt(new String(pacote.getData(), 0, pacote.getLength()));
                    
                    System.out.println("Porta pvp: " + portaParaJogar);

                    ServidorService newGame = new ServidorService();
                    new Thread(() -> {
                        newGame.createGame(portaParaJogar, dados, mensagem, _common, _servidor);
                }).start();
                    
                }
 
            }

                
        } catch (SocketException e) {
            System.out.println("Erro ao criar socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error");
        }

    }
}
