package ast;

public class AssignmentNode
        extends ASTNode {

    private String variableName;

    private ExpressionNode expression;

    public AssignmentNode(
            String variableName,
            ExpressionNode expression) {

        this.variableName = variableName;
        this.expression = expression;
    }

    public String getVariableName() {
        return variableName;
    }

    public ExpressionNode getExpression() {
        return expression;
    }
}