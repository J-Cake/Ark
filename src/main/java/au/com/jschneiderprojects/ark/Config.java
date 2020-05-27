package au.com.jschneiderprojects.ark;

import au.com.jschneiderprojects.ark.Lexer.LexConfig;

public class Config<Options> {
    public Options options;

    public Config(Options options) {
        this.options = options;
    }
}
