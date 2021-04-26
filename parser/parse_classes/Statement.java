package parser.parse_classes;
import parser.CodeGenerationException;
import lowlevel.*;

/**
 * File: Statement.java
 * 
 * Represent a generic Statement class in C-.
 */
public abstract class Statement implements ParseClass {
  public abstract void genLLCode(Function fun) throws CodeGenerationException;
}
