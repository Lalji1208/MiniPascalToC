package ast;

import java.util.List;

public class WhileNode extends ASTNode {

    private ConditionNode condition;

    private List<ASTNode> statements;

    public WhileNode(
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