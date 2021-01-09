import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

    private Socket server;

    // allows the client to connect to the server with an IP address and port.
    public ChatClient(String address, int port) {
        try {
            server = new Socket(address,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void go() {
        try {
            // takes user input and gets the input/output from the server.
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter serverOut = new PrintWriter(server.getOutputStream(), true);
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(server.getInputStream()));

            // allows the user to input a username in order to differentiate between the different clients connected to the same server.
            String username;
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter a username.");
            username = input.readLine();
            serverOut.println(username);
            System.out.println("Hello " + username + " you may start chatting!");

            // creates a new thread.
            new Thread(() -> {
                while (true) {
                    String userInput = null;
                    try {
                        // gets user input
                        userInput = userIn.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // server outputs the message inputted by the user.
                    serverOut.println(userInput);
                }
            }).start();

            while(true) {
                try {
                    // prints out messages from other clients.
                    String serverRes = serverIn.readLine();
                    if (serverRes != null) {
                        System.out.println(serverRes);
                    }

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

    } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ChatClient(PortReader.addressReader(args), PortReader.portReader(args, "-ccp")).go();
    }
}