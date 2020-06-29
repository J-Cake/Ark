package au.com.jschneiderprojects.ark.Formatter;

import au.com.jschneiderprojects.ark.Executer.Scope;
import au.com.jschneiderprojects.ark.Executer.Value;
import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;
import au.com.jschneiderprojects.ark.Lexer.Origin;
import au.com.jschneiderprojects.ark.Lexer.Token;

import java.util.ArrayList;

public class Reference extends Token {
    /*
        A class to allow for parsing and selecting of values given scopes and objects etc.
        Allows easy use of variables in shunting yard etc
     */

    public ArrayList<Token> selectorList;

    Reference(ArrayList<Token> reference, Origin o) {
        super(TokenType.Reference, Reference.stringify(reference), o);
        this.selectorList = reference;
    }

    public Value getValue(Scope parent) {
        return null;
    }

    public String stringify() {
        return Reference.stringify(this.selectorList);
    }

    static String stringify(ArrayList<Token> tokens) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Token t : tokens)
            stringBuilder.append(t.source);

        return stringBuilder.toString();
    }
}
