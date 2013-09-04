package pl.karbi.android.annotation;

import java.lang.reflect.Field;

import android.os.Parcel;

public final class ParcelizingVisitor implements ObjectWalker.Visitor {

    public ParcelizingVisitor(final Parcel parcel) {
        this.parcel = parcel;
    }

    @Override
    public void onField(final Object object, final Field field) {
        // TODO Auto-generated method stub

    }

    private final Parcel parcel;
}
