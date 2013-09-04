package pl.karbi.android.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.karbi.android.annotation.ObjectWalker.Visitor;

public final class LoggingVisitor implements Visitor {

    public LoggingVisitor(final Logger logger) {
        this.logger = logger;
    }

    @Override
    public void onField(final Object object, final Field field) {
        final String fieldName = field.getName();
        final Class<?> fieldType = field.getType();
        final Object fieldValue;

        try {
            fieldValue = field.get(object);
        } catch (final IllegalArgumentException e) {
            LOG.error("", e);
            return;
        } catch (final IllegalAccessException e) {
            LOG.error("", e);
            return;
        }

        logger.trace("    Field {} {} {} = {}", new Object[] {
            Modifier.toString(field.getModifiers()),
            fieldType,
            fieldName,
            fieldValue});

    }

    private final Logger logger;

    private static final Logger LOG = LoggerFactory.getLogger(LoggingVisitor.class);
}
