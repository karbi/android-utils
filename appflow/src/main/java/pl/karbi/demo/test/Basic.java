package pl.karbi.demo.test;

public class Basic {

    private String privateField = "privf";

    protected String protectedField = "protf";

    public String publicField = "pubf";

    private final String finalField;

    public Basic(final String finalField) {
        this.finalField = finalField;
    }

}
