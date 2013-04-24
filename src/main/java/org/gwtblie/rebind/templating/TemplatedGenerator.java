package org.gwtblie.rebind.templating;

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.inject.Guice;
import com.google.inject.Inject;

/**
 * Base class for template-driven generators.
 *
 * <pre>
 * Notes / TODO:
 * * Docs
 * * API is likely to change
 * </pre>
 *
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 *
 */
public abstract class TemplatedGenerator extends Generator {
  /**
   * Package and class name of the type which is being generated.
   */
  protected abstract class TypeInformation {
    abstract protected String getPackageName();

    abstract protected String getSimpleName();

    final String getFullName() {
      return getPackageName() + "." + getSimpleName();
    }
  }

  @Inject
  private SelectionProcessor selectionAnnotationProcessor;

  @Inject
  private ConfigurationProcessor configurationAnnotationProcessor;

  @Inject
  private TemplateFieldProcessor templateFieldAnnotationProcessor;

  @Override
  public final String generate(TreeLogger treeLogger, GeneratorContext generatorContext, String typeName)
      throws UnableToCompleteException {
    Guice.createInjector(new GeneratorModule(this, treeLogger, generatorContext)).injectMembers(this);
    selectionAnnotationProcessor.process();
    configurationAnnotationProcessor.process();

    TypeInformation typeInformation = getTypeInformation();
    PrintWriter printWriter = generatorContext.tryCreate(treeLogger, typeInformation.getPackageName(),
        typeInformation.getSimpleName());
    if (printWriter != null) {
      generate();
      templateFieldAnnotationProcessor.process(typeInformation, printWriter, typeName);
    }

    return typeInformation.getFullName();
  }

  /**
   * @return The {@link TypeInformation} of the type which is being to generated.
   */
  protected abstract TypeInformation getTypeInformation();

  /**
   * Generate the data-model which is used to populate the template.
   */
  protected abstract void generate() throws UnableToCompleteException;
}
