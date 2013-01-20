package ru.java5.action;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import javax.swing.AbstractAction;
import ru.java5.model.ParagraphUI;

/**
 *
 * @author Зайнуллин Радик
 * @since 2012.08.24
 */
public class MixedItemAction extends AbstractAction implements PropertyChangeListener {
  public MixedItemAction() {
    ParagraphUI.getInstance().addPropertyChangeListener(this);
    putValue(NAME, "Добавить текст/формулу");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); // буфер обмена
      if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
        String text = (String) clipboard.getData(DataFlavor.stringFlavor);
        if (text.trim().equals("")) return;
        ParagraphUI.getInstance().add(text);
      }
    } catch (UnsupportedFlavorException | IOException ex) {
      org.apache.log4j.Logger.getLogger(MixedItemAction.class).log(org.apache.log4j.Level.ERROR, null, ex);
    }
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String currentStatus = ParagraphUI.getInstance().status;
    if (currentStatus.equals(ParagraphUI.STATUS_IMAGE)) setEnabled(false);
    else setEnabled(true);
  }
}