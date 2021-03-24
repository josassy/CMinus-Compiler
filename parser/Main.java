/**
 * File: Main.java
 * Main class to test the CMinusParser.
 */

package parser;

import java.io.*;

import scanner.CMinusScanner;

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

    BufferedReader inFile = new BufferedReader(in);
    try {
      CMinusScanner scanner = new CMinusScanner(inFile);
      CMinusParser parser = new CMinusParser(scanner);
      parser.parseProgram();

    } catch (ParseException e) {
      System.out.println("ParseException: " + e.toString());
    }

    try {
      in.close();
      out.close();
    } catch (IOException e) {
      System.out.println("Bad news houston! I couldn't close the file!");
    }
  }
}
