package pl.karbi.android.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.karbi.android.annotation.Parcelable.Policy;
import android.os.Parcel;

final class ParcelizatorHelper {

    public ParcelizatorHelper(final Parcel target, final Object parcelizedObject) {
        this.parcelizedObject = parcelizedObject;
        this.target = target;
    }

    public void parcel(final Parcelable.Policy policy, final Class<?> objectClass) throws ParcelizingException {
        LOG.trace("Parcelling class {}", objectClass.getCanonicalName());
        for (final Field field : objectClass.getDeclaredFields()) {
            if (isParcelable(policy, field)) {
                parcelField(field);
            }
        }
    }

    private boolean isParcelable(final Parcelable.Policy policy, final Field field) {
        if (policy == Policy.ALL) {
            return true;
        }

        final ParcelableField pf = field.getAnnotation(ParcelableField.class);
        return pf != null;
    }

    private void parcelField(final Field field) throws ParcelizingException {
        field.setAccessible(true);

        final String fieldName = field.getName();
        final Class<?> fieldType = field.getType();
        final Object fieldValue;

        try {
            fieldValue = field.get(parcelizedObject);
        } catch (final IllegalArgumentException e) {
            throw new ParcelizingException("Error parcelizing field " + fieldName, e);
        } catch (final IllegalAccessException e) {
            throw new ParcelizingException("Error parcelizing field " + fieldName, e);
        }

        LOG.trace("    Field {} {} {} = {}", new Object[] {
            Modifier.toString(field.getModifiers()),
            fieldType,
            fieldName,
            fieldValue});
    }

    private final Object parcelizedObject;

    private final Parcel target;

    private static final Logger LOG = LoggerFactory.getLogger(ParcelizatorHelper.class);

}
