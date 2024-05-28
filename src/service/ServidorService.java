package service;

import interfaces.IServidor;
import java.rmi.RemoteException;// necessito do UnicastRemoteObject � Objeto Remoto Unicast
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class ServidorService extends UnicastRemoteObject implements IServidor {

    public ServidorService() throws RemoteException {
        super();
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
            return "Jogada não encontrada, tente novamente";
        }

    }



}