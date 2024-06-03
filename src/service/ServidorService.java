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
                    while (!mensagem.equalsIgnoreCase("trocar") && !mensagem.equalsIgnoreCase("exit")) {
                        DatagramPacket jogada = new DatagramPacket(dados, dados.length);
                        serverSocket.receive(jogada);
                        String respJogada = new String(jogada.getData(), 0, jogada.getLength());
                        
                        String resposta = jokenpo(respJogada);

                        // Criar um novo DatagramPacket para enviar a resposta de volta para o cliente
                        byte[] respostaCliente = resposta.getBytes();
                        DatagramPacket pacoteEnviar = new DatagramPacket(respostaCliente, respostaCliente.length, jogada.getAddress(), jogada.getPort());
                        serverSocket.send(pacoteEnviar);
                }
                // else if (mensagem.equals("2")) {
                //     System.out.println("entrou no if 2");
                //     DatagramSocket player1Socket = new DatagramSocket(PORT);
                //     DatagramSocket player2Socket = new DatagramSocket(PORT);

                //     DatagramPacket receberPlayer1 = new DatagramPacket(dados, dados.length, pacoteModoJogo.getAddress(), pacoteModoJogo.getPort());
                //     DatagramPacket receberPlayer2 = new DatagramPacket(dados2, dados2.length, player2.getAddress(), pacoteModoJogo.getPort());

                //     player1Socket.receive(receberPlayer1);
                //     player2Socket.receive(receberPlayer2);

                //     System.out.println("Player1: " + receberPlayer1 + "\nPlayer2: " + receberPlayer2);

                //     String resultadoPVP = jokenpoPvp(String.valueOf(receberPlayer1), String.valueOf(receberPlayer2));

                //     byte[] respostaCliente = resultadoPVP.getBytes();
                //     DatagramPacket pacoteEnviar = new DatagramPacket(respostaCliente, respostaCliente.length, receberPlayer1.getAddress(), receberPlayer1.getPort());
                //     DatagramPacket pacoteEnviarP2 = new DatagramPacket(respostaCliente, respostaCliente.length, receberPlayer2.getAddress(), receberPlayer2.getPort());
                //     serverSocket.send(pacoteEnviar);
                //     serverSocket.send(pacoteEnviarP2);

                //     System.out.println(resultadoPVP);
                // }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            return "Os dois jogaram o mesmo elemento \n---EMPATE!";
        }
        else if (jogada.equalsIgnoreCase("pedra") && jogada2.equalsIgnoreCase("tesoura") ||
                    jogada.equalsIgnoreCase("papel") && jogada2.equalsIgnoreCase("pedra") ||
                    jogada.equalsIgnoreCase("tesoura") && jogada2.equalsIgnoreCase("papel")) {
                return "Jogador 1 jogou: " + jogada + "\nJogador 1 jogou: " + jogada2 + "---Jogador 1 ganhou!";
        } 
        else if (jogada2.equalsIgnoreCase("pedra") && jogada.equalsIgnoreCase("tesoura") ||
                    jogada2.equalsIgnoreCase("papel") && jogada.equalsIgnoreCase("pedra") ||
                    jogada2.equalsIgnoreCase("tesoura") && jogada.equalsIgnoreCase("papel")) {
                return "Jogador 1 jogou: " + jogada + "\nJogador 1 jogou: " + jogada2 + "---Jogador 2 ganhou";
        }
        else if(jogada.equalsIgnoreCase("trocar")|| jogada.equalsIgnoreCase("exit")){
            return"\n";
        }
        else {
            return "Jogada não encontrada \ntente novamente";
        }
    }



}