package ast;

import java.util.List;

public class IfNode extends ASTNode {

    private ConditionNode condition;

    private List<ASTNode> statements;

    public IfNode(
            ConditionNode condition,
            List<ASTNode> statements) {

        this.condition = condition;
        this.statements = statements;
    }

    public ConditionNode getCondition() {
        return condition;
    }

    public List<ASTNode> getStatements() {
        return statements;
    }
}