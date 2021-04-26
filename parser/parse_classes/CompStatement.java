package parser.parse_classes;

import parser.ParseUtility;
import parser.CodeGenerationException;
import java.util.ArrayList;
import lowlevel.*;
import java.io.Writer;
import java.util.HashMap;

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

  public void genLLCode(Function fun) throws CodeGenerationException {
    
    //Parse decls into CodeItems
    HashMap st = fun.getTable();
    for (Declaration decl : decls) {
      st.put(decl.id, fun.getNewRegNum());
    }

    //Parse stmts into Operations
    for (Statement stmt : stmts) {
      stmt.genLLCode(fun);
    }
  }
}