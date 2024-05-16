package controllers;//Atenção aos Imports
import service.ServidorService;

import java.io.FileReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ServidorController {

    public static void main(String[] args) {
        // Responsável pelo Registro e pelo Binder
        Registry registry;

        try {

            // Instância do Servidor Implementado
            ServidorService m = new ServidorService();

            registry = LocateRegistry.createRegistry(11099);
            registry.rebind("Servidor", m);
            System.out.println("Servidor no Ar...");
            while (true) {
                Scanner scanner = new Scanner(System.in);
               // String resposta = m.jokenpo(scanner.next());
                //System.out.println(resposta);
                System.out.println("OLA");
            }
            // crio uma instancia do obj e registro no Binder
            //Naming.rebind("rmi://localhost:1099/Servidor", m);
        } catch( Exception e ) {
            System.out.println( "Trouble: " + e.getMessage() );
        }
    }
}