package ast;

public class VarDeclarationNode extends ASTNode {

    private String variableName;

    public VarDeclarationNode(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }
}