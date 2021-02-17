package scanner;

import java.io.*;

public class ScanDaemon {
    public static void main(String[] args) {
        FileReader in = null;

        try {
            in = new FileReader("page27.c");
        }
        catch(FileNotFoundException e) {
            System.out.println("Big problemo, boss! Couldn't find file!");
            return;
        }

        BufferedReader inFile = new BufferedReader(in);
        CMinusScanner scanner = new CMinusScanner(inFile);

        while (scanner.hasNextToken()) {
            Token currToken = scanner.getNextToken();
            System.out.println(currToken.getType() + " " + currToken.getData());
        }
    }
}
