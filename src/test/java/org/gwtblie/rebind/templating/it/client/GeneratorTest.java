package org.gwtblie.rebind.templating.it.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * Stub test for playing around.
 *
 * TODO: real unit tests
 *
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 *
 */
public class GeneratorTest extends GWTTestCase {

  private ToBeGenerated generated;

  @Override
  public String getModuleName() {
    return "org.gwtblie.rebind.templating.it.TestApp";
  }

  @Override
  protected void gwtSetUp() throws Exception {
    super.gwtSetUp();
    generated = GWT.create(ToBeGenerated.class);
  }

  public void testIt() {
    System.out.println(generated.get());
    assertEquals("yes", generated.get().get("selection"));
    assertEquals("2", generated.get().get("selectionPossibleValuesSize"));
    assertEquals("42", generated.get().get("singleValued"));
    assertEquals("42", generated.get().get("singleValued2"));
    assertEquals("2", generated.get().get("multiValuedSize"));
    assertEquals("test A", generated.get().get("multiValued1"));
    assertEquals("test B", generated.get().get("multiValued2"));
  }

}
