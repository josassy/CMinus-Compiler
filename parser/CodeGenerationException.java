package parser;

/**
 * File: CodeGenerationException.java
 * 
 * Provide an interface for low-level code generation exceptions thrown from the
 * parse classes.
 */

public class CodeGenerationException extends Exception {
    public CodeGenerationException(String message) {
        super(message);
    }
}
