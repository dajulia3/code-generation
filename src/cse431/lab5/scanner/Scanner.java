package cse431.lab5.scanner;

import java.io.DataInputStream;
import java.io.IOException;

import cse431.lab5.parser.sym;

import java_cup.runtime.*;

/**
 * A CUP compatible scanner.
 */
public class Scanner implements java_cup.runtime.Scanner {

    protected Lexer scanner; // The JFlex produced scanner.
    protected Symbol nextToken; // The lookahead token.

    /**
     * Creates the scanner and reads the first lookahead token.
     * 
     * @param istream
     *            the input stream to tokenize.
     */
    public Scanner(DataInputStream istream) {
        super();

        scanner = new Lexer(istream);

        try {
            nextToken = scanner.yylex();
        } catch (IOException e) {
            nextToken = null;
        } catch (ScannerException e) {
            nextToken = null;
        }
    }

    /**
     * Peeks at the lookahead token without consuming it.
     * 
     * @return the next Token.
     */
    public Symbol peek() {
        return (nextToken == null) ? new Symbol(sym.EOF) : nextToken;
    }

    /**
     * Tests if the input stream has more tokens.
     * 
     * @return <tt>true</tt> if the input stream has more tokens, <tt>false</tt>
     *         otherwise.
     */
    public boolean hasNext() {
        return nextToken != null;
    }

    /**
     * Consumes and returns the next token.
     * 
     * @return the next Token.
     */
    public Symbol next_token() {
        Symbol old = peek();
        advance();
        return old;
    }

    /**
     * Consumes and returns the next token.
     * 
     * @return the next Token.
     */
    public Symbol nextToken() {
        return next_token();
    }

    /**
     * Consumes and returns the next token.
     * 
     * @return the next Token.
     */
    public Symbol next() {
        return next_token();
    }

    /**
     * Consumes a token without returning the value.
     */
    public void advance() {
        if (nextToken != null) {
            try {
                nextToken = scanner.yylex();
            } catch (IOException e) {
                nextToken = null;
            } catch (ScannerException e) {
                nextToken = null;
            }
        }
    }
}
