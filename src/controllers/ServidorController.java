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

    public ServidorController(IServidor servidor){
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
            while (!mensagem.equalsIgnoreCase("exit")) {
                DatagramPacket pacoteModoJogo = new DatagramPacket(dados, dados.length);
                System.out.println("aguardando");
                serverSocket.receive(pacoteModoJogo);
                System.out.println("passou");
                mensagem = new String(pacoteModoJogo.getData(), 0, pacoteModoJogo.getLength());
                System.out.println(mensagem);

                //String mensagem = _common.ReceberPacote(dados, serverSocket);

                if (mensagem.equals("1")) {
                    while(!mensagem.equalsIgnoreCase("trocar") && !mensagem.equalsIgnoreCase("exit")) {
                        mensagem = _common.ReceberPacote(dados, serverSocket);

                        String resposta = _servidor.jokenpo(mensagem);
                        System.out.println(resposta);

                        // Criar um novo DatagramPacket para enviar a resposta de volta para o cliente
                        byte[] respostaCliente = resposta.getBytes();
                        DatagramPacket pacoteEnviar = new DatagramPacket(respostaCliente, respostaCliente.length, pacoteModoJogo.getAddress(), pacoteModoJogo.getPort());
                        serverSocket.send(pacoteEnviar);
                    }

                }
            }
        } catch (SocketException e) {
            System.out.println("Erro ao criar socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error");
        }

    }
}
