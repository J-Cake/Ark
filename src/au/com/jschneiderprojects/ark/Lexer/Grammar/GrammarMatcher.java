package au.com.jschneiderprojects.ark.Lexer.Grammar;

import au.com.jschneiderprojects.ark.Config;
import au.com.jschneiderprojects.ark.Lexer.Token;

import java.util.ArrayList;

public class GrammarMatcher {
    Config<GrammarConfig> config;
    Grammar grammar;

    ArrayList<Token> tokenList;

    public int indentation = 0; // The number of times the block is indented (if there are 8 leading spaces with an indent of 4, the indent level is 2)
    int indent = 0; // The number of characters that make up a single indentation level (i.e. 4 spaces for a single indent)
    boolean isSpaceBased = false;

    public StringBuilder blockContent = new StringBuilder();

    public GrammarMatcher(Config<GrammarConfig> config) {
        this.config = config;
        this.grammar = new Grammar();
    }

    public boolean isOpen(String token) {
        if (token.length() > 0)
            for (TokenRule rule : grammar.rules)
                if (rule.isDelimiterOpen(token))
                    return true;
        return false;
    }

    public boolean isClosed(String token) {
        if (token.length() > 0)
            for (TokenRule rule : grammar.rules)
                if (rule.isDelimiterClosed(token))
                    return true;
        return false;
    }

    public TokenType resolve(String token) {
        if (token.length() > 0)
            for (TokenRule rule : grammar.rules)
                if (rule.matches(token, tokenList, indentation))
                    return rule.type;
        return null;
    }

    public void setTokenList(ArrayList<Token> list) {
        this.tokenList = list;
    }

    public void setIndentation(int indentation, int indent, boolean isSpaceBased) {
        System.out.println("Indent: " + indent);
        this.indentation = indentation;
        this.indent = indent;
        this.isSpaceBased = isSpaceBased;
    }
}
