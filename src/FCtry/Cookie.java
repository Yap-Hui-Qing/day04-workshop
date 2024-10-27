package FCtry;

import java.io.*;
import java.util.*;

public class Cookie {
    
    List<String> cookiesList = new LinkedList<>();

    public String returnCookie(String fileName) throws IOException{
        String cookie;
        if (fileName == null){
            System.out.println("Please enter a filename.");
        } else{
            // open the file
            FileReader reader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(reader);
            String line = "x";
            
            while (line != null){
                line = br.readLine();
                cookiesList.add(line);
            }

            // close the cookie file
            reader.close();
        }

        if (cookiesList.size() > 0){
            // randomly return one cookie
            Random rand = new Random();
            int cookieIndex = rand.nextInt(cookiesList.size());
            String returnCookie = cookiesList.get(cookieIndex);
            return returnCookie;
        } else {
            return "No cookies";
        }
    }

    // public static void main (String[] args) throws IOException{
    //     Cookie cookie = new Cookie();
    //     System.out.println(cookie.returnCookie("cookie_file.txt"));
    // }
}