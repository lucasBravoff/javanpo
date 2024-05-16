package controllers;

import service.ServidorService;

import java.rmi.*;

public class ClienteController {
    public static void main(String args[]) {
        try {
            ServidorService m = (ServidorService) Naming.lookup("rmi://localhost:11099/Servidor");
        } catch (Exception e) {
            System.out.println("GenericException: " + e.toString());
        }
    }
}