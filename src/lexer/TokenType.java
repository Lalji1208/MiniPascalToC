package lexer;

public enum TokenType {

    // Keywords
    VAR,
    BEGIN,
    END,
    WRITE,
    READ,
    IF,
    THEN,
    WHILE,
    DO,

    // Identifiers & literals
    IDENTIFIER,
    NUMBER,

    // Operators
    ASSIGN,     // :=
    PLUS,       // +
    MINUS,      // -
    MULTIPLY,   // *
    DIVIDE,     // /

    GREATER,    // >
    LESS,       // <
    EQUAL,      // =

    // Symbols
    SEMICOLON,  // ;
    LPAREN,     // (
    RPAREN,
    // )

    EOF
}