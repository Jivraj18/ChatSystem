/*
 * Class that reads the port and/or IP Address if the user requests to bind the server or client to another port or IP Address.
 * If the user does not enter the phrase to change the port, then the server and client use the default port.
 */

public class PortReader {

    public static int portReader (String[] args, String param) {
        for(int i = 0; i < args.length; i++) {
            if (args[i].equals(param)) {
                if(i + 1 < args.length) {
                    return Integer.parseInt(args[i + 1]);
                }
            }
        }
        return 14001;
    }

    public static String addressReader (String[] args) {
        for(int i = 0; i < args.length; i++) {
            if (args[i].equals("-cca")) {
                if(i + 1 < args.length) {
                    return args[i + 1];
                }
            }
        }
        return "localhost";
    }
}
