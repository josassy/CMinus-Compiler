/**
 * File: Main.java
 * Main class to test the CMinusParser.
 */

package parser;

import java.io.*;

import scanner.CMinusScanner;
import parser.parse_classes.Program;

public class Main {
  public static void main(String[] args) {
    FileReader in = null;
    FileWriter out = null;

    String fileStub = "27";

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
      Program p = parser.parseProgram();
      p.Print(0);

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
