package NetworkFile;

import java.net.*;
import java.io.*;

public class ClientFileHandler implements Runnable {
    
    private final Socket sock;

    public ClientFileHandler(Socket s){
        sock = s;
    }

    @Override
    public void run(){

        try {
            ReceiveFile recvFile = new ReceiveFile(sock);
            recvFile.receive();
        } catch (IOException e){
            System.out.println("Error in receiving file: " + e.getMessage());
        }
    }
}
