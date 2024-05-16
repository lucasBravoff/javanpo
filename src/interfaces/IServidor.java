package interfaces;

import java.io.FileNotFoundException;
import java.rmi.*; // importa pacotes do RMI

public interface IServidor extends Remote
{
    public String jokenpo(String jogada);
}
