public class Token {
    public enum TokenType {
        INT_TOKEN,
        DOUBLE_TOKEN,
        IF_TOKEN,
        EOF_TOKEN,
        //More
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