package rmi.uploadFile;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {
    boolean authUsername(String username) throws RemoteException;

    int authUser(String username, String password) throws RemoteException;

    void createFile(String fileName, int ssid) throws RemoteException;

    boolean transferFile(byte[] data, int ssid) throws IOException;

    boolean closeTransfer(int ssid) throws RemoteException;

    byte[] getDataFromServer(String pathFromServer, int ssid) throws RemoteException;
}
