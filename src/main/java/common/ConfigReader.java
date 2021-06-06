package main.java.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class is responsible for reading the server and client connection information from local test files
 * in resources.
 */
public class ConfigReader {

    public ConfigReader() {

    }

    /**
     * This method reads a configuration file to obtain the setting needed to connect the server.
     *
     * @return set of strings containing the client connection information.
     * @throws IOException throws if the file path is incorrect.
     */
    public Set<String> readClientFile() throws IOException {

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

    /**
     * This method reads a configuration file to obtain the setting needed to connect the server.
     *
     * @return set of strings containing the server connection information.
     * @throws IOException throws if the file path is incorrect.
     */
    public Set<String> readServerFile() throws IOException {

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


