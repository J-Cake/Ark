package au.com.jschneiderprojects.ark.Compiler;

import au.com.jschneiderprojects.ark.Executer.FunctionSignature;

public class FunctionEntry extends Variable {
    FunctionSignature signature;
    FunctionEntry(FunctionSignature signature) {
        super(signature.functionName, ReferenceType.FUNCTION);
        this.signature = signature;
    }
}
