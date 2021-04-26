package parser.parse_classes;

import java.util.ArrayList;

import compiler.CMinusCompiler;

import java.io.Writer;
import parser.CodeGenerationException;
import lowlevel.*;

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

  public void Print(Writer out, int indent) {
    for (Declaration d : decls) {
      d.Print(out, indent);
    }
  }

  public CodeItem genLLCode() throws CodeGenerationException {
    CodeItem head = decls.get(0).genLLCode();
    CodeItem currItem = head;
    if (head instanceof Data) {
      Data dataThing = (Data)head;
      CMinusCompiler.globalHash.put(dataThing.getName(), 0);
    }
    for (int i = 1; i < decls.size(); i++) {
      CodeItem nextItem = decls.get(i).genLLCode();
      // if it was a vardecl, add it to the symbol table you fools!
      if (nextItem instanceof Data) {
        Data dataThing = (Data)nextItem;
        CMinusCompiler.globalHash.put(dataThing.getName(), 0);
      }
      currItem.setNextItem(nextItem);
      currItem = nextItem;
    }
    return head;
  }
}