package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Scanner console;


    public Client(String serverIp, int serverPort) {
        try {
            s = new Socket(serverIp, serverPort);
            System.out.printf("Successfully connected to the server: %s\n", s);

            console = new Scanner(System.in);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

        } catch (IOException e) {
            //handle exceptions after failing to connect to the server socket.
            e.printStackTrace();
            close();
            return;
        }

        //start communication with the server
        try {
            communicateWithServer();
        } catch (IOException e) {
            close();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            s.close();
            dis.close();
            dos.close();
            console.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void communicateWithServer() throws IOException, ClassNotFoundException {

        while (true) {
            System.out.println(dis.readUTF());

            String userInput;

            userInput = console.nextLine();
            dos.writeUTF(userInput);

            if (userInput.equals("0")) {
                return;
            }

            int size = dis.readInt();
            System.out.println(size);

            for (int i = 0; i < size; i++) {
                System.out.println(dis.readUTF());
            }
        }
    }
}
