package service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import interfaces.ICommon;

public class CommonService implements ICommon {

    public String ReceberPacote(byte[] dados, DatagramSocket socket) throws IOException {
        try {
            DatagramPacket pacote = new DatagramPacket(dados, dados.length);
            socket.receive(pacote);
            String mensagem = new String(pacote.getData(), 0, pacote.getLength());
            return mensagem;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
