package service;

import interfaces.ICommon;
import interfaces.IServidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
                if (mensagem.equals("1")) {
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
                }
                else if (mensagem.equals("2")) {
                    ArrayList<String> jog = new ArrayList<String>();

                    ServerSocket servidor = new ServerSocket(PORT);
                    System.out.println("Servidor ouvindo a porta: " + PORT);

                    while(true) {
                        ArrayList<Socket> jogadores = new ArrayList<Socket>();

                        while (jog.size() < 2) {
                            Socket jogador = servidor.accept();
                            ObjectInputStream entrada = new ObjectInputStream(jogador.getInputStream());
                            jog.add((String) entrada.readObject());
                            jogadores.add(jogador);
                        }

                        String msg = jokenpoPvp(jog.get(0), jog.get(1));
                        Socket respostaCliente = new Socket(jogadores.get(0).getInetAddress(), jogadores.get(0).getPort()+2);
                        ObjectOutputStream saida = new ObjectOutputStream(respostaCliente.getOutputStream());
                        saida.writeObject(msg);

                        msg = jokenpoPvp(jog.get(1), jog.get(0));
                        respostaCliente = new Socket(jogadores.get(1).getInetAddress(), jogadores.get(1).getPort()+2);
                        saida = new ObjectOutputStream(respostaCliente.getOutputStream());
                        saida.writeObject(msg);


                        jogadores.clear();
                        jog.clear();
                    }


                    // DatagramPacket jogada = new DatagramPacket(dados, dados.length);
                    // serverSocket.receive(jogada);
                    // String tal = new String(jogada.getData(), 0, jogada.getLength());
                    }                    

                }
          
        } catch (Exception e) {
            System.out.println("erro no servidorService: " + e.getMessage());
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
            return "Os dois jogaram o mesmo elemento \n---EMPATE!---";
        }
        else if (jogada.equalsIgnoreCase("pedra") && jogada2.equalsIgnoreCase("tesoura") ||
                    jogada.equalsIgnoreCase("papel") && jogada2.equalsIgnoreCase("pedra") ||
                    jogada.equalsIgnoreCase("tesoura") && jogada2.equalsIgnoreCase("papel")) {
                return "Você jogou: " + jogada + "\nSeu oponente jogou: " + jogada2 + "\n---VOCÊ GANHOU!!---";
        } 
        else if (jogada2.equalsIgnoreCase("pedra") && jogada.equalsIgnoreCase("tesoura") ||
                    jogada2.equalsIgnoreCase("papel") && jogada.equalsIgnoreCase("pedra") ||
                    jogada2.equalsIgnoreCase("tesoura") && jogada.equalsIgnoreCase("papel")) {
            return "Você jogou: " + jogada + "\nSeu oponente jogou: " + jogada2 + "\n---Você perdeu...";
        }
        else if(jogada.equalsIgnoreCase("trocar")|| jogada.equalsIgnoreCase("exit")){
            return"\n";
        }
        else {
            return "Jogada não encontrada \ntente novamente";
        }
    }



}