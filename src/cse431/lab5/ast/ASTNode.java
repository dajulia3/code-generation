package cse431.lab5.ast;

import java.util.ArrayList;
import java.util.List;

import cse431.lab5.semantic.SymbolInfo;
import cse431.lab5.visitor.ASTVisitor;
import cse431.lab5.visitor.SimpleVisitor;

/**
 * The AST Node interface.
 */
public interface ASTNode {

    /**
     * Returns the node type.
     */
    NodeType getNodeType();
    
    /**
     * Sets the symbol info.
     */
    void setSymbolInfo(SymbolInfo si);

    /**
     * Gets the symbol info.
     */
    SymbolInfo getSymbolInfo();
    
    /**
     * Accepts a simple visitor.
     */
    void accept(SimpleVisitor visitor) throws Exception;

    /**
     * Accepts visitors (the Visitor pattern).
     */
    void accept(ASTVisitor visitor);
    
    /**
     * Adds a node to the end of the list of children.
     */
    void addChild(ASTNode node);

    /**
     * Adds a node to the list of children at the specified location.
     */
    void addChild(int index, ASTNode node);
    
    /**
     * Adds a list of nodes to the end of the list of children.
     */
    void addChildren(List<ASTNode> nodes);
    
    /**
     * Returns the list of children.
     */
    ArrayList<ASTNode> getChildren();
    
    /**
     * Returns the child at the specified location.
     */
    ASTNode getChild(int index);
    
    void setParent(ASTNode parent);
    ASTNode getParent();
}
