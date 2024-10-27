package FCtry;

import java.net.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class ThreadedServer{

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

        ExecutorService thrPool = Executors.newFixedThreadPool(2);
        String threadName = Thread.currentThread().getName();
        // create a TCP server port
        ServerSocket server = new ServerSocket(port);
        
        while (!close){
            // waiting for an incoming connection
            System.out.printf("[%s] Waiting for connection on port %d\n", threadName, port);
            Socket sock = server.accept();

            System.out.println("Got a new connection");

            ClientHandler handler = new ClientHandler(sock, fileName);
            thrPool.submit(handler);
        }

    }
        
}
