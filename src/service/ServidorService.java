package service;

import interfaces.IServidor;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;// necessito do UnicastRemoteObject � Objeto Remoto Unicast
import java.util.Random;
import java.util.Scanner;

public class ServidorService extends UnicastRemoteObject implements IServidor {

    public ServidorService() throws RemoteException {
        super();
    }

    public String jokenpo(String jogada){
        jogada = jogada.toLowerCase();
        String[] possibilidades = {"pedra", "papel", "tesoura"};
        Random rand = new Random();

        String jogadaMaquina = possibilidades[rand.nextInt(3)];
        if(jogada.equals(jogadaMaquina)) {
            return "EMPATE!";
        }
        else if (jogada.equals("pedra") && jogadaMaquina.equals("tesoura") ||
                    jogada.equals("papel") && jogadaMaquina.equals("pedra") ||
                    jogada.equals("tesoura") && jogadaMaquina.equals("papel")) {
                return "Você ganhou!";
        } else {
                return "Você perdeu!";
        }

    }

}