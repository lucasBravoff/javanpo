package service;

import interfaces.ICommon;
import interfaces.IServidor;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;// necessito do UnicastRemoteObject � Objeto Remoto Unicast
import java.util.Random;

public class ServidorService extends UnicastRemoteObject implements IServidor {

    public ServidorService() throws RemoteException {
        super();
    }

    public void createGame(int PORT, byte[] dados, String mensagem, ICommon _common, IServidor _servidor) {
        
        try {
            DatagramSocket serverSocket;
            serverSocket = new DatagramSocket(PORT);
             while (!mensagem.equalsIgnoreCase("exit")) {

                DatagramPacket pacoteModoJogo = new DatagramPacket(dados, dados.length);
                serverSocket.receive(pacoteModoJogo);
                mensagem = new String(pacoteModoJogo.getData(), 0, pacoteModoJogo.getLength());
                
                System.out.println(mensagem);

                //String mensagem = _common.ReceberPacote(dados, serverSocket);
                if (mensagem.equals("1")) {
                    while (!mensagem.equalsIgnoreCase("trocar") && !mensagem.equalsIgnoreCase("exit")) {
                        mensagem = _common.ReceberPacote(dados, serverSocket);

                        String resposta = _servidor.jokenpo(mensagem);

                        // Criar um novo DatagramPacket para enviar a resposta de volta para o cliente
                        byte[] respostaCliente = resposta.getBytes();
                        DatagramPacket pacoteEnviar = new DatagramPacket(respostaCliente, respostaCliente.length, pacoteModoJogo.getAddress(), pacoteModoJogo.getPort());
                        serverSocket.send(pacoteEnviar);
                    }

                }
            }
        } catch (Exception e) {
        }
       
    }

    @Override
    public String jokenpo(String jogada){
        jogada = jogada.toLowerCase().strip();
        String[] possibilidades = {"pedra", "papel", "tesoura"};
        Random rand = new Random();

        String jogadaMaquina = possibilidades[rand.nextInt(3)];
        if(jogada.equals(jogadaMaquina)) {
            return "Máquina jogou: " + jogadaMaquina + "\n---EMPATE!";
        }
        else if (jogada.equals("pedra") && jogadaMaquina.equals("tesoura") ||
                    jogada.equals("papel") && jogadaMaquina.equals("pedra") ||
                    jogada.equals("tesoura") && jogadaMaquina.equals("papel")) {
                return "Máquina jogou: " + jogadaMaquina + "\n---Você ganhou!";
        } else if (jogadaMaquina.equals("pedra") && jogada.equals("tesoura") ||
                    jogadaMaquina.equals("papel") && jogada.equals("pedra") ||
                    jogadaMaquina.equals("tesoura") && jogada.equals("papel")) {
                return "Máquina jogou: " + jogadaMaquina + "\n---Você perdeu!";
        }
        else if(jogada.equalsIgnoreCase("trocar")|| jogada.equalsIgnoreCase("exit")){
            return"\n";
        }
        else {
            return "Jogada não encontrada \ntente novamente";
        }

    }

    public String jokenpoPvp(String jogada, String jogada2) {
        if(jogada.equalsIgnoreCase(jogada2)) {
            return "Seu oponente: " + jogada2 + "\n---EMPATE!";
        }
        else if (jogada.equalsIgnoreCase("pedra") && jogada2.equalsIgnoreCase("tesoura") ||
                    jogada.equalsIgnoreCase("papel") && jogada2.equalsIgnoreCase("pedra") ||
                    jogada.equalsIgnoreCase("tesoura") && jogada2.equalsIgnoreCase("papel")) {
                return "Oponente jogou: " + jogada2 + "\n---Você ganhou!";
        } 
        else if (jogada2.equalsIgnoreCase("pedra") && jogada.equalsIgnoreCase("tesoura") ||
                    jogada2.equalsIgnoreCase("papel") && jogada.equalsIgnoreCase("pedra") ||
                    jogada2.equalsIgnoreCase("tesoura") && jogada.equalsIgnoreCase("papel")) {
                return "Oponente jogou: " + jogada2 + "\n---Você perdeu!";
        }
        else if(jogada.equalsIgnoreCase("trocar")|| jogada.equalsIgnoreCase("exit")){
            return"\n";
        }
        else {
            return "Jogada não encontrada \ntente novamente";
        }
    }



}