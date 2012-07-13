package cse431.lab5.semantic;

import cse431.lab5.ast.ASTNode;
import cse431.lab5.ast.ClassNode;
import cse431.lab5.ast.IdentifierNode;
import cse431.lab5.ast.PrimitiveType;
import cse431.lab5.ast.TypeNode;
import cse431.lab5.visitor.SimpleVisitor;

/**
 * A visitor which attaches SymbolInfo to Identfier nodes and method signatures
 * to Class nodes.
 */
public class TypeVisitor implements SimpleVisitor {
    private SymbolTable symbolTable = new SymbolTable();
    private PrimitiveType currentType;
    private ClassNode classNode;
    
    @Override
    public void visit(ASTNode node) throws Exception {
        switch (node.getNodeType()) {
        case BLOCK:
            visitBlockNode(node);
            break;
        
        case CLASS:
            visitClassNode(node);
            break;
            
        case IDENTIFIER:
            visitIdentifierNode(node);
            break;
            
        case LOCAL_VAR_DECLARATION:
            visitLocalVarDeclarationNode(node);
            break;
            
        case METHOD_DECLARATION:
            visitMethodDeclarationNode(node);
            break;
            
        case PARAMETER:
            visitParameterNode(node);
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
    
    private void visitBlockNode(ASTNode node) throws Exception {
        symbolTable.enterScope();
        visitAllChildren(node);
        symbolTable.leaveScope();
    }
    
    private void visitClassNode(ASTNode node) throws Exception {
        classNode = (ClassNode) node;
        
        symbolTable.enterScope();
        visitAllChildren(node);
        symbolTable.leaveScope();
    }
    
    private void visitIdentifierNode(ASTNode node) {
        IdentifierNode idNode = (IdentifierNode) node;
        String id = idNode.getValue();
        SymbolInfo si = symbolTable.get(id);
        node.setSymbolInfo(si);
    }

    private void visitLocalVarDeclarationNode(ASTNode node) throws Exception {
        TypeNode typeNode = (TypeNode) node.getChild(0);
        currentType = typeNode.getType();
        
        node.getChild(1).accept(this);
        
        currentType = null;
    }
    
    private void visitMethodDeclarationNode(ASTNode node) throws Exception {
        IdentifierNode idNode = (IdentifierNode) node.getChild(0);
        String methodName = idNode.getValue();
        
        String sig = methodName + "(";
        
        for (ASTNode paramNode : node.getChild(1).getChildren()) {
            TypeNode typeNode = (TypeNode) paramNode.getChild(1);
            sig += typeNode.getType().getSignature();
        }
        
        sig += ")";
        
        TypeNode typeNode = (TypeNode) node.getChild(2);
        sig += typeNode.getType().getSignature();
        
        classNode.putMethodSig(methodName, sig);
        
        symbolTable.enterScope();
        visitAllChildren(node);
        symbolTable.leaveScope();
    }
    
    private void visitParameterNode(ASTNode node) throws Exception {
        TypeNode typeNode = (TypeNode) node.getChild(1);
        currentType = typeNode.getType();
        
        node.getChild(0).accept(this);
        
        currentType = null;
    }
    
    private void visitVariableDeclarationNode(ASTNode node) throws Exception {
        IdentifierNode idNode = (IdentifierNode) node.getChild(0);
        String id = idNode.getValue();
        
        SymbolInfo si = new SymbolInfo(node);
        si.setType(currentType);
        
        symbolTable.put(id, si);
        
        visitAllChildren(node);
    }
}
