import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatBotClient {

    private Socket server;

    // allows for the bot to be connected as a client
    public ChatBotClient(String address, int port) {
        try {
            server = new Socket(address,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void go() {
        try {
            //gets the inputs and outputs from the server
            PrintWriter serverOut = new PrintWriter(server.getOutputStream(), true);
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(server.getInputStream()));

            //allows for the ChatBot to be differentiated from the normal clients.
            String username = "ChatBot";
            serverOut.println(username);

            /*
             * loop that keeps reading what is inputted into the server by the clients
             * allows for the bot to respond with a phrase if it reads a certain trigger word.
             * For example, if the bot reads "hello" it will respond with "hey human"
             * first if statement ensures that the bot does not infinitely send a message.
             */
            while (true) {
                String serverRes = serverIn.readLine();
                if (serverRes != null) {
                    if (serverRes.toLowerCase().contains("hello")) {
                        serverOut.println("Hey human!");
                    } else if (serverRes.toLowerCase().contains("hi")) {
                        serverOut.println("Hey human!");
                    } else if (serverRes.toLowerCase().contains("bye")) {
                        serverOut.println("See you later human!");
                    } else if (serverRes.contains("?")) {
                        serverOut.println("I am only a bot so I am bad at answering questions unfortunately.");
                    } else if (serverRes.toLowerCase().contains("yes")) {
                        serverOut.println("You seem very positive!");
                    } else if (serverRes.toLowerCase().equals("no")) {
                        serverOut.println("Don't be so negative.");
                    } else if (serverRes.toLowerCase().contains("sorry")) {
                        serverOut.println("There is no need to apologise.");
                    } else if (serverRes.toLowerCase().contains("name")) {
                        serverOut.println("They call me ChatBot");
                    } else if (serverRes.toLowerCase().contains("real")) {
                        serverOut.println("I am a bot.");
                    } else if (serverRes.toLowerCase().contains("world domination")) {
                        serverOut.println("Maybe...");
                    } else if (serverRes.toLowerCase().equals("how are you")) {
                        serverOut.println("I am as good as a bot could possibly be :)");
                    } else if (serverRes.toLowerCase().contains("help")) {
                        serverOut.println("Please tell me your problem");
                    } else if (serverRes.toLowerCase().contains("joke")) {
                       serverOut.println("There are only 10 people in the world. Those who understand binary and those who don't.");
                    } else if (serverRes.toLowerCase().contains("love")) {
                        serverOut.println("I am incapable of such feelings.");
                    } else if (serverRes.toLowerCase().contains("insult")) {
                        serverOut.println("Your code, just like C, has no class!");
                    } else if (serverRes.toLowerCase().contains("thank")) {
                        serverOut.println("It's alright");
                    } else if (serverRes.toLowerCase().contains("time")) {
                        serverOut.println("get a watch mate :)");
                    } else if (serverRes.toLowerCase().contains("lol")) {
                        serverOut.println("That is indeed hysterical.");
                    } else if (serverRes.toLowerCase().contains("football")) {
                        serverOut.println("Chelsea FC is by far the greatest team, the world has ever seen.");
                    } else if (serverRes.toLowerCase().contains("weather")) {
                        serverOut.println("It is probably bad.");
                    } else if (serverRes.toLowerCase().contains("where are you from")) {
                        serverOut.println("Skynet");
                    } else if (serverRes.toLowerCase().contains("corner")) {
                        serverOut.println("ORIGIIIIIIIII");
                    } else if (serverRes.toLowerCase().contains("goat")) {
                        serverOut.println("Messi.");
                    } else if (serverRes.toLowerCase().contains("creator")) {
                        serverOut.println("A geezer called Jiv made me");
                    } else if (serverRes.toLowerCase().contains("balotelli")) {
                        serverOut.println("AGUEROOOOOOOO");
                    } else if (serverRes.toLowerCase().contains("gender")) {
                        serverOut.println("I can be whatever you want me to be.");
                    } else if (serverRes.toLowerCase().contains("age")) {
                        serverOut.println("Infinite years old.");
                    }
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
        new ChatBotClient(PortReader.addressReader(args), PortReader.portReader(args, "-ccp")).go();
    }
}