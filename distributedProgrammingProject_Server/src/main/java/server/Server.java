package server;

import service.imp.AllServiceImp;
import service.inteface.AllService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) throws Exception {
        Context context = new InitialContext();
        LocateRegistry.createRegistry(7101);

        AllService allService = new AllServiceImp();

        context.bind("rmi://LAPTOP-7ERSHT8P:7101/allService", allService);

        System.out.println("RMI Server is running...");
    }
}
