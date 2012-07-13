package cse431.lab5;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.lang.StringUtils;

import cse431.lab5.ast.CompilationUnit;
import cse431.lab5.codegen.CodeGenVisitor;
import cse431.lab5.codegen.LocalVarMapVisitor;
import cse431.lab5.parser.Parser;
import cse431.lab5.scanner.Scanner;
import cse431.lab5.semantic.TypeVisitor;
import cse431.lab5.visitor.PrinterVisitor;

public class Compiler {
    
    /**
     * Main entry point for the cse431 compiler.  Runs the compiler on the specified file.
     * 
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java Compiler file-name");
            System.exit(-1);
        }
        
        Compiler compiler = new Compiler(args[0]);
        try {
            compiler.run();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    private String source;

    private Compiler(String source) {
        this.source = source;
    }
    
    private void run() throws Exception {
        File sourceFile = verifySource(source);
        File parentDir = null;
        process(sourceFile, parentDir);
    }
    
    private File verifySource(String source) throws Exception {
        if (StringUtils.isEmpty(source)) {
            throw new Exception("Source file must be specified");
        }

        File sourceFile = new File(source);

        if (!sourceFile.exists()) {
            throw new Exception("Source \"" + source + "\" does not exist");
        }

        return sourceFile;
    }
    
    /**
     * Pass one: parse the source files and generate the ASTs.
     */
    private void process(File sourceFile, File parentDir) throws Exception {
        if (sourceFile.isFile()) {
            processFile(sourceFile, parentDir);
        } else {
            throw new Exception("Source \"" + sourceFile.getAbsolutePath() + "\" is not a normal file");
        }
    }

    private void processFile(File sourceFile, File parentDir) throws Exception {
        String baseName = sourceFile.getName();
        String[] parts = baseName.split("\\.");
        
        if (parts.length != 2 || !"djav".equals(parts[1])) {
            throw new Exception("File \"" + sourceFile.getAbsolutePath() + "\" is not a source file");
        }

        CompilationUnit cu = parse(sourceFile);
        performSemanticAnalysis(cu);
        performLocalVarMapping(cu);
        //   printAST(cu);
        generateCode(cu);
    }
    
    private CompilationUnit parse(File sourceFile) throws Exception {
        FileInputStream istream = new FileInputStream(source);
        DataInputStream distream = new DataInputStream(new BufferedInputStream(istream));

        Parser parser = new Parser(new Scanner(distream));
        parser.parse();
        return parser.getRoot();
    }

    /**
     * Debugging pass: print the ASTs.
     */
    private void printAST(CompilationUnit cu) {
        cu.accept(new PrinterVisitor(System.out));
    }
    
    private void performSemanticAnalysis(CompilationUnit cu) throws Exception {
        cu.accept(new TypeVisitor());
    }
    
    private void performLocalVarMapping(CompilationUnit cu) throws Exception {
        cu.accept(new LocalVarMapVisitor());
    }
    
    private void generateCode(CompilationUnit cu) throws Exception {
        cu.accept(new CodeGenVisitor(System.out));
    }
}
