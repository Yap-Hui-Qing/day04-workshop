package NetworkFile;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadedFileServer {
    
    public static void main(String[] args) throws IOException{
        int port = 3000;

        // create a thread pool with 2 threads
        ExecutorService thrPool = Executors.newFixedThreadPool(2);

        // create a server port, using TCP
        ServerSocket server = new ServerSocket(port);

        System.out.printf("File server started on port %d\n", port);

        while (true){
            Socket sock = server.accept();
            System.out.printf("New file upload\n");

            ClientFileHandler handler = new ClientFileHandler(sock);
            thrPool.submit(handler);
        }

    }
}
