package interfaces;

import java.io.IOException;
import java.net.DatagramSocket;

public interface ICommon
{
    String ReceberPacote(byte[] dados, DatagramSocket socket) throws IOException;
}
