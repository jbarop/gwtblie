package org.gwtblie.client.ui;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Unit tests for {@link InputLabel}.
 *
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 *
 */
public class InputLabelGwtTest extends GWTTestCase {

  @Override
  public String getModuleName() {
    return "org.gwtblie.GWTBlie";
  }

  /**
   * Check that the widgets created with the correct underlying element.
   */
  public void testElement() {
    // Given
    TextBox input = new TextBox();
    InputLabel label = new InputLabel(input);

    // When

    // Then
    assertEquals("label", label.getElement().getTagName().toLowerCase());
  }

  /**
   * Check that text can be set and retrieved.
   */
  public void testText() {
    // Given
    String someText = "someText";
    TextBox input = new TextBox();
    InputLabel label = new InputLabel(input);

    // When
    assertEquals("", label.getText());
    assertEquals("", label.getElement().getInnerText());
    label.setText(someText);

    // Then
    assertEquals(someText, label.getText());
    assertEquals(someText, label.getElement().getInnerText());
  }

  /**
   * Check that the 'for' attribute of the label elements matches the 'id' attribute of the
   * referenced widget and ensure that their are unique.
   */
  public void testIds() {
    // When
    TextBox input1 = new TextBox();
    TextBox input2 = new TextBox();
    InputLabel label1 = new InputLabel(input1);
    InputLabel label2 = new InputLabel(input2);

    // Then
    assertEquals(label1.getElement().getAttribute("for"), input1.getElement().getAttribute("id"));
    assertEquals(label2.getElement().getAttribute("for"), input2.getElement().getAttribute("id"));
    assertFalse(label1.getElement().getAttribute("for").equals(label2.getElement().getAttribute("for")));
  }
}
