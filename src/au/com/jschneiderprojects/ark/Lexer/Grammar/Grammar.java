package au.com.jschneiderprojects.ark.Lexer.Grammar;

import au.com.jschneiderprojects.ark.Formatter.Operators;
import au.com.jschneiderprojects.ark.Lexer.Lexer;
import au.com.jschneiderprojects.ark.Lexer.Token;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Grammar {
    final TokenRule String = new TokenRule(TokenType.String, '"') {
        @Override
        boolean matches(String characters, ArrayList<Token> prev, int indentation) {
            return this.isWrappedByDelimiter(characters);
        }
    };
    final TokenRule Int = new TokenRule(TokenType.Int) {
        @Override
        boolean matches(String characters, ArrayList<Token> prev, int indentation) {
            Matcher regularExpression = Pattern.compile("^(((0d)?-?[0-9]+)|(0o-?[0-7]+)|(0x-?[0-9a-f]+)|(0B-?[01]+)|(0b[0-1]))$").matcher(characters);

            return regularExpression.find();
        }
    };
    final TokenRule Float = new TokenRule(TokenType.Float) {
        @Override
        boolean matches(String characters, ArrayList<Token> prev, int indentation) {
            Matcher regularExpression = Pattern.compile("^((0d)?-?[0-9]+\\.[0-9]+)|(0o-?[0-7]+\\.[0-7]+)|(0x-?[0-9a-f]+\\.[0-9a-f]+)|(0B-?[01]+\\.[01]+)$").matcher(characters);

            return regularExpression.find();
        }
    };
    final TokenRule Boolean = new TokenRule(TokenType.Boolean) {
        @Override
        boolean matches(String characters, ArrayList<Token> prev, int indentation) {
            Matcher regularExpression = Pattern.compile("^true|false$").matcher(characters);

            return regularExpression.find();
        }
    };
    final TokenRule Operator = new TokenRule(TokenType.Operator) {
        @Override
        boolean matches(String characters, ArrayList<Token> prev, int indentation) {
            return Operators.exists(characters);
        }
    };
    final TokenRule Null = new TokenRule(TokenType.Null) {
        @Override
        boolean matches(String characters, ArrayList<Token> prev, int indentation) {
            return characters.equals("null");
        }
    };
    final TokenRule Block = new TokenRule(TokenType.Block) {
        @Override
        boolean matches(String characters, ArrayList<Token> prev, int indentation) {
            return prev.get(prev.size() - 1).indentationLevel <= indentation;
        }
    };

    TokenRule[] rules = new TokenRule[]{String, Int, Float, Boolean, Operator, Null, Block};
}
