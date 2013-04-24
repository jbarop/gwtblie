package org.gwtblie.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

/**
 * A widget that represents a simple &lt;label&gt; element.
 *
 * <p>
 * The &lt;label&gt; tag defines a label for an &lt;input&gt; element. It provides usability
 * improvement for the user, because if the &lt;label&gt; is clicked it toggles the &lt;input&gt;
 * element.
 * </p>
 *
 * <p>
 * Every &lt;input&gt; which get's bound to an {@link InputLabel} get's associated with its own
 * unique id.
 * </p>
 *
 * <h2>Usage:</h2>
 *
 * <pre>
 * &lt;g:TextBox ui:field="userName" /&gt;
 * &lt;p:FormLabel on="{userName}" text="Username:" /&gt
 * </pre>
 *
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 *
 */
public class InputLabel extends Widget implements HasText {
  /**
   * Construct a new {@link InputLabel}.
   *
   * <p>
   * The <code>id</code> attribute of the given input widget will be set to an new unique id.
   * </p>
   *
   * @param input The input widget which should referenced by this label.
   */
  @UiConstructor
  public InputLabel(final Widget input) {
    String id = DOM.createUniqueId();

    setElement(Document.get().createLabelElement());
    getElement().setAttribute("for", id);
    input.getElement().setId(id);
  }

  @Override
  public void setText(final String text) {
    getElement().setInnerText(text);
  }

  @Override
  public String getText() {
    return getElement().getInnerText();
  }
}
