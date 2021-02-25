/**
 * File: Main.java
 * Main class to test the CMinusScanner. Reads in from test files and writes to
 * output in the format "<TOKEN_TYPE> <TOKEN_DATA>"
 */

package scanner;

import java.io.*;

public class Main {
  public static void main(String[] args) {
    FileReader in = null;
    FileWriter out = null;

    String fileStub = "quicksort";

    try {
      File outFile = new File(fileStub + ".out");
      outFile.createNewFile();
      out = new FileWriter(outFile);
    } catch (IOException e) {
      System.out.println("uwu, my computer has no concept of files");
    }

    try {
      in = new FileReader(fileStub + ".c");
    } catch (FileNotFoundException e) {
      System.out.println("Big problemo, boss! Couldn't find file!");
      return;
    }

    System.out.println(in);

    try {
      BufferedReader inFile = new BufferedReader(in);
      // CMinusScanner scanner = new CMinusScanner(inFile);
      CMinusJFlexScanner scanner = new CMinusJFlexScanner(inFile);
      Token currToken = scanner.getNextToken();
      while (currToken.getType() != Token.TokenType.EOF) {
        currToken = scanner.getNextToken();
        System.out.println(currToken.getType() + " " + currToken.getData());
        out.write(currToken.getType() + " " + currToken.getData() + "\n");
      }
    } catch (IOException e) {
      System.out.println("what is file");
    }

    try {
      in.close();
      out.close();
    } catch (IOException e) {
      System.out.println("Bad news houston! I couldn't close the file!");
    }
  }
}
