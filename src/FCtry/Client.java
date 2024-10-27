package FCtry;

import java.io.*;
import java.net.*;

public class Client{

    public static void menu() {
        System.out.println("Request the server to send a cookie: type 'get-cookie'");
        System.out.println("Request the server to close the connection: type 'close'");
    }

    public static void main(String[] args) throws IOException{

        // localhost:12345
        if (args.length <= 0){
            System.out.println("Usage: <host>:<port>");
            System.exit(-1);
        }

        String input = args[0].replace(":", " ");
        String[] connection = input.split(" ");

        // set the host and port
        String host = connection[0];
        int port = Integer.parseInt(connection[1]);

        // create a connection to the server
        // connect to the port on the server
        System.out.println("Connecting to the server");
        Socket sock = new Socket(host, port);

        System.out.println("Connected!");

        
        Console cons = System.console();

        menu();
        // write a command to the server
        String command = cons.readLine("command: ");

        // get the output stream
        OutputStream os = sock.getOutputStream();
        Writer writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);

        // write the command to server
        bw.write(command);
        bw.newLine();
        bw.flush();
        os.flush();
        
        // get the input stream
        InputStream is = sock.getInputStream();
        Reader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);

        if (command.toLowerCase().equals("get-cookie")){
            // read the result from the server
            String cookieFromServer = br.readLine().replace("cookie-text", "");

            System.out.printf(">>> SERVER: %s\n", cookieFromServer);
        } else if (command.toLowerCase().equals("close")) {
            System.out.println("Connection is closed\n");
        } else {
            System.out.println("Invalid command, please enter again");
        }
    
        br.close();
        os.close();
        is.close();
        sock.close();
    }
}
