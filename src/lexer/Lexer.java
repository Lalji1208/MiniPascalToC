package lexer;

import exceptions.SyntaxException;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private final String source;
    private int position = 0;
    private int line = 1;

    public Lexer(String source) {
        this.source = source;
    }

    public List<Token> tokenize() {

        List<Token> tokens = new ArrayList<>();

        while (position < source.length()) {

            char current = source.charAt(position);

            if (Character.isWhitespace(current)) {

                if (current == '\n') {
                    line++;
                }

                position++;
                continue;
            }

            if (Character.isLetter(current)) {
                tokens.add(readIdentifier());
                continue;
            }

            if (Character.isDigit(current)) {
                tokens.add(readNumber());
                continue;
            }

            switch (current) {

                case '+':
                    tokens.add(new Token(TokenType.PLUS, "+", line));
                    position++;
                    break;

                case '-':
                    tokens.add(new Token(TokenType.MINUS, "-", line));
                    position++;
                    break;

                case '*':
                    tokens.add(new Token(TokenType.MULTIPLY, "*", line));
                    position++;
                    break;

                case '/':
                    tokens.add(new Token(TokenType.DIVIDE, "/", line));
                    position++;
                    break;

                case ';':
                    tokens.add(new Token(TokenType.SEMICOLON, ";", line));
                    position++;
                    break;

                case '(':
                    tokens.add(new Token(TokenType.LPAREN, "(", line));
                    position++;
                    break;

                case ')':
                    tokens.add(new Token(TokenType.RPAREN, ")", line));
                    position++;
                    break;

                case '>':
                    tokens.add(new Token(TokenType.GREATER, ">", line));
                    position++;
                    break;

                case '<':
                    tokens.add(new Token(TokenType.LESS, "<", line));
                    position++;
                    break;

                case '=':
                    tokens.add(new Token(TokenType.EQUAL, "=", line));
                    position++;
                    break;

                case ':':

                    if (peek() == '=') {

                        tokens.add(
                                new Token(
                                        TokenType.ASSIGN,
                                        ":=",
                                        line
                                )
                        );

                        position += 2;
                    }
                    else {
                        throw new SyntaxException(
                                "Unexpected ':' at line " + line
                        );
                    }

                    break;

                default:

                    throw new SyntaxException(
                            "Unexpected character '" +
                                    current +
                                    "' at line " +
                                    line
                    );
            }
        }

        tokens.add(
                new Token(
                        TokenType.EOF,
                        "",
                        line
                )
        );

        return tokens;
    }

    private char peek() {

        if (position + 1 >= source.length()) {
            return '\0';
        }

        return source.charAt(position + 1);
    }

    private Token readIdentifier() {

        StringBuilder builder = new StringBuilder();

        while (
                position < source.length()
                        &&
                        Character.isLetterOrDigit(
                                source.charAt(position)
                        )
        ) {

            builder.append(
                    source.charAt(position)
            );

            position++;
        }

        String word = builder.toString();

        switch (word.toLowerCase()) {

            case "var":
                return new Token(TokenType.VAR, word, line);

            case "begin":
                return new Token(TokenType.BEGIN, word, line);

            case "end":
                return new Token(TokenType.END, word, line);

            case "write":
                return new Token(TokenType.WRITE, word, line);

            case "read":
                return new Token(TokenType.READ, word, line);

            case "if":
                return new Token(TokenType.IF, word, line);

            case "then":
                return new Token(TokenType.THEN, word, line);

            case "while":
                return new Token(TokenType.WHILE, word, line);

            case "do":
                return new Token(TokenType.DO, word, line);

            default:
                return new Token(
                        TokenType.IDENTIFIER,
                        word,
                        line
                );
        }
    }

    private Token readNumber() {

        StringBuilder builder =
                new StringBuilder();

        while (
                position < source.length()
                        &&
                        Character.isDigit(
                                source.charAt(position)
                        )
        ) {

            builder.append(
                    source.charAt(position)
            );

            position++;
        }

        return new Token(
                TokenType.NUMBER,
                builder.toString(),
                line
        );
    }
}