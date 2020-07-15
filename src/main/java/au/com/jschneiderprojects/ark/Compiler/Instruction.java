package au.com.jschneiderprojects.ark.Compiler;

import java.util.List;

public class Instruction {
    InstructionType type;
    List<Primitive> parameter;
    public Instruction(InstructionType type, List<Primitive> parameter) {
        this.type = type;
        this.parameter = parameter;
    }

    public String toString() {
        return this.type.toString() + " - " + this.parameter.toString();
    }
}
