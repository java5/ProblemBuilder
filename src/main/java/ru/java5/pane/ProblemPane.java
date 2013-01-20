package ru.java5.pane;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import ru.java5.model.ProblemUI;

/**
 *
 * @author Зайнуллин Радик
 * @since 2012.08.19
 */
public class ProblemPane extends JPanel implements PropertyChangeListener {
  private JEditorPane jEditorPane;

  public ProblemPane() {
    super(new BorderLayout());
    ProblemUI.getInstance().addPropertyChangeListener("doc", this);
    jEditorPane = new JEditorPane("text/html", "");
    jEditorPane.setEditable(false);
    JScrollPane scroller = new JScrollPane(jEditorPane,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    add(scroller, BorderLayout.CENTER);
    // Начальное html задачки
    this.propertyChange(new PropertyChangeEvent(new Object(), "doc", null, null));
  }

  public void propertyChange(PropertyChangeEvent evt) {
    String problemStr = ProblemUI.getInstance().getProblemAsString();
    String xml = problemStr.replaceAll("xmlns=\"http://www.java5.ru/problem\"", "");
    String html = ru.java5.util.XML.xsltTransform(xml, "html.xslt");
    jEditorPane.setText(html);
    jEditorPane.updateUI();
  }
}