package scanner;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        FileReader in = null;
        FileWriter out = null;

        try {
            File outFile = new File("j.out");
            outFile.createNewFile();
            out = new FileWriter(outFile);
        }
        catch (IOException e) {
            System.out.println("uwu, my computer has no concept of files");
        }

        try {
            in = new FileReader("bad.c");
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
            try {
                out.write(currToken.getType() + " " + currToken.getData() + "\n");
            }
            catch(IOException e) {
                System.out.println("what is file");
            }
        }

        try {
            in.close();
            out.close();
        }
        catch (IOException e) {
            System.out.println("Bad news houston! I couldn't close the file!");
        }
    }
}
