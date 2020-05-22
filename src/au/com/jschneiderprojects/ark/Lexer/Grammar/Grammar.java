package au.com.jschneiderprojects.ark.Lexer.Grammar;

import au.com.jschneiderprojects.ark.Formatter.Operators;
import au.com.jschneiderprojects.ark.Lexer.Token;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Grammar {
    TokenRule[] rules = new TokenRule[]{new TokenRule(TokenType.String, '"') {
            @Override
            boolean matches(String characters, ArrayList<Token> prev) {
                return this.isWrappedByDelimiter(characters);
            }
        }, new TokenRule(TokenType.Int) {
            @Override
            boolean matches(String characters, ArrayList<Token> prev) {
                Matcher regularExpression = Pattern.compile("^(((0d)?-?[0-9]+)|(0o-?[0-7]+)|(0x-?[0-9a-f]+)|(0B-?[01]+)|(0b[0-1]))$").matcher(characters);

                return regularExpression.find();
            }
        }, new TokenRule(TokenType.Float) {
            @Override
            boolean matches(String characters, ArrayList<Token> prev) {
                Matcher regularExpression = Pattern.compile("^((0d)?-?[0-9]+\\.[0-9]+)|(0o-?[0-7]+\\.[0-7]+)|(0x-?[0-9a-f]+\\.[0-9a-f]+)|(0B-?[01]+\\.[01]+)$").matcher(characters);

                return regularExpression.find();
            }
        }, new TokenRule(TokenType.Boolean) {
            @Override
            boolean matches(String characters, ArrayList<Token> prev) {
                Matcher regularExpression = Pattern.compile("^true|false$").matcher(characters);

                return regularExpression.find();
            }
        }, new TokenRule(TokenType.Operator) {
            @Override
            boolean matches(String characters, ArrayList<Token> prev) {
                return Operators.exists(characters);
            }
        }, new TokenRule(TokenType.Null) {
            @Override
            boolean matches(String characters, ArrayList<Token> prev) {
                return characters.equals("null");
            }
        }
    };
}
