package ast;

import java.util.ArrayList;
import java.util.List;

public class ProgramNode extends ASTNode {

    private List<ASTNode> statements;

    public ProgramNode() {
        statements = new ArrayList<>();
    }

    public void addStatement(ASTNode node) {
        statements.add(node);
    }

    public List<ASTNode> getStatements() {
        return statements;
    }
}