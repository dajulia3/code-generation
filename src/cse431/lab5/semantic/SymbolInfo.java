package cse431.lab5.semantic;

import cse431.lab5.ast.ASTNode;
import cse431.lab5.ast.PrimitiveType;

public class SymbolInfo {
    private ASTNode node;
    private PrimitiveType type;
    private int localVarIndex = -1;
    
    public SymbolInfo(ASTNode node) {
        this.node = node;
    }
    
    public ASTNode getNode() {
        return node;
    }

    public PrimitiveType getType() {
        return type;
    }

    public void setType(PrimitiveType type) {
        this.type = type;
    }
    
    public int getLocalVarIndex() {
        return localVarIndex;
    }
    
    public void setLocalVarIndex(int index) {
        this.localVarIndex = index;
    }
    
    public String toString() {
        String str = "SymbolInfo: " + type;
        
        if (localVarIndex != -1) {
            str += ", lv = " + localVarIndex;
        }
        
        return str;
    }
}
