package parser.parse_classes;

import java.util.ArrayList;
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
    for (int i = 1; i < decls.size(); i++) {
      CodeItem nextItem = decls.get(i).genLLCode();
      currItem.setNextItem(nextItem);
      currItem = nextItem;
    }
    return head;
  }
}