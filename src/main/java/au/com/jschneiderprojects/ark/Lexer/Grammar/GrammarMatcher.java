package au.com.jschneiderprojects.ark.Lexer.Grammar;

import au.com.jschneiderprojects.ark.Config;
import au.com.jschneiderprojects.ark.Lexer.Token;

import java.util.ArrayList;

public class GrammarMatcher {
    Config<GrammarConfig> config;
    Grammar grammar;

    ArrayList<Token> tokenList;

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
                if (rule.matches(token, tokenList))
                    return rule.type;
        return null;
    }

    public void setTokenList(ArrayList<Token> list) {
        this.tokenList = list;
    }
}
