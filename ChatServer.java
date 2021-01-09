import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    private ServerSocket in;
    private PrintWriter out;

    ArrayList<Socket> arrayListOfSockets = new ArrayList<>();

    public ChatServer(int port) {
        try {
            in = new ServerSocket(port);
            // creates a new thread
            new Thread(() -> {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                while(true) {
                    try {
                        // if the user enters "EXIT" then the server shuts down cleanly.
                        if(reader.readLine().equals("EXIT")) {
                            System.out.println("Server has closed.");
                            System.exit(0);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void go() {
        try {
            System.out.println("Server listening");
            while (true) {
                // when a new client connects, the socket is added to an array list.
                Socket s = in.accept();
                arrayListOfSockets.add(s);
                new Thread(new Runnable() {
                    @Override
                    public synchronized void run() {
                        try {
                            // gets inputs from
                            InputStreamReader r = new InputStreamReader(s.getInputStream());
                            BufferedReader clientIn = new BufferedReader(r);
                            String username = clientIn.readLine();
                            while (true) {
                                /*
                                 * In order to broadcast a message from one client to all the clients connected,
                                 * For loop cycles through the array list of sockets and outputs the message to all the connected clients
                                 */
                                String userInput = clientIn.readLine();
                                for (Socket socket : arrayListOfSockets) {
                                    try {
                                        // If the string is not empty then don't broadcast null messages from client that has left
                                        if (!(userInput.isEmpty())) {
                                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                                            out.println("[" + username + "] " + userInput);
                                        }

                                    }
                                    // If the input stream of a client that has left is being accessed, remove the socket of client and escape the for loop
                                    catch (NullPointerException e) {
                                        arrayListOfSockets.remove(s);
                                        System.out.println("The user " + username + " has disconnected.");
                                        return;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                System.out.println("Server accepted connection on " + in.getLocalPort() + " ; " + s.getPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ChatServer(PortReader.portReader(args, "-csp")).go();
    }
}