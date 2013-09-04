package pl.karbi.demo;

import android.os.Parcel;
import android.os.Parcelable;

public final class MessageInput implements Parcelable {

    public MessageInput(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(message);
    }

    public static final Parcelable.Creator<MessageInput> CREATOR = new Parcelable.Creator<MessageInput>() {

        @Override
        public MessageInput[] newArray(final int size) {
            return new MessageInput[size];
        }

        @Override
        public MessageInput createFromParcel(final Parcel source) {
            return new MessageInput(source.readString());
        }
    };

    private final String message;
}

