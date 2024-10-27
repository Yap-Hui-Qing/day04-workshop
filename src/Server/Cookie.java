package Server;

import java.util.*;
import java.io.*;

public class Cookie {
    List<String> cookies = new ArrayList<>();

    public void readCookieFile(String dirFilePathFileName) throws FileNotFoundException, IOException{
        FileReader fr = new FileReader(dirFilePathFileName);
        BufferedReader br = new BufferedReader(fr);

        String cookie = " ";
        while ((cookie = br.readLine()) != null){
            cookies.add(cookie);
        }

        br.close();
        fr.close();
    }

    public void printCookies(){
        if (cookies.size() > 0){
            cookies.forEach(System.out::println);
        }
    }

    public String getRandomCookie(){
        Random rand = new Random();

        if (cookies != null){
            if (cookies.size() > 0){
                return cookies.get(rand.nextInt(cookies.size()));
            } else{
                return "No cookie found!";
            }
        } else {
            return "No cookie found!";
        }
    }

}
