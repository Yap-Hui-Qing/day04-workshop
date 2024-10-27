package NetworkFile;

import java.io.*;
import java.net.*;

public class SendFile {
    
    private String host;
    private int port;
    private File file;

    public SendFile(String host, int port, File file){
        this.host = host;
        this.port = port;
        this.file = file;
    }

    public void send() throws IOException{
        System.out.println("Connecting to the server");
        // perform the send
        Socket sock = new Socket(host, port);
        System.out.println("Connected!");

        // open a outputstream
        OutputStream os = sock.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bos);

        // open the file for reading
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);

        // get the file information
        String filename = file.getName();
        Long filesize = file.length();

        // write the metadata
        dos.writeUTF(filename);
        dos.writeLong(filesize);

        // write the file to server
        int readBytes = 0;
        int sendBytes = 0;
        byte[] buff = new byte[4 * 1024];

        while (sendBytes < filesize){
            readBytes = bis.read(buff);
            // write the amount that I have read
            dos.write(buff, 0, readBytes);
            sendBytes += readBytes;
            System.out.printf("Send %d of %d\n", sendBytes, filesize);
        }

        // close the file
        bis.close();
        fis.close();

        // flush connection
        dos.flush();
        bos.flush();
        os.flush();;

        // close connection
        dos.close();
        bos.close();
        os.close();
        sock.close();

    }
}
