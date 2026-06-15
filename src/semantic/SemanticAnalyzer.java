package semantic;

import java.util.HashSet;
import java.util.Set;
import ast.ExpressionNode;
import ast.NumberNode;
import ast.VariableNode;
import ast.BinaryExpressionNode;
import ast.ReadNode;

import ast.ASTNode;
import ast.AssignmentNode;
import ast.ProgramNode;
import ast.VarDeclarationNode;
import ast.WriteNode;
import exceptions.SemanticException;
import ast.IfNode;
import ast.ConditionNode; 
import ast.WhileNode;
public class SemanticAnalyzer {

    private Set<String> declaredVariables =
            new HashSet<>();

    public void analyze(
            ProgramNode program) {

        for (ASTNode node :
                program.getStatements()) {

            if (node instanceof VarDeclarationNode) {

                analyzeVarDeclaration(
                        (VarDeclarationNode) node
                );
            }

            else if (node instanceof AssignmentNode) {

                analyzeAssignment(
                        (AssignmentNode) node
                );
            }

            else if (node instanceof WriteNode) {

                analyzeWrite(
                        (WriteNode) node
                );
            }
            else if (node instanceof ReadNode) {

                analyzeRead(
                        (ReadNode) node
                );
            }
            else if (node instanceof IfNode) {

                analyzeIf(
                        (IfNode) node
                );
            }
            else if (node instanceof WhileNode) {

                analyzeWhile(
                        (WhileNode) node
                );
            }
        }
    }
    private void analyzeWhile(
            WhileNode node) {

        analyzeCondition(
                node.getCondition()
        );

        for (ASTNode statement :
                node.getStatements()) {

            if (statement instanceof WriteNode) {

                analyzeWrite(
                        (WriteNode) statement
                );
            }

            else if (
                    statement instanceof AssignmentNode
            ) {

                analyzeAssignment(
                        (AssignmentNode) statement
                );
            }

            else if (
                    statement instanceof ReadNode
            ) {

                analyzeRead(
                        (ReadNode) statement
                );
            }
        }
    }
    private void analyzeIf(
            IfNode node) {

        analyzeCondition(
                node.getCondition()
        );

        for (ASTNode statement :
                node.getStatements()) {

            if (statement instanceof WriteNode) {

                analyzeWrite(
                        (WriteNode) statement
                );
            }

            else if (
                    statement instanceof AssignmentNode
            ) {

                analyzeAssignment(
                        (AssignmentNode) statement
                );
            }
        }
    }
    private void analyzeCondition(
            ConditionNode condition) {

        analyzeExpression(
                condition.getLeft()
        );

        analyzeExpression(
                condition.getRight()
        );
    }
    private void analyzeRead(
            ReadNode node) {

        String variable =
                node.getVariableName();

        if (!declaredVariables.contains(
                variable)) {

            throw new SemanticException(
                    "Variable '" +
                    variable +
                    "' not declared."
            );
        }
    }

    private void analyzeVarDeclaration(
            VarDeclarationNode node) {

        String variable =
                node.getVariableName();

        if (declaredVariables.contains(
                variable)) {

            throw new SemanticException(
                    "Variable '" +
                    variable +
                    "' already declared."
            );
        }

        declaredVariables.add(variable);
    }

    private void analyzeAssignment(
            AssignmentNode node) {

        String variable =
                node.getVariableName();

        if (!declaredVariables.contains(
                variable)) {

            throw new SemanticException(
                    "Variable '" +
                    variable +
                    "' not declared."
            );
        }

        analyzeExpression(
                node.getExpression()
        );
    }
    private void analyzeExpression(
            ExpressionNode expression) {

        if (expression instanceof NumberNode) {
            return;
        }

        if (expression instanceof VariableNode) {

            String variable =
                    ((VariableNode) expression)
                            .getName();

            if (!declaredVariables.contains(
                    variable)) {

                throw new SemanticException(
                        "Variable '" +
                        variable +
                        "' not declared."
                );
            }

            return;
        }

        if (expression instanceof
                BinaryExpressionNode) {

            BinaryExpressionNode binary =
                    (BinaryExpressionNode)
                            expression;

            analyzeExpression(
                    binary.getLeft()
            );

            analyzeExpression(
                    binary.getRight()
            );
        }
    }
    private void analyzeWrite(
            WriteNode node) {

        String variable =
                node.getVariableName();

        if (!declaredVariables.contains(
                variable)) {

            throw new SemanticException(
                    "Variable '" +
                    variable +
                    "' not declared."
            );
        }
    }
}