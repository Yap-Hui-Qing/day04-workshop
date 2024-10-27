package Client;

import java.net.*;
import java.io.*;

public class ClientApp {
    public static void main(String[] args) throws IOException{

        String portNumber = " ";

        if (args.length > 0) {
            portNumber = args[0];
        } else {
            System.err.println("Invalid arguments input");
        }

        // day04 - slide 8,9
        try{
            Socket socket = new Socket("localhost", Integer.parseInt(portNumber));

            InputStream is = socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            OutputStream os = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            Console console = System.console();
            String cookie = "";
            String keyboardInput = "";

            while (!(keyboardInput.toLowerCase().equals("close"))) {
                keyboardInput = console.readLine("Enter 'get-cookie' to request for a cookie ('close' to terminate): ");

                if (keyboardInput.toLowerCase().equals("close")){
                    break;
                }

                dos.writeUTF(keyboardInput);
                dos.flush();

                cookie = dis.readUTF();
                System.out.println(cookie);
            }

            dos.close();
            bos.close();
            os.close();

            dis.close();
            bis.close();
            is.close();
            socket.close();
        } catch (EOFException eofException){

        }
    }
}
