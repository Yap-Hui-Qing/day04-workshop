package Server;

import java.io.*;
import java.net.*;

public class App {

    // -> args[0] = port number
    // -> args[1] = directory path
    public static void main(String[] args) throws NumberFormatException, IOException{

        String portNumber = " ";
        String dirPath = " ";
        String fileName = " ";

        if (args.length > 0){
            // first argument is the port number
            portNumber = args[0];
            dirPath = args[1];
            fileName = args[2];
        } else {
            System.err.println("Invalid number of arguments expected");
            System.exit(0);
        }

        File newDirectory = new File(dirPath);
        if (!newDirectory.exists()){
            newDirectory.mkdir();
        }

        // read and print cookies
        Cookie c = new Cookie();
        c.readCookieFile(dirPath + File.separator + fileName);
        // c.printCookies();
        
        // day04 - slide 8
        ServerSocket socket = new ServerSocket(Integer.parseInt(portNumber));
        Socket sock = socket.accept();
        System.out.printf("\r\nWebsocket server started on port... %s\r\n", portNumber);

        // day04 slide 9, 10
        try{
            // read from client 
            InputStream is = sock.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            String messageReceived = " ";

            // write to client
            OutputStream os = sock.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            // day04 - slide 9
            while (!(messageReceived.toLowerCase().equals("close"))){
                System.out.println("Waiting for client's input...");

                messageReceived = dis.readUTF();
                if (messageReceived.toLowerCase().equals("close")){
                    break;
                }
                // pick a random cookie
                String retrievedCookie = c.getRandomCookie();

                // put it to the DataOutputStream to send back to client
                dos.writeUTF(retrievedCookie);
                dos.flush();
            }

            // close in reverse order
            dos.close();
            bos.close();
            os.close();

            dis.close();
            bis.close();
            is.close();
            socket.close();
        } catch (EOFException ex){
            System.err.println(ex.toString());
        } finally {

        }
    }
}
