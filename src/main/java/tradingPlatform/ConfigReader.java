package main.java.tradingPlatform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ConfigReader {

    private static String hostname;
    private static int port;
    private static int socket_accept_timeout;
    private static int socket_read_timeout;
    private static String propsfile;


    public ConfigReader() {
        propsfile = "this file";
    }

    public Set<String> readClientFile() throws IOException {
        //TODO MAKE EXCEPTIONS INCASE SOMEONE RUINS CONFIG FILE
        Set<String> mySet = new HashSet<String>();
        File fileName = new File("src/main/resources/clientconfig.txt");

        FileReader fileReader = new FileReader(fileName);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        Scanner scanner = new Scanner(bufferedReader);


        for (; ; ) {
            if (!scanner.hasNext()) break;
            String token = scanner.next();
            String port1 = scanner.next();
            mySet.add(port1);
            String token2 = scanner.next();
            String port2 = scanner.next();
            mySet.add(port2);
        }
        bufferedReader.close();
        scanner.close();
        return mySet;
    }


    public Set<String> readServerFile() throws IOException {
//TODO MAKE EXCEPTIONS INCASE SOMEONE RUINS CONFIG FILE
        Set<String> mySet = new HashSet<String>();
        File fileName = new File("src/main/resources/serverconfig.txt");

        FileReader fileReader = new FileReader(fileName);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        Scanner scanner = new Scanner(bufferedReader);

        for (; ; ) {
            if (!scanner.hasNext()) break;
            String token = scanner.next();
            String port1 = scanner.next();
            mySet.add(port1);
            String token2 = scanner.next();
            String port2 = scanner.next();
            mySet.add(port2);
            String token3 = scanner.next();
            String port3 = scanner.next();
            mySet.add(port3);
            String token4 = scanner.next();
            String port4 = scanner.next();
            mySet.add(port4);

        }
        bufferedReader.close();
        scanner.close();
        return mySet;
    }
}


