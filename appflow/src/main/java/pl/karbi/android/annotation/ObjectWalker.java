package pl.karbi.android.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class ObjectWalker {

    public interface Visitor {
        void onField(Object object, Field field);
    }

    public ObjectWalker(final Object object) {
        this.object = object;
        this.visitors = new ArrayList<ObjectWalker.Visitor>();
    }

    public void addVisitor(final Visitor visitor) {
        visitors.add(visitor);
    }

    public void removeVisitor(final Visitor visitor) {
        visitors.remove(visitor);
    }

    public void walk() {
        walk(object.getClass());
    }

    private void walk(final Class<?> objectClass) {
        final Class<?> superClass = objectClass.getSuperclass();
        if (superClass != null) {
            walk(superClass);
        }

        for (final Field field : objectClass.getDeclaredFields()) {
            visitField(field);
        }
    }

    private void visitField(final Field field) {
        for (final Visitor visitor : visitors) {
            field.setAccessible(true);

            visitor.onField(object, field);
        }
    }

    private final Object object;
    private List<Visitor> visitors;
}
