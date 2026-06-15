package ast;

public class WriteNode extends ASTNode {

    private String variableName;

    public WriteNode(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }
}