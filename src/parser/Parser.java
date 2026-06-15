package parser;

import java.util.List;
import ast.ExpressionNode;
import ast.NumberNode;
import ast.VariableNode;
import ast.BinaryExpressionNode;
import ast.ReadNode;
import ast.AssignmentNode;
import ast.ASTNode;
import ast.ProgramNode;
import ast.VarDeclarationNode;
import ast.WriteNode;
import exceptions.SyntaxException;
import lexer.Token;
import lexer.TokenType;
import java.util.ArrayList;
import ast.WhileNode;

import ast.IfNode;
import ast.ConditionNode;

public class Parser {

    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public ProgramNode parse() {

        ProgramNode program =
                new ProgramNode();

        while (!isAtEnd()) {

            ASTNode statement =
                    parseStatement();

            if (statement != null) {
                program.addStatement(statement);
            }
        }

        return program;
    }

    private ASTNode parseStatement() {

        Token token = peek();

        switch (token.getType()) {

            case VAR:
                return parseVarDeclaration();

            case IDENTIFIER:
                return parseAssignment();

            case WRITE:
                return parseWrite();
            case READ:
                return parseRead();
            case IF:
                return parseIf();
            case WHILE:
                return parseWhile();

            default:
                throw new SyntaxException(
                        "Unexpected token "
                        + token.getType()
                        + " at line "
                        + token.getLine()
                );
        }
    }
    private IfNode parseIf() {

        consume(TokenType.IF);

        ConditionNode condition =
                parseCondition();

        consume(TokenType.THEN);

        consume(TokenType.BEGIN);

        List<ASTNode> statements =
                new ArrayList<>();

        while (
                peek().getType()
                        != TokenType.END
        ) {

            statements.add(
                    parseStatement()
            );
        }

        consume(TokenType.END);

        return new IfNode(
                condition,
                statements
        );
    }
    private ConditionNode parseCondition() {

        ExpressionNode left =
                parseExpression();

        Token operator = peek();

        if (
                operator.getType()
                        != TokenType.GREATER
                &&
                operator.getType()
                        != TokenType.LESS
        ) {

            throw new SyntaxException(
                    "Expected comparison operator"
            );
        }

        current++;

        ExpressionNode right =
                parseExpression();

        return new ConditionNode(
                left,
                operator.getValue(),
                right
        );
    }
    private ReadNode parseRead() {

        consume(TokenType.READ);

        consume(TokenType.LPAREN);

        Token variable =
                consume(TokenType.IDENTIFIER);

        consume(TokenType.RPAREN);

        consume(TokenType.SEMICOLON);

        return new ReadNode(
                variable.getValue()
        );
    }

    private VarDeclarationNode parseVarDeclaration() {

        consume(TokenType.VAR);

        Token variable =
                consume(TokenType.IDENTIFIER);

        consume(TokenType.SEMICOLON);

        return new VarDeclarationNode(
                variable.getValue()
        );
    }

    private AssignmentNode parseAssignment() {

        Token variable =
                consume(TokenType.IDENTIFIER);

        consume(TokenType.ASSIGN);

        ExpressionNode expression =
                parseExpression();

        consume(TokenType.SEMICOLON);

        return new AssignmentNode(
                variable.getValue(),
                expression
        );
    }
    private WhileNode parseWhile() {

        consume(TokenType.WHILE);

        ConditionNode condition =
                parseCondition();

        consume(TokenType.DO);

        consume(TokenType.BEGIN);

        List<ASTNode> statements =
                new ArrayList<>();

        while (
                peek().getType()
                        != TokenType.END
        ) {

            statements.add(
                    parseStatement()
            );
        }

        consume(TokenType.END);

        return new WhileNode(
                condition,
                statements
        );
    }
    private ExpressionNode parseExpression() {

        ExpressionNode left =
                parsePrimary();

        while (
                peek().getType() ==
                        TokenType.PLUS
                ||
                peek().getType() ==
                        TokenType.MINUS
        ) {

            Token operator = peek();

            current++;

            ExpressionNode right =
                    parsePrimary();

            left =
                    new BinaryExpressionNode(
                            left,
                            operator.getValue(),
                            right
                    );
        }

        return left;
    }
    private ExpressionNode parsePrimary() {

        Token token = peek();

        if (token.getType()
                == TokenType.NUMBER) {

            current++;

            return new NumberNode(
                    token.getValue()
            );
        }

        if (token.getType()
                == TokenType.IDENTIFIER) {

            current++;

            return new VariableNode(
                    token.getValue()
            );
        }

        throw new SyntaxException(
                "Expected expression at line "
                        + token.getLine()
        );
    }
    private WriteNode parseWrite() {

        consume(TokenType.WRITE);

        consume(TokenType.LPAREN);

        Token variable =
                consume(TokenType.IDENTIFIER);

        consume(TokenType.RPAREN);

        consume(TokenType.SEMICOLON);

        return new WriteNode(
                variable.getValue()
        );
    }

    private Token consume(TokenType expected) {

        Token token = peek();

        if (token.getType() != expected) {

            throw new SyntaxException(
                    "Expected "
                    + expected
                    + " but found "
                    + token.getType()
                    + " at line "
                    + token.getLine()
            );
        }

        current++;

        return token;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }
}