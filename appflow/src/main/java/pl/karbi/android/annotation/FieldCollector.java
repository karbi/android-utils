package pl.karbi.android.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

final class FieldCollector implements ObjectWalker.Visitor {

    public FieldCollector() {
        fields = new ArrayList<Field>();
    }

    public List<Field> getFields() {
        return fields;
    }

    @Override
    public void onField(final Object object, final Field field) {
        if (!Modifier.isStatic(field.getModifiers())) {
            fields.add(field);
        }
    }

    private final List<Field> fields;

}
