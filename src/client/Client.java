package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public Client(String serverIp, int serverPort) {

        try (Socket s = new Socket(serverIp, serverPort)) {

            System.out.printf("Successfully connected to the server: %s\n", s);

            try (DataInputStream dis = new DataInputStream(s.getInputStream());
                 DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                 Scanner console = new Scanner(System.in)) {

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

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
