package rmi.uploadFile;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class UploadImpl extends UnicastRemoteObject implements RemoteInterface {
    private static int ssid = 0;
    private HashMap<Integer, OutputStream> sessions;

    protected UploadImpl() throws RemoteException {
        sessions = new HashMap<>();
    }

    @Override
    public boolean authUsername(String username) throws RemoteException {
        for (User usr : UserData.getAll()) {
            if (usr.getUsername().equalsIgnoreCase(username))
                return true;
        }
        return false;
    }

    @Override
    public int authUser(String username, String password) throws RemoteException {
        for (User usr : UserData.getAll()) {
            if (usr.getUsername().equalsIgnoreCase(username) && usr.getPassword().equals(password)) {
                ssid++;
                return ssid;
            }
        }
        return -1;
    }

    @Override
    public void createFile(String fileName, int ssid) throws RemoteException {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName));

            sessions.put(ssid, bos);

        } catch (FileNotFoundException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public boolean transferFile(byte[] data, int ssid) throws IOException {
        if (sessions.containsKey(ssid)) {
            try {
                OutputStream os = sessions.get(ssid);

                os.write(data);

                return true;
            } catch (IOException e) {
                throw new RemoteException(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean closeTransfer(int ssid) throws RemoteException {
        if (sessions.containsKey(ssid)) {
            OutputStream os = sessions.get(ssid);
            try {
                os.close();
                sessions.remove(ssid);
                return true;
            } catch (IOException e) {
                throw new RemoteException(e.getMessage());
            }
        }
        return false;
    }
}
