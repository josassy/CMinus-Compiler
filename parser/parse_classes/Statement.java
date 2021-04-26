package parser.parse_classes;
import lowlevel.*;

/**
 * File: Statement.java
 * 
 * Represent a generic Statement class in C-.
 */
public abstract class Statement implements ParseClass {
  public abstract void genLLCode(Function fun);
}
