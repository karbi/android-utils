package pl.karbi.android.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Parcel;

public final class Parcelizator {

    private Parcelizator() {
        // utility class
    }

    public static void toParcel(final Parcel parcel, final Object object) throws ParcelizingException {

        final Class<?> objectClass = object.getClass();

        final ParcelizatorHelper helper = new ParcelizatorHelper(parcel, object);
        toParcel(helper, objectClass, getPolicy(objectClass));
    }

    private static void toParcel(final ParcelizatorHelper helper, final Class<?> objectClass,
                                 final Parcelable.Policy policy)
            throws ParcelizingException {

        helper.parcel(policy, objectClass);

        final Class<?> superClass = objectClass.getSuperclass();
        if (superClass != null) {
            LOG.trace("Superclass: {}", superClass.getCanonicalName());
            toParcel(helper, superClass, getPolicyDefault(superClass));
        }
    }

    private static Parcelable.Policy getPolicy(final Class<?> objectClass) throws ParcelizingException {
        final Parcelable parcelable = objectClass.getAnnotation(Parcelable.class);
        if (parcelable == null) {
            throw new ParcelizingException("Parcelized class must be annotated with "
                    + Parcelable.class.getCanonicalName());
        }

        return parcelable.policy();
    }

    private static Parcelable.Policy getPolicyDefault(final Class<?> objectClass) {
        final Parcelable parcelable = objectClass.getAnnotation(Parcelable.class);
        if (parcelable == null) {
            return Parcelable.Policy.ALL;
        }

        return parcelable.policy();
    }

    private static final Logger LOG = LoggerFactory.getLogger(Parcelizator.class);

}
