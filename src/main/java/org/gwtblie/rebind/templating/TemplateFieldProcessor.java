package org.gwtblie.rebind.templating;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Map;

import org.gwtblie.rebind.templating.TemplatedGenerator.TypeInformation;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.dev.util.collect.HashMap;
import com.google.inject.Inject;

import freemarker.template.Template;
import freemarker.template.TemplateException;

class TemplateFieldProcessor {
  private final TreeLogger treeLogger;

  private final GeneratorContext generatorContext;

  private final TemplatedGenerator generator;

  @Inject
  public TemplateFieldProcessor(
      TreeLogger treeLogger,
      GeneratorContext generatorContext,
      TemplatedGenerator generator) {
    this.treeLogger = treeLogger;
    this.generatorContext = generatorContext;
    this.generator = generator;
  }

  void process(TypeInformation typeInformation, PrintWriter out, String requestedType) throws UnableToCompleteException {
    try {
      freemarker.template.Configuration cfg = new freemarker.template.Configuration();
      cfg.setClassForTemplateLoading(this.getClass(), "/");
      Template template = cfg.getTemplate(getTemplate());

      Map<String, Object> dataModel = new HashMap<String, Object>();
      dataModel.put("packageName", typeInformation.getPackageName());
      dataModel.put("className", typeInformation.getSimpleName());
      dataModel.put("requestedType", requestedType);

      for (Field field : Util.getFields(generator.getClass(), TemplateField.class)) {
        String fieldName = field.getName();
        Object fieldObj = field.get(generator);
        dataModel.put(fieldName, fieldObj);
      }

      template.process(dataModel, out);
      generatorContext.commit(treeLogger, out);
      BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
      template.process(dataModel, log);

    } catch (IOException e) {
      treeLogger.log(Type.ERROR, "yo man", e);
      throw new UnableToCompleteException();
    } catch (TemplateException e) {
      treeLogger.log(Type.ERROR, "yo man", e);
      throw new UnableToCompleteException();
    } catch (IllegalAccessException e) {
      treeLogger.log(Type.ERROR, "yo man", e);
      throw new UnableToCompleteException();
    }
  }

  private String getTemplate() {
    TemplateFile templateFileAnnotation = generator.getClass().getAnnotation(TemplateFile.class);

    if (templateFileAnnotation == null) {
      return generator.getClass().getName().replace('.', '/') + ".ftl";
    } else {
      return templateFileAnnotation.value();
    }

  }
}
