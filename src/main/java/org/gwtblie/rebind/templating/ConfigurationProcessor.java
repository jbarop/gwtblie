package org.gwtblie.rebind.templating;

import java.lang.reflect.Field;
import java.util.Collection;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.ConfigurationProperty;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.inject.Inject;

/**
 * Processes all fields in the generator-class annotated with {@link Configuration} and assigns the
 * corresponding value from the gwt.xml file.
 *
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 *
 */
class ConfigurationProcessor extends PropertyProcessor<ConfigurationProperty> {
  private final TreeLogger treeLogger;

  private final PropertyOracle propertyOracle;

  @Inject
  ConfigurationProcessor(TemplatedGenerator generator, TreeLogger treeLogger, PropertyOracle propertyOracle) {
    super(generator, treeLogger, Configuration.class, "");
    this.treeLogger = treeLogger;
    this.propertyOracle = propertyOracle;
  }

  @Override
  protected String readPropertyName(Field field) {
    return field.getAnnotation(Configuration.class).name();
  }

  @Override
  protected ConfigurationProperty readGwtProperty(String propertyName) throws BadPropertyValueException {
    return propertyOracle.getConfigurationProperty(propertyName);
  }

  @Override
  protected void assign(Field field, ConfigurationProperty value) throws UnableToCompleteException {
    Class<?> fieldType = field.getType();

    if (ConfigurationProperty.class.isAssignableFrom(fieldType)) {
      assignField(field, value);
    } else if (Collection.class.isAssignableFrom(fieldType)) {
      assignField(field, value.getValues());
    } else {
      if (value.getValues().size() != 1) {
        treeLogger.log(Type.ERROR, "Incompatible (multi value) type");
        throw new UnableToCompleteException();
      }
      assignField(field, value.getValues().get(0));
    }
  }
}
