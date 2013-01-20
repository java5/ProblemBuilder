package ru.java5.pane;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JEditorPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import ru.java5.action.ImageItemAction;
import ru.java5.action.MixedItemAction;
import ru.java5.model.ParagraphUI;

/**
 * "Правая" панель где проиходит показ и построение отдельного параграфа для задачки
 * @author Зайнуллин Радик
 * @since 2012.08.19
 */
public class ParagraphPane extends JPanel implements PropertyChangeListener {
  private JEditorPane jEditorPane;
  
  public ParagraphPane() {
    setLayout(new BorderLayout());
    jEditorPane = new JEditorPane("text/html", "");
    jEditorPane.setEditable(false);
    JScrollPane scroller = new JScrollPane();
    scroller.getViewport().add(jEditorPane);
    add(scroller, BorderLayout.CENTER);
    
    jEditorPane.addMouseListener(
      new MouseAdapter() {
        private JPopupMenu popupMenu = initPopupMenu();
        @Override
        public void mousePressed(MouseEvent e) { checkForTriggerEvent(e); }
        @Override
        public void mouseReleased(MouseEvent e){ checkForTriggerEvent(e); }
        private void checkForTriggerEvent(MouseEvent e) {
          if (e.isPopupTrigger()) popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }        
        private JPopupMenu initPopupMenu(){
          popupMenu = new JPopupMenu();
          popupMenu.add(new JMenuItem(new MixedItemAction()));
          popupMenu.add(new JMenuItem(new ImageItemAction()));
          return popupMenu;
        }
      });
    ParagraphUI.getInstance().addPropertyChangeListener("node", this);
  }

  public void propertyChange(PropertyChangeEvent evt) {
      String str = ParagraphUI.getInstance().getNodeAsString();
      String xml = str.replaceAll("xmlns=\"http://www.java5.ru/problem\"", "");
      String html = ru.java5.util.XML.xsltTransform(xml, "mixed.xslt");
      jEditorPane.setText(html);
      jEditorPane.updateUI();
  }  
}