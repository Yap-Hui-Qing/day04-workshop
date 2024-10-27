package NetworkFile;

import java.io.*;

public class FileTransfer {
    
    public static void main(String[] args) throws IOException{
        
        int port = 3000;

        if (args.length <= 0){
            System.err.println("Missing file name");
            System.exit(-1);
        }

        // create a file object with the input argument
        File file = new File(args[0]);

        // file name
        String filename = file.getName();
        // size
        Long filesize = file.length();

        System.out.printf("Transferring file %s\n", filename);

        SendFile sendFile = new SendFile("localhost", port, file);
        sendFile.send();

    }
}
