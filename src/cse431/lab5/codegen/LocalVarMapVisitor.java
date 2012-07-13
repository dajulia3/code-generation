package cse431.lab5.codegen;

import cse431.lab5.ast.ASTNode;
import cse431.lab5.ast.IdentifierNode;
import cse431.lab5.semantic.SymbolInfo;
import cse431.lab5.visitor.SimpleVisitor;

/**
 * A visitor which sets the local var table index for local variables and method
 * arguments.
 */
public class LocalVarMapVisitor implements SimpleVisitor {
    private int nextLocalVarIndex;

    @Override
    public void visit(ASTNode node) throws Exception {
        switch (node.getNodeType()) {
        case METHOD_DECLARATION:
            visitMethodDeclarationNode(node);
            break;
            
        case VARIABLE_DECLARATION:
            visitVariableDeclarationNode(node);
            break;

        default:
            visitAllChildren(node);
        }
    }

    private void visitAllChildren(ASTNode node) throws Exception {
        for (ASTNode child : node.getChildren()) {
            child.accept(this);
        }
    }
    
    private void visitMethodDeclarationNode(ASTNode node) throws Exception {
        nextLocalVarIndex = 0;
        visitAllChildren(node);
    }

    private void visitVariableDeclarationNode(ASTNode node) throws Exception {
        IdentifierNode idNode = (IdentifierNode) node.getChild(0);
        SymbolInfo si = idNode.getSymbolInfo();
        si.setLocalVarIndex(nextLocalVarIndex);
        ++nextLocalVarIndex;
    }
}
