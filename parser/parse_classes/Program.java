package parser.parse_classes;

import java.util.ArrayList;

/**
 * File: Program.java
 * 
 * Represent the high-level Program class in C-. Contains a List of Declarations
 * to form a complete program in C-.
 */
public class Program implements ParseClass {

  ArrayList<Declaration> decls;

  public Program(ArrayList<Declaration> decls) {
    this.decls = decls;
  }

  public void Print(int indent) {
    for (Declaration d : decls) {
      d.Print(indent);
    }
  }
}