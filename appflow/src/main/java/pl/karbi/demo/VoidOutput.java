package pl.karbi.demo;

import android.os.Parcel;
import android.os.Parcelable;

public final class VoidOutput implements Parcelable {

    public VoidOutput() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
    }

    public static final Parcelable.Creator<VoidOutput> CREATOR = new Parcelable.Creator<VoidOutput>() {

        @Override
        public VoidOutput[] newArray(final int size) {
            return new VoidOutput[size];
        }

        @Override
        public VoidOutput createFromParcel(final Parcel source) {
            return new VoidOutput();
        }
    };
}

