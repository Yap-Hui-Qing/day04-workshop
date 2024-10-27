package FCtry;

import java.net.*;
import java.io.*;

public class ClientHandler implements Runnable {
    
    private final Socket sock;
    private final String filename;

    public ClientHandler(Socket s, String filename){
        this.sock = s;
        this.filename = filename;
    }

    @Override
    public void run() {

        boolean close = false;

        String threadName = Thread.currentThread().getName();

        try{
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
                String outputCookie = cookie.returnCookie(filename);

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
                sock.close();
                close = true;
                break;

                default:
                System.out.println("Invalid command, please enter again");
            }
        } catch (IOException e){
            System.err.println();
        }

    }
}
