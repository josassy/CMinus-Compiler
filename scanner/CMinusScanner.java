/**
 * Jeb Bishop, Alex Kretshmer, Josiah Lansford
 * Dr. Gallagher
 * CS3510 Compiler
 * 17 February 2021
 * Project 1: C- Scanner
 * 
 * Algorithm overview: 
 * 
 * This class follows the structure specified in Louden Appendix B to implement 
 * a token scanner.
 * 
 * To implement scanToken(), we built a DFA that uses nested switch statements 
 * to control execution flow according to the DFA specified for this assignment,
 * which can handle the token types in C- as specified in Louden p.491.
 * 
 * Since Java does not handle EOF in the same way as C, we added an EOF flag to
 * the class to indicate when the EOF has been reached.
 */

package scanner;

import java.io.IOException;
import java.io.BufferedReader;
import scanner.Token.TokenType;
import java.util.HashMap;

public class CMinusScanner implements Scanner {
  private Token nextToken;
  private BufferedReader inFile;
  private boolean EOF = false;

  private HashMap<String, TokenType> reservedWords;

  public enum StateType {
    START, INNUM, INID, INLESS, INGREATER, INNOTEQUAL, INASSIGN, INDIV, INCOMMENT, LEAVECOMMENT, DONE
  }

  public CMinusScanner(BufferedReader file) {
    reservedWords = new HashMap<String, TokenType>();

    // populate reserved tokens map
    reservedWords.put("else", TokenType.ELSE_TOKEN);
    reservedWords.put("if", TokenType.IF_TOKEN);
    reservedWords.put("int", TokenType.INT_TOKEN);
    reservedWords.put("return", TokenType.RETURN_TOKEN);
    reservedWords.put("void", TokenType.VOID_TOKEN);
    reservedWords.put("while", TokenType.WHILE_TOKEN);

    inFile = file;
    nextToken = scanToken();
  }

  /**
   * Method to indicate whether another token is available
   */
  public boolean hasNextToken() {
    return nextToken.getType() != Token.TokenType.EOF;
  }

  public Token getNextToken() {
    Token returnToken = nextToken;
    if (nextToken.getType() != Token.TokenType.EOF)
      nextToken = scanToken();
    return returnToken;
  }

  public Token viewNextToken() {
    return nextToken;
  }

  /**
   * Check if string matches a reserved token
   * 
   * @return the applicable token type
   */
  private TokenType reservedWordLookup(String toCheck) {
    // if reserved word, look up that token type
    if (reservedWords.containsKey(toCheck)) {
      return reservedWords.get(toCheck);
    }
    // if not reserved word, it is regular ID token
    else {
      return TokenType.ID_TOKEN;
    }
  }

  /**
   * Attempt to get next char from the file. If reaches EOF or hits exception,
   * will return '\0' character and set EOF flag.
   * 
   * @return the character returned
   */
  private char getChar() {
    try {
      inFile.mark(1);
      int val = inFile.read();
      if (val > 0) {
        return (char) val;
      } else {
        // Since C handles EOF differently than Java, need to pass EOF via flag
        EOF = true;
        return '\0';
      }
    }

    catch (IOException e) {
      System.out.println(e);
      EOF = true;
      return '\0';
    }
  }

  /**
   * Move the pointer in the input buffer back one character so that it can be
   * processed again This usually happens when the state needs to be reset before
   * processing the token
   */
  private void unGetChar() {
    try {
      inFile.reset();
    }

    catch (IOException e) {
      System.out.println(e);
      return;
    }
  }

  /**
   * Private method to read in the next character and identify as token. Called
   * from getNextToken to set nextToken, which is viewable on a subsequent call to
   * getNextToken or viewNextToken.
   * 
   * Most of the scanner magic happens in this method.
   */
  private Token scanToken() {
    // Holds current token to be returned
    Token currentToken = null;

    String tokenString = "";

    // Start in Start State
    StateType state = StateType.START;

    // Flag to indicate save to tokenString
    boolean save = false;

    while (state != StateType.DONE) {

      // Get the next char
      char c = getChar();

      save = true;

      switch (state) {
        // If state of DFA is start, look at the character with fresh eyes
        case START:
          // clear token string from any previous values
          tokenString = "";
          
          // Handle all the characters that would move to a new state rather
          // than being done immediately
          if (Character.isDigit(c)) {
            state = StateType.INNUM;
          } else if (Character.isAlphabetic(c)) {
            state = StateType.INID;
          } else if (c == '<') {
            state = StateType.INLESS;
          } else if (c == '>') {
            state = StateType.INGREATER;
          } else if (c == '!') {
            state = StateType.INNOTEQUAL;
          } else if (c == '=') {
            state = StateType.INASSIGN;
          } else if (c == '/') {
            state = StateType.INDIV;
          } else if ((c == ' ') || (c == '\t') || (c == '\n') || (c == '\r')) {
            save = false;
          } else {
            // Handle all the characters that are processed immediately without
            // any intermediate states
            state = StateType.DONE;
  
            // If EOF flag is set, break out of loop.
            if (EOF) {
              save = false;
              currentToken = new Token(TokenType.EOF);
              break;
            }
            switch (c) {
              case '+':
                currentToken = new Token(TokenType.PLUS_TOKEN);
                break;
              case '-':
                currentToken = new Token(TokenType.MINUS_TOKEN);
                break;
              case '*':
                currentToken = new Token(TokenType.MULT_TOKEN);
                break;
              case ';':
                currentToken = new Token(TokenType.SEMICOLON_TOKEN);
                break;
              case ',':
                currentToken = new Token(TokenType.COMMA_TOKEN);
                break;
              case '(':
                currentToken = new Token(TokenType.OPAREN_TOKEN);
                break;
              case ')':
                currentToken = new Token(TokenType.CPAREN_TOKEN);
                break;
              case '[':
                currentToken = new Token(TokenType.OBRACKET_TOKEN);
                break;
              case ']':
                currentToken = new Token(TokenType.CBRACKET_TOKEN);
                break;
              case '{':
                currentToken = new Token(TokenType.OCURLY_TOKEN);
                break;
              case '}':
                currentToken = new Token(TokenType.CCURLY_TOKEN);
                break;
              default:
                currentToken = new Token(TokenType.ERROR);
            }
          }
          break;

        // In middle of number
        case INNUM:
          // if a character is seen directly after a number, error
          if (Character.isAlphabetic(c)) {
            unGetChar();
            save = false;
            state = StateType.DONE;
            currentToken = new Token(TokenType.ERROR);
          }
          else if (!Character.isDigit(c)) {
            unGetChar();
            save = false;
            state = StateType.DONE;
            currentToken = new Token(TokenType.NUM_TOKEN);
          }
          break;

        // in middle of identifier
        case INID:
          if (Character.isDigit(c)) {
            unGetChar();
            save = false;
            state = StateType.DONE;
            currentToken = new Token(TokenType.ERROR);
          }
          
          else if (!Character.isAlphabetic(c)) {
            unGetChar();
            save = false;
            state = StateType.DONE;
            currentToken = new Token(TokenType.ID_TOKEN);
          }
          break;

        // determine if <= or <
        case INLESS:
          state = StateType.DONE;
          save = false;
          if (c == '=') {
            currentToken = new Token(TokenType.LEQ_TOKEN);
          }
          // step backwards
          else {
            unGetChar();
            currentToken = new Token(TokenType.LESS_TOKEN);
          }
          break;

        // determine if >= or >
        case INGREATER:
          state = StateType.DONE;
          save = false;
          if (c == '=') {
            currentToken = new Token(TokenType.GEQ_TOKEN);
          }
          // step backwards
          else {
            unGetChar();
            currentToken = new Token(TokenType.GREATER_TOKEN);
          }
          break;

        // determine if != or ERROR
        case INNOTEQUAL:
          state = StateType.DONE;
          if (c == '=') {
            currentToken = new Token(TokenType.NEQ_TOKEN);
          }
          // step backwards
          else {
            unGetChar();
            save = false;
            currentToken = new Token(TokenType.ERROR);
          }
          break;

        // determine if == or =
        case INASSIGN:
          state = StateType.DONE;
          if (c == '=') {
            currentToken = new Token(TokenType.EQL_TOKEN);
          }
          // step backwards
          else {
            unGetChar();
            save = false;
            currentToken = new Token(TokenType.ASSIGN_TOKEN);
          }
          break;

        // determine if /* or /
        case INDIV:
          if (c == '*') {
            save = false;
            state = StateType.INCOMMENT;
          }
          // step backwards
          else {
            unGetChar();
            state = StateType.DONE;
            save = false;
            currentToken = new Token(TokenType.DIV_TOKEN);
          }
          break;

        // look for comment end pattern
        case INCOMMENT:
          save = false;
          if (c == '*') {
            state = StateType.LEAVECOMMENT;
          }
          break;

        // determine if */ or we're still in comment
        case LEAVECOMMENT:
          save = false;
          if (c == '/') {
            state = StateType.START;
          } else if (c == '*') {
            state = StateType.LEAVECOMMENT;
          } else {
            state = StateType.INCOMMENT;
          }
          break;

        // we should never get here, but need default case for safety
        case DONE:
        default: // Should never happen, indicates scanner bug!
          System.out.println("Scanner bug: " + state);
          state = StateType.DONE;
          currentToken = new Token(TokenType.ERROR);
          break;
      }
      if (save) {
        tokenString = tokenString + c;
      }
      if (state == StateType.DONE) {
        if (currentToken.getType() == TokenType.ID_TOKEN) {
          currentToken = new Token(reservedWordLookup(tokenString));
        }
      }
    }

    return new Token(currentToken.getType(), tokenString);
  }
}