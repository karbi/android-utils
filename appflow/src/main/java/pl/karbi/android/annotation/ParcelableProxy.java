package pl.karbi.android.annotation;

import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Parcel;
import android.os.Parcelable;

public final class ParcelableProxy implements Parcelable {

    public ParcelableProxy(final Object object) {
        this.object = object;
    }

    private ParcelableProxy(final Parcel source) {
        final String className = source.readString();

        try {
            final Class<?> objectClass = Class.forName(className);

            object = objectClass.newInstance();
            //object = newInstanceSkippingConstructor(objectClass);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        for (final Field field : getFields(object)) {
            deparcelField(source, field);
        }
    }

    private static Object newInstanceSkippingConstructor(final Class<?> objectClass) throws Exception {

        final Method newInstance = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class, Class.class);
        newInstance.setAccessible(true);
        return newInstance.invoke(null, objectClass, Object.class);

    }

    public <T> T getObject() {
        return (T) object;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(object.getClass().getCanonicalName());

        for (final Field field : getFields(object)) {
            parcelField(dest, field);
        }
    }

    private void parcelField(final Parcel dest, final Field field) {
        final Class<?> fieldClass = field.getType();

        Object value;
        try {
            value = field.get(object);
        } catch (final IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }

        if (fieldClass.equals(int.class)) {
            dest.writeInt((Integer) value);
        } else if (fieldClass.equals(String.class)) {
            dest.writeString((String) value);
        } else {
            throw new IllegalArgumentException("Parcelizing field of types " + fieldClass.getCanonicalName()
                    + " not implemented yet");
        }
    }

    private void deparcelField(final Parcel source, final Field field) {
        final Class<?> fieldClass = field.getType();

        final Object value;
        if (fieldClass.equals(int.class)) {
            value = source.readInt();
        } else if (fieldClass.equals(String.class)) {
            value = source.readString();
        } else {
            throw new IllegalArgumentException("Parcelizing field of types " + fieldClass.getCanonicalName()
                    + " not implemented yet");
        }

        try {
            field.set(object, value);
        } catch (final IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }


    private static List<Field> getFields(final Object object) {
        final Class<?> objectClass = object.getClass();

        final List<Field> cachedFields = FIELDS_CACHE.get(objectClass);
        if (cachedFields != null) {
            return cachedFields;
        }

        final ObjectWalker walker = new ObjectWalker(object);

        final FieldCollector collector = new FieldCollector();
        walker.addVisitor(collector);

        walker.walk();

        final List<Field> fields = collector.getFields();
        FIELDS_CACHE.put(objectClass, fields);
        return fields;
    }

    private static final Map<Class<?>, List<Field>> FIELDS_CACHE = new HashMap<Class<?>, List<Field>>();

    private final Object object;

    public static final Creator<ParcelableProxy> CREATOR = new Parcelable.Creator<ParcelableProxy>() {

        @Override
        public ParcelableProxy[] newArray(final int size) {
            return new ParcelableProxy[size];
        }

        @Override
        public ParcelableProxy createFromParcel(final Parcel source) {
            return new ParcelableProxy(source);
        }
    };

    private static final Logger LOG = LoggerFactory.getLogger(ParcelableProxy.class);
}
