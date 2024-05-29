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
        String mensagem = "";

        try {
            serverSocket = new DatagramSocket(PORT);
            System.out.println("Server online ;D");

            while (true) { 

                DatagramPacket pacote = new DatagramPacket(dados, dados.length);
                System.out.println("Esperando porta");
                serverSocket.receive(pacote);
                int portaParaJogar = Integer.parseInt(new String(pacote.getData(), 0, pacote.getLength()));
                System.out.println(portaParaJogar);
                
                new Thread(() -> {
                    ServidorService newGame = null;
                    try {
                        newGame = new ServidorService();
                    } catch (RemoteException ex) {
                    }
                    newGame.createGame(portaParaJogar, dados, mensagem, _common, _servidor);
                }).start();
            }

                
        } catch (SocketException e) {
            System.out.println("Erro ao criar socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error");
        }

    }
}
