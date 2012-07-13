package cse431.lab5.ast;

public class StringLiteralNode extends BaseASTNode {
    private String value;
    
    public StringLiteralNode(String value) {
        super(NodeType.STRING_LITERAL);
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return getNodeType() + " (" + value + ")";
    }
}
