package ast;

public class ConditionNode {

    private ExpressionNode left;

    private String operator;

    private ExpressionNode right;

    public ConditionNode(
            ExpressionNode left,
            String operator,
            ExpressionNode right) {

        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public String getOperator() {
        return operator;
    }

    public ExpressionNode getRight() {
        return right;
    }
}