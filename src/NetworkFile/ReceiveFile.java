package NetworkFile;

import java.net.*;
import java.io.*;

public class ReceiveFile {
    
    private final Socket sock;

    public ReceiveFile(Socket sock){
        this.sock = sock;
    }

    public void receive() throws IOException{
        // open input stream to receive file
        InputStream is = sock.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        DataInputStream dis = new DataInputStream(bis);
        
        // read the metadata
        String filename = dis.readUTF();
        Long filesize = dis.readLong();

        // create a file to write image data
        FileOutputStream fos = new FileOutputStream("received_" + filename);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        // for large amounts of binary data
        int bytesRead = 0;
        int recvBytes = 0;
        byte[] buff = new byte[4 * 1024];

        // read in the file
        while (recvBytes < filesize){
            // read(byte[] b) 
            // read some number of bytes and stores them into buffer array b
            bytesRead = dis.read(buff);
            // write(byte[] b, int off, int len)
            // writes 'len' bytes from the specified byte array starting at offset 'off' in byte array
            bos.write(buff, 0, bytesRead);
            recvBytes += bytesRead;
            System.out.printf("Receive %d of %d\n", recvBytes, filesize);
        }

        // close the new file
        bos.flush();
        fos.close();

        // close the socket
        dis.close();
        bis.close();
        is.close();
        sock.close();
    }

}
