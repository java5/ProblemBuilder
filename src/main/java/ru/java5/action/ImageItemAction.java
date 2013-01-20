package ru.java5.action;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdesktop.application.SingleFrameApplication;
import ru.java5.model.ParagraphUI;

/**
 * Этот action слушает изменения в ParagraphUI - состояние узла node
 * @author Зайнуллин Радик
 * @since 2012.08.24
 */
public class ImageItemAction extends AbstractAction implements PropertyChangeListener {
  private JFrame mainFrame;
  public ImageItemAction(){
    mainFrame = ((SingleFrameApplication)(SingleFrameApplication.getInstance())).getMainFrame();
    putValue(NAME, "Добавить рисунок с подписью");
    ParagraphUI.getInstance().addPropertyChangeListener(this);  
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); // буфер обмена
      if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
        Image image = (Image) clipboard.getData(DataFlavor.imageFlavor);
        String caption = JOptionPane.showInputDialog("Введите подпись к рисунку.");
        ParagraphUI.getInstance().add(image, caption);
      } else {
        JOptionPane.showMessageDialog(mainFrame, "В буфере нет рисунка. Попробуйте еще раз.");
        return;
      }
    } catch (UnsupportedFlavorException | IOException ex) {
      org.apache.log4j.Logger.getLogger(ImageItemAction.class).log(org.apache.log4j.Level.ERROR, null, ex);
    }
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    // Если параграф что-нибудь содержит, то отключаем это действие
    if (!ParagraphUI.getInstance().status.equals(ParagraphUI.STATUS_EMPTY)) setEnabled(false);
    else setEnabled(true); 
  }
}