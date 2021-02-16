// SEE PAGE 491 FOR INFORMATION ON C-

public class Token {
    public enum TokenType {
        ELSE_TOKEN, // else
        IF_TOKEN, // if
        INT_TOKEN, // int
        RETURN_TOKEN, // return
        VOID_TOKEN, // void
        WHILE_TOKEN, // while
        PLUS_TOKEN, // +
        MINUS_TOKEN, // -
        MULT_TOKEN, // *
        DIV_TOKEN, // /
        LESS_TOKEN, // <
        LEQ_TOKEN, // <=
        GREATER_TOKEN, // >
        GEQ_TOKEN, // >=
        EQL_TOKEN, // ==
        NEQ_TOKEN, // !=
        ASSIGN_TOKEN, // =
        SEMICOLON_TOKEN, // ;
        COMMA_TOKEN, // ,
        OPAREN_TOKEN, // (
        CPAREN_TOKEN, // )
        OBRACKET_TOKEN, // [
        CBRACKET_TOKEN, // ]
        OCURLY_TOKEN, // {
        CCURLY_TOKEN, // }
        OCOMMENT_TOKEN, // /*
        CCOMMENT_TOKEN, // */
        ID_TOKEN, // letter letter*
        NUM_TOKEN, // digit digit*
        EOF_TOKEN
    }

    private TokenType tokenType;
    private Object tokenData;

    public Token(TokenType type) {
        this(type, null);
    }

    public Token(TokenType type, Object data) {
        tokenType = type;
        tokenData = data;
    }

    public Token.TokenType getType() {
        return tokenType;
    }

    public Object getData() {
        return tokenData;
    }
}