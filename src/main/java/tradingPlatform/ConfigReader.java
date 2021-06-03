package main.java.tradingPlatform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ConfigReader {

    private static String hostname;
    private static int port;
    private static int socket_accept_timeout;
    private static int socket_read_timeout;
    private static String propsfile;


    public ConfigReader() {

    }

    public Set<String> readClientFile() throws IOException {
        //TODO MAKE EXCEPTIONS INCASE SOMEONE RUINS CONFIG FILE
        File fileName = new File("src/main/resources/clientconfig.txt");
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Scanner scanner = new Scanner(bufferedReader);
        Set<String> configFile = new HashSet<String>();
        configFile.add("Test");
        System.out.println(configFile);

        for (; ; ) {
            if (!scanner.hasNext()) break;
            String token = scanner.next("[a-z]*");
            if (token.equals("Client")) {
                String hostname = scanner.next();

                configFile.add(scanner.next());

                //configFile.add(hostnameIP);

                String port = scanner.next();
                String portID = scanner.next();
                configFile.add(portID);
            }
        }
        scanner.close();
        return configFile;
    }

    public Set<String> readServerFile() throws IOException {

        File fileName = new File("src/main/resources/thisfile.txt");
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Scanner scanner = new Scanner(bufferedReader);
        Set<String> configFile = null;

        for (; ; ) {
            if (!scanner.hasNext()) break;
            String token = scanner.next("[a-z]*");
            if (token.equals("Client")) {
                String hostname = scanner.next();
                String hostnameIP = scanner.next();
                configFile.add(hostnameIP);
                //System.out.println(portvalue);

            }
            //String token = scanner.next("[a-z]*");

            //System.out.println(token);
        }

        scanner.close();
        return configFile;
    }
}


