package test;

public class BaseType {

    private int value = 0;

    public int retParam(int param) {
        value += param;
        return value;
    }
}
