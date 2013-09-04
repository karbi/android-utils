package pl.karbi.demo.test;

import pl.karbi.android.annotation.Parcelable;
import pl.karbi.android.annotation.Parcelable.Policy;
import pl.karbi.android.annotation.ParcelableField;

@Parcelable(policy = Policy.ANNOTATED)
public class Derived extends Basic {

    public Derived() {
        super(null);
    }

    public Derived(final String finalField) {
        super(finalField);
    }

    @ParcelableField
    private String derivedField = "df";

    private String nonParcelable = "np";


}
