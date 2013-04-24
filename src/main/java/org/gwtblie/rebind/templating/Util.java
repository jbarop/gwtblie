package org.gwtblie.rebind.templating;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

class Util {
  /**
   * @return Return all fields which are annotated by the given annotation.
   */
  static Collection<Field> getFields(Class<?> generatorClass, Class<? extends Annotation> annotation) {
    ArrayList<Field> fields = new ArrayList<Field>();
    for (Field field : generatorClass.getFields()) {
      if (field.isAnnotationPresent(annotation)) {
        fields.add(field);
      }
    }

    return fields;
  }
}
