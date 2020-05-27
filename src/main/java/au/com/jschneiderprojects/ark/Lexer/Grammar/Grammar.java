package au.com.jschneiderprojects.ark.Lexer.Grammar;

import au.com.jschneiderprojects.ark.Formatter.Operators;
import au.com.jschneiderprojects.ark.Lexer.Token;
import au.com.jschneiderprojects.ark.Executer.Construct;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Grammar {
    final TokenRule String = new TokenRule(TokenType.String, '"') {
        @Override
        boolean matches(String characters, ArrayList<Token> prev) {
            return this.isWrappedByDelimiter(characters);
        }
    };
    final TokenRule Int = new TokenRule(TokenType.Int) {
        @Override
        boolean matches(String characters, ArrayList<Token> prev) {
            Matcher regularExpression = Pattern.compile("^(((0d)?-?[0-9]+)|(0o-?[0-7]+)|(0x-?[0-9a-f]+)|(0B-?[01]+)|(0b[0-1]))$").matcher(characters);

            return regularExpression.find();
        }
    };
    final TokenRule Float = new TokenRule(TokenType.Float) {
        @Override
        boolean matches(String characters, ArrayList<Token> prev) {
            Matcher regularExpression = Pattern.compile("^((0d)?-?[0-9]+\\.[0-9]+)|(0o-?[0-7]+\\.[0-7]+)|(0x-?[0-9a-f]+\\.[0-9a-f]+)|(0B-?[01]+\\.[01]+)$").matcher(characters);

            return regularExpression.find();
        }
    };
    final TokenRule Boolean = new TokenRule(TokenType.Boolean) {
        @Override
        boolean matches(String characters, ArrayList<Token> prev) {
            Matcher regularExpression = Pattern.compile("^true|false$").matcher(characters);

            return regularExpression.find();
        }
    };
    final TokenRule Operator = new TokenRule(TokenType.Operator) {
        @Override
        boolean matches(String characters, ArrayList<Token> prev) {
            return Operators.exists(characters);
        }
    };
    final TokenRule Null = new TokenRule(TokenType.Null) {
        @Override
        boolean matches(String characters, ArrayList<Token> prev) {
            return characters.equals("null");
        }
    };
    final TokenRule Block = new TokenRule(TokenType.Block) {
        @Override
        boolean matches(String characters, ArrayList<Token> prev) {
            return false; //TODO: detect block
//            return prev.get(prev.size() - 1).indentationLevel <= indentation;
        }
    };
    final TokenRule Reference = new TokenRule(TokenType.Reference) {
        @Override
        boolean matches(java.lang.String characters, ArrayList<Token> prev) {
            Matcher regularExpression = Pattern.compile("^[$_#&?@a-zA-Z][$_#&?@a-zA-Z0-9]*$").matcher(characters);

            return regularExpression.find();
        }
    };
    final TokenRule SConstruct = new TokenRule(TokenType.Construct) {
        @Override
        boolean matches(String characters, ArrayList<Token> prev) {
            Construct[] entries = Construct.values();

            for (Construct entry : entries)
                if (entry.identifier.equals(characters))
                    return true;
            return false;
        }
    };

    final TokenRule LeftParenthesis = new TokenRule(TokenType.LeftParenthesis) {
        @Override
        boolean matches(java.lang.String characters, ArrayList<Token> prev) {
            return characters.equals("(");
        }
    };
    final TokenRule RightParenthesis = new TokenRule(TokenType.RightParenthesis) {
        @Override
        boolean matches(java.lang.String characters, ArrayList<Token> prev) {
            return characters.equals(")");
        }
    };
    final TokenRule LeftBracket = new TokenRule(TokenType.LeftBracket) {
        @Override
        boolean matches(java.lang.String characters, ArrayList<Token> prev) {
            return characters.equals("[");
        }
    };
    final TokenRule RightBracket = new TokenRule(TokenType.RightBracket) {
        @Override
        boolean matches(java.lang.String characters, ArrayList<Token> prev) {
            return characters.equals("]");
        }
    };
    final TokenRule LeftBrace = new TokenRule(TokenType.LeftBrace) {
        @Override
        boolean matches(java.lang.String characters, ArrayList<Token> prev) {
            return characters.equals("{");
        }
    };
    final TokenRule RightBrace = new TokenRule(TokenType.RightBrace) {
        @Override
        boolean matches(java.lang.String characters, ArrayList<Token> prev) {
            return characters.equals("}");
        }
    };
    final TokenRule Comma = new TokenRule(TokenType.Comma) {
        @Override
        boolean matches(java.lang.String characters, ArrayList<Token> prev) {
            return characters.equals(",");
        }
    };
    final TokenRule SubReference = new TokenRule(TokenType.Subreference) {
        @Override
        boolean matches(java.lang.String characters, ArrayList<Token> prev) {
            return characters.equals(".");
        }
    };

    TokenRule[] rules = new TokenRule[]{LeftBrace, LeftBracket, LeftParenthesis, RightBrace, RightBracket, RightParenthesis, String, Int, Float, Boolean, Operator, SConstruct, Reference, Null, Block};
}
