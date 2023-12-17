package rmi.uploadFile;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        UploadImpl upload = new UploadImpl();
        Registry reg = LocateRegistry.createRegistry(12345);

        reg.bind("UPLOAD", upload);

    }
}
