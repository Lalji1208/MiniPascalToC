package ast;

public class NumberNode
        extends ExpressionNode {

    private String value;

    public NumberNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}