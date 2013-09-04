package pl.karbi.demo.test;

import android.os.Parcel;
import android.os.Parcelable;

public final class ParcelableObject extends TestObject implements Parcelable {

    public ParcelableObject() {
        super(null);
    }

    public ParcelableObject(final String v) {
        super(v);
    }

    public ParcelableObject(final Parcel source) {
        super(null);
        f1 = source.readString();
        f2 = source.readString();
        f3 = source.readString();
        f4 = source.readString();
        f5 = source.readString();
        f6 = source.readString();
        f7 = source.readString();
        f8 = source.readString();
        f9 = source.readString();
        f10 = source.readString();
        f11 = source.readString();
        f12 = source.readString();
        f13 = source.readString();
        f14 = source.readString();
        f15 = source.readString();
        f16 = source.readString();
        f17 = source.readString();
        f18 = source.readString();
        f19 = source.readString();
        f20 = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(f1);
        dest.writeString(f2);
        dest.writeString(f3);
        dest.writeString(f4);
        dest.writeString(f5);
        dest.writeString(f6);
        dest.writeString(f7);
        dest.writeString(f8);
        dest.writeString(f9);
        dest.writeString(f10);
        dest.writeString(f11);
        dest.writeString(f12);
        dest.writeString(f13);
        dest.writeString(f14);
        dest.writeString(f15);
        dest.writeString(f16);
        dest.writeString(f17);
        dest.writeString(f18);
        dest.writeString(f19);
        dest.writeString(f20);
    }

    public static final Creator<ParcelableObject> CREATOR = new Parcelable.Creator<ParcelableObject>() {

        @Override
        public ParcelableObject createFromParcel(final Parcel source) {
            return new ParcelableObject(source);
        }

        @Override
        public ParcelableObject[] newArray(final int size) {
            return new ParcelableObject[size];
        }
    };

}
