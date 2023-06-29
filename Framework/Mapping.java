package etu1932.framework;

public class Mapping {
    String className;
    String method;
    
    public void setClassName(String className) {
        this.className = className;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public String getClassName(){
        return className;
    }
    public String getMethod() {
        return method;
    }

    public Mapping(){}

    public Mapping(String className, String method) {
        setClassName(className);
        setMethod(method);
    }
}
