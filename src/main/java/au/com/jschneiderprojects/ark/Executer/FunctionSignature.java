package au.com.jschneiderprojects.ark.Executer;

import java.util.HashMap;
import java.util.Map;

public class FunctionSignature {
    public String functionName;
    public String returnType;
    public HashMap<String, String> parameters; // name: type

    public FunctionSignature(String name, String returnType, HashMap<String, String> parameters) {
        this.functionName = name;
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public boolean matches(FunctionSignature signature) {
        return this.matches(signature, true);
    }

    public boolean matches(FunctionSignature signature, boolean compareReturnType) {
        // check return type - should also be accepted if signature.returnType is a subclass of this.returnType
        if (compareReturnType)
            if (!signature.returnType.equals(this.returnType))
                return false;

        for (Map.Entry<String, String> parameter : signature.parameters.entrySet())
            if (!parameter.getValue().equals(this.parameters.get(parameter.getKey())))
                return false;
        return true;
    }
}
