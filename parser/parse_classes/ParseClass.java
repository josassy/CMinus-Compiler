package parser.parse_classes;

import java.io.Writer;
import lowlevel.*;
import parser.CodeGenerationException;

/**
 * File: ParseClass.java
 * 
 * Defines the interface that Parse Classes need to implement, primarily the
 * Print() function to print the AST.
 */
public interface ParseClass {
  public void Print(Writer out, int indent);
}