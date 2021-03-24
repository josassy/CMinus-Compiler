package parser.parse_classes;

import parser.ParseUtility;
import java.util.ArrayList;

/**
 * File: CompStatement.java
 * 
 * Represent a CompoundStatement in C- which inherits from Statement, including
 * lists of Declarations and Statements.
 */
public class CompStatement extends Statement {
  ArrayList<Declaration> decls;
  ArrayList<Statement> stmts;

  public CompStatement(ArrayList<Declaration> decls, ArrayList<Statement> stmts) {
    this.decls = decls;
    this.stmts = stmts;
  }

  public void Print(int indent) {
    ParseUtility.IndentedPrintln("{", indent);
    for (Declaration d : decls) {
      d.Print(indent + 1);
    }
    for (Statement s : stmts) {
      s.Print(indent + 1);
    }
    ParseUtility.IndentedPrintln("}", indent);
  }
}