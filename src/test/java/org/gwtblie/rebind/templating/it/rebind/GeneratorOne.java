package org.gwtblie.rebind.templating.it.rebind;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.gwtblie.rebind.templating.Configuration;
import org.gwtblie.rebind.templating.PropertyPrefix;
import org.gwtblie.rebind.templating.Selection;
import org.gwtblie.rebind.templating.TemplateField;
import org.gwtblie.rebind.templating.TemplatedGenerator;
import org.gwtblie.rebind.templating.it.client.ToBeGenerated;

import com.google.gwt.core.ext.ConfigurationProperty;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.SelectionProperty;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.inject.Inject;

/**
 * First try of some sort of integration test.
 *
 * <p>
 * This is more of an demo to play around with. Good unit tests are TODO.
 * </p>
 *
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 *
 */
@PropertyPrefix("gwtblie.generator.test.")
public class GeneratorOne extends TemplatedGenerator {

  @Inject
  private TreeLogger treeLogger;

  @Inject
  private GeneratorContext generatorContext;

  @Inject
  private PropertyOracle propertyOracle;

  @Inject
  private TypeOracle typeOracle;

  @Selection
  public String selection;

  @Selection(name = "selection")
  public SelectionProperty selection2;

  @Configuration
  public String singleValued;

  @Configuration(name = "singleValued")
  public ConfigurationProperty singleValued2;

  @Configuration
  public List<String> multiValued;

  @Configuration(name = "multiValued")
  public ConfigurationProperty multiValued2;

  @TemplateField
  public Map<String, String> params;

  @Override
  public TypeInformation getTypeInformation() {
    return new TypeInformation() {
      @Override
      public String getSimpleName() {
        return ToBeGenerated.class.getSimpleName() + "Impl";
      }

      @Override
      public String getPackageName() {
        return ToBeGenerated.class.getPackage().getName();
      }
    };
  }

  @Override
  public void generate() throws UnableToCompleteException {

    treeLogger.log(Type.INFO, "Injected TreeLogger instance");

    if (generatorContext == null) {
      treeLogger.log(Type.ERROR, "generatorContext == null");
      throw new UnableToCompleteException();
    }

    if (propertyOracle == null) {
      treeLogger.log(Type.ERROR, "propertyOracle == null");
      throw new UnableToCompleteException();
    }

    if (typeOracle == null) {
      treeLogger.log(Type.ERROR, "typeOracle == null");
      throw new UnableToCompleteException();
    }

    params = new TreeMap<String, String>();
    params.put("selection", selection);
    params.put("selectionPossibleValuesSize", String.valueOf(selection2.getPossibleValues().size()));
    params.put("singleValued", singleValued);
    params.put("singleValued2", singleValued2.getValues().get(0));
    params.put("multiValuedSize", String.valueOf(multiValued.size()));
    params.put("multiValued1", multiValued.get(0));
    params.put("multiValued2", multiValued.get(1));
  }

}
