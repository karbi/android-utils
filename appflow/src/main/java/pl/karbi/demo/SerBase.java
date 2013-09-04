package pl.karbi.demo;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Bundle;

public class SerBase {

    public final void serializeFields() {
        final Field[] fields = getClass().getDeclaredFields();

        for (final Field field : fields) {
            final String name = field.getName();
            final String type = field.getType().getCanonicalName();

            LOG.trace("field {} of type {}", name, type);
        }
    }

    public final Bundle serializeToBundle() {
        // boolean
        // byte
        // char
        // CharSequence
        // double
        // float
        // int
        // long
        // short
        // String

        LOG.trace(">> serializeToBundle");

        final Bundle bundle = new Bundle();

        for (final Field field : getClass().getDeclaredFields()) {
            serializeField(bundle, field);
        }

        return bundle;
    }

    private void serializeField(final Bundle bundle, final Field field) {
        final String name = field.getName();
        final String type = field.getType().getCanonicalName();

        field.setAccessible(true);
        LOG.trace("field {} of type {}", name, type);
        try {
            final Object object = field.get(this);
            LOG.trace("value {}, type {}", object, object.getClass().getCanonicalName());
        } catch (final IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            LOG.error("", e);
        }
    }

    private static final Logger LOG = LoggerFactory.getLogger(SerBase.class);
}
