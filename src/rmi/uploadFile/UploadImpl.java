package rmi.uploadFile;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class UploadImpl extends UnicastRemoteObject implements RemoteInterface {
    private static int ssid = 0;
    private HashMap<Integer, OutputStream> sessionOut;
    private HashMap<Integer, InputStream> sessionIn;

    protected UploadImpl() throws RemoteException {
        sessionOut = new HashMap<>();
        sessionIn = new HashMap<>();
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

            sessionOut.put(ssid, bos);

        } catch (FileNotFoundException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public boolean transferFile(byte[] data, int ssid) throws IOException {
        if (sessionOut.containsKey(ssid)) {
            try {
                OutputStream os = sessionOut.get(ssid);

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
        if (sessionOut.containsKey(ssid)) {
            OutputStream os = sessionOut.get(ssid);
            try {
                os.close();
                sessionOut.remove(ssid);
                return true;
            } catch (IOException e) {
                throw new RemoteException(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public byte[] getDataFromServer(String pathFromServer, int ssid) throws RemoteException {
        InputStream is;
        try {
            if (!sessionIn.containsKey(ssid)) {
                is = new BufferedInputStream(new FileInputStream(pathFromServer));
                sessionIn.put(ssid, is);

            } else {
                is = sessionIn.get(ssid);
            }
        } catch (FileNotFoundException e) {
            throw new RemoteException(e.getMessage());
        }

        byte[] data = new byte[102400];
        int bytesRead;

        try {
            if ((bytesRead = is.read(data)) != -1) {
                byte[] fitData = new byte[bytesRead];
                System.arraycopy(data, 0, fitData, 0, bytesRead);

                System.out.println("data not null");
                return fitData;

            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RemoteException(e.getMessage());
        }
    }
}
