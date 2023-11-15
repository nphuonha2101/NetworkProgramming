package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class ServerThread implements Runnable {
	private Socket client;

	public ServerThread(Socket client) {
		this.client = client;
	}

	private String doMathOperationFromCmd(String command) {
		String result = "";
		
		// validate operator
		if (!validate(command).equals("done"))
			return validate(command);

		else {
			StringTokenizer token = new StringTokenizer(command, " ");
			int operand1 = Integer.parseInt(token.nextToken());
			String operator = token.nextToken();
			int operand2 = Integer.parseInt(token.nextToken());

			
			switch (operator) {
			case "+":
				result =  String.valueOf(operand1 + operand2);
				break;
			case "-":
				result = String.valueOf(operand1 - operand2);
				break;
			case "*":
				result = String.valueOf(operand1 * operand2);
				break;
			case "/": 
				result =  String.valueOf(operand1 / operand2) + " remain = " + String.valueOf(operand1 % operand2);
				break;
			}
		}
		return command + " = " + result;

	}

	public String handleCommand(String command) throws IOException {
		String[] commandArr = command.split(" ");

		if (commandArr.length == 1 && commandArr[0].toLowerCase().equals("exit")) {
			client.close();
			return "exited";
		} else {
			return doMathOperationFromCmd(command);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
				String command = reader.readLine();

				String result = handleCommand(command);
				if (result.equals("exited"))
					break;

				PrintWriter out = new PrintWriter(client.getOutputStream());
				out.println(result);
				out.flush();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public String validate(String command) {
		String[] cmdArr = command.split(" ");
		if (cmdArr.length < 3)
			return "You must prompt two operands and one operator separated by space. Example: 1 + 2";
		if (cmdArr.length > 3)
			return "You prompt wrong format";
		else {
			if (!"+-*/".contains(cmdArr[1]))
				return "Operator not exist.";
			if (cmdArr[2].equals("0") && cmdArr[1].equals("/"))
				return "You must not divide for 0";
		}

		return "done";
	}

}
