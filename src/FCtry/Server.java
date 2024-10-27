package FCtry;

import java.net.*;
import java.util.*;
import java.io.*;

public class Server{

    private static String fileName;
    
    public static void main(String[] args) throws IOException{
        // 12345 cookie_file.txt

        boolean close = false;

        if (args.length < 2){
            System.out.println("Usage: <port> <file>");
            System.exit(-1);
        }

        // set the port
        int port = Integer.parseInt(args[0]);

        // set the filename
        if (args[1] == null){
            System.out.println("Please enter a filename.");
        } else{
            fileName = args[1];
        }

        // create a TCP server port
        ServerSocket server = new ServerSocket(port);
        
        while (!close){
            // waiting for an incoming connection
            System.out.printf("Waiting for connection on port %d\n", port);
            Socket sock = server.accept();

            System.out.println("Got a new connection");

            // get the input stream
            InputStream is = sock.getInputStream();
            Reader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);

            // get the output Stream
            OutputStream os = sock.getOutputStream();
            Writer writer = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(writer);

            // read the command from client
            String fromClient= br.readLine();

            System.out.println(fromClient);

            switch (fromClient.toLowerCase()){
                case "get-cookie":
                Cookie cookie = new Cookie();
                String outputCookie = cookie.returnCookie(fileName);

                String fromServer = "cookie-text " + outputCookie;

                // write result back to client
                bw.write(fromServer);
                bw.newLine();
                bw.flush();
                os.flush();
                break;

                case "close":
                os.close();
                is.close();
                server.close();
                close = true;
                break;

                default:
                System.out.println("Invalid command, please enter again");
            }
        }

    }
        
}
