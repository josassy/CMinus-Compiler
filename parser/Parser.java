package parser;

import parser.parse_classes.Program;

/**
 * File: Parser.java
 * Public interface for CMinusParser to implement.
 */
public interface Parser {
    public Program parseProgram() throws ParseException;
}
