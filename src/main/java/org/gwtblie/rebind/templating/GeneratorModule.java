package org.gwtblie.rebind.templating;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Guice module for a generator.
 *
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 *
 */
class GeneratorModule extends AbstractModule {
  private final TemplatedGenerator templatedGenerator;

  private final TreeLogger treeLogger;

  private final GeneratorContext generatorContext;

  GeneratorModule(
      TemplatedGenerator templatedGenerator,
      TreeLogger treeLogger,
      GeneratorContext generatorContext) {
    this.templatedGenerator = templatedGenerator;
    this.treeLogger = treeLogger;
    this.generatorContext = generatorContext;
  }

  @Override
  protected void configure() {
    bind(TreeLogger.class).toInstance(treeLogger);
    bind(GeneratorContext.class).toInstance(generatorContext);
    bind(PropertyOracle.class).toInstance(generatorContext.getPropertyOracle());
    bind(TypeOracle.class).toInstance(generatorContext.getTypeOracle());
    bind(TemplatedGenerator.class).toInstance(templatedGenerator);

    bind(SelectionProcessor.class).in(Singleton.class);
    bind(ConfigurationProcessor.class).in(Singleton.class);
  }
}
