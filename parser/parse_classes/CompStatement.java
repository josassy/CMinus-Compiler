package parser.parse_classes;

import parser.ParseUtility;
import java.util.ArrayList;
import java.io.Writer;

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

  public void Print(Writer out, int indent) {
    ParseUtility.IndentedPrintln("{", indent, out);
    for (Declaration d : decls) {
      d.Print(out, indent + 1);
    }
    for (Statement s : stmts) {
      s.Print(out, indent + 1);
    }
    ParseUtility.IndentedPrintln("}", indent, out);
  }
}