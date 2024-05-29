package controllers;

import interfaces.ICommon;
import interfaces.IServidor;
import java.io.IOException;
import java.net.*;
import java.rmi.RemoteException;
import java.util.Random;
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
                    new Thread(() -> {
                    ServidorService newGame = null;
                    try {
                        newGame = new ServidorService();
                    } catch (RemoteException ex) {
                    }
                    Random random = new Random();
                    int porta = random.nextInt(6001, 15000);
                    newGame.createGame(porta, dados, mensagem, _common, _servidor);
                }).start();
                }
                else if (mensagem.equals("2")) {
                    DatagramPacket pacote = new DatagramPacket(dados, dados.length);
                    serverSocket.receive(pacote);
                    int portaParaJogar = Integer.parseInt(new String(pacote.getData(), 0, pacote.getLength()));
                    
                    
                    
                }


                
                
            }

                
        } catch (SocketException e) {
            System.out.println("Erro ao criar socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error");
        }

    }
}
