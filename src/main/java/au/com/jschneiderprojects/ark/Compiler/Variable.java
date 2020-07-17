package au.com.jschneiderprojects.ark.Compiler;

abstract class Variable {
    String name;
    ReferenceType dataType;

    Variable(String name, ReferenceType dataType) {
        this.name = name;
        this.dataType = dataType;
    }
}
