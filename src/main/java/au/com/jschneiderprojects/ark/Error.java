package au.com.jschneiderprojects.ark;

import au.com.jschneiderprojects.ark.Executer.Scope;
import au.com.jschneiderprojects.ark.Lexer.Origin;

public class Error {
    String message;
    ErrorType type;
    Origin origin;

    StringBuilder verbose;

    public Error(Origin origin, ErrorType type, String message) {
        this.message = message;
        this.type = type;
        this.origin = origin;

        this.verbose = new StringBuilder();
    }

    void throwError(Scope parent, boolean verbose) {
        // Break flow and print traverse up scope to generate error stack.

        String errorType = this.type.toString().substring(0, 1).toUpperCase() + this.type.toString().substring(1).toLowerCase() + "Error";

        String errMsg = errorType + " - " + this.message.trim() + " at " + this.origin.file.trim() + ": " + this.origin.line + ":" + this.origin.charIndex;
        System.err.println(errMsg);

        if (verbose)
            System.err.println(this.verbose.toString());
    }

    public Error verbose(String msg) {
        this.verbose
                .append(" -> Verbose - ")
                .append(msg.trim());
        return this; // for chaining purposes
    }
}
