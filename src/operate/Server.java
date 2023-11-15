package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket server;
	public static final int SERVER_PORT = 12347;
	
	public void startServer(int port) throws IOException {
		server = new ServerSocket(port);
		
		while (true) {
			Socket client = server.accept();
			
			new Thread(new ServerThread(client)).start();
			PrintWriter out = new PrintWriter(client.getOutputStream());
			out.println("Hello from server!");
			out.flush();
			
		}
	}
	
	public static void main(String[] args) throws IOException {
		Server server  = new Server();
		server.startServer(SERVER_PORT);
	}
}
