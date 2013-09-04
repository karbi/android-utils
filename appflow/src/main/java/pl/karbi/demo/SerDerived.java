package pl.karbi.demo;

import java.util.ArrayList;
import java.util.List;

import pl.karbi.android.annotation.Parcelable;
import pl.karbi.android.annotation.ParcelableField;

@Parcelable
public class SerDerived extends SerBase {

    @ParcelableField
    private String field1 = "Some string";
    private int field2 = 123;
    private List<String> field3 = new ArrayList<String>();



}
