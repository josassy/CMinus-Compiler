import java.io.*;
import java.util.Scanner;

class CMinusScanner {
  public static void main(String[] args) {
    System.out.println("Hi mom!");
    FileInputStream inFile = null;
    FileOutputStream outFile = null;
    
    try {
      inFile = new FileInputStream("input.cminus");
      //outFile = new FileOutputStream("output.txt");
    }
    catch (FileNotFoundException e) {
      System.out.println("Seems like your files are not in order, bro!");
      return;
    }
    Scanner inS = new Scanner(inFile);

    while (inS.hasNext()) {
      System.out.println(inS.next());
    }
  }
}