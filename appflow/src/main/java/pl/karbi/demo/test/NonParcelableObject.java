package pl.karbi.demo.test;

public class NonParcelableObject extends TestObject {

    public NonParcelableObject() {
        super(null);
    }

    public NonParcelableObject(final String v) {
        super(v);
    }

}
