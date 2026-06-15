package generator;

import java.io.FileWriter;
import java.io.IOException;
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
import ast.IfNode;
import ast.ConditionNode;
import ast.WhileNode;

public class CGenerator {

    private StringBuilder code =
            new StringBuilder();

    public void generate(
            ProgramNode program,
            String outputFile) {

        code.append("#include <stdio.h>\n\n");

        code.append("int main() {\n\n");

        for (ASTNode node :
                program.getStatements()) {

            generateStatement(node);
        }

        code.append("\nreturn 0;\n");
        code.append("}\n");

        writeToFile(outputFile);
    }

    private void generateStatement(
            ASTNode node) {

        if (node instanceof VarDeclarationNode) {

            VarDeclarationNode var =
                    (VarDeclarationNode) node;

            code.append("    int ")
                    .append(var.getVariableName())
                    .append(";\n");
        }

        else if (node instanceof AssignmentNode) {

            AssignmentNode assignment =
                    (AssignmentNode) node;

            code.append("    ")
                    .append(
                            assignment.getVariableName()
                    )
                    .append(" = ")
                    .append(
                            generateExpression(
                                    assignment.getExpression()
                            )
                    )
                    .append(";\n");
        }

        else if (node instanceof WriteNode) {

            WriteNode write =
                    (WriteNode) node;

            code.append("    printf(\"%d\\n\", ")
                    .append(
                            write.getVariableName()
                    )
                    .append(");\n");
        }
        else if (node instanceof ReadNode) {

            ReadNode read =
                    (ReadNode) node;

            code.append(
                    "    scanf(\"%d\", &"
            )
            .append(
                    read.getVariableName()
            )
            .append(");\n");
        }
        else if (node instanceof IfNode) {

            generateIf(
                    (IfNode) node
            );
        }
        else if (node instanceof WhileNode) {

            generateWhile(
                    (WhileNode) node
            );
        }
    }
    private void generateWhile(
            WhileNode node) {

        ConditionNode condition =
                node.getCondition();

        code.append("    while(")

                .append(
                        generateExpression(
                                condition.getLeft()
                        )
                )

                .append(" ")

                .append(
                        condition.getOperator()
                )

                .append(" ")

                .append(
                        generateExpression(
                                condition.getRight()
                        )
                )

                .append(") {\n");

        for (ASTNode statement :
                node.getStatements()) {

            generateStatement(
                    statement
            );
        }

        code.append("    }\n");
    }
    private void generateIf(
            IfNode node) {

        ConditionNode condition =
                node.getCondition();

        code.append("    if(")

                .append(
                        generateExpression(
                                condition.getLeft()
                        )
                )

                .append(" ")

                .append(
                        condition.getOperator()
                )

                .append(" ")

                .append(
                        generateExpression(
                                condition.getRight()
                        )
                )

                .append(") {\n");

        for (ASTNode statement :
                node.getStatements()) {

            generateStatement(
                    statement
            );
        }

        code.append("    }\n");
    }
    private String generateExpression(
            ExpressionNode expression) {

        if (expression instanceof NumberNode) {

            return ((NumberNode) expression)
                    .getValue();
        }

        if (expression instanceof VariableNode) {

            return ((VariableNode) expression)
                    .getName();
        }

        if (expression instanceof BinaryExpressionNode) {

            BinaryExpressionNode binary =
                    (BinaryExpressionNode)
                            expression;

            return generateExpression(
                    binary.getLeft()
            )
            + " "
            + binary.getOperator()
            + " "
            + generateExpression(
                    binary.getRight()
            );
        }

        return "";
    }

    private void writeToFile(
            String fileName) {

        try {

            FileWriter writer =
                    new FileWriter(fileName);

            writer.write(code.toString());

            writer.close();

            System.out.println(
                    "C code generated successfully."
            );

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}