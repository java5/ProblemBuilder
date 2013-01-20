package ru.java5.main;

import java.awt.Dimension;
import java.util.EventObject;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.View;
import ru.java5.data.Handler;
import ru.java5.model.ProblemUI;
import ru.java5.pane.ParagraphPane;
import ru.java5.pane.ProblemPane;

/**
 *
 * @author Зайнуллин Радик
 * @since 2012.08.18
 */
public class App extends SingleFrameApplication {
  @Override
  protected void startup() {
    addExitListener(
      new Application.ExitListener() {
        public boolean canExit(EventObject e) {
          int option = JOptionPane.showConfirmDialog(getMainFrame(), "Really Exit?");
          return option == JOptionPane.YES_OPTION;
        }
        public void willExit(EventObject e) {}
      });
    View view = getMainView();
    view.setMenuBar(new ru.java5.components.MenuBar());
    view.setToolBar(new ru.java5.components.ToolBar());
    view.setComponent(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new ProblemPane(),
            new ParagraphPane()));// setResizeWeight(0.5);
    getMainFrame().setMinimumSize(new Dimension(400, 600));
    show(view);
  }
  /* До начала работы необходимо идентифицировать задачку */
  @Override
  protected void initialize(String[] args) {
    // показ модального окна, в котором нужно ввести идентификацию задачи
    InfoPane infoPane = new InfoPane();
    int result = JOptionPane.showConfirmDialog(null, infoPane,
            "Идентифицируйте новую задачу", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      ProblemUI.getInstance().setIdentification(infoPane.getId(), infoPane.getPath());
    } else exit();
  }

  @Override
  protected void shutdown() { getMainFrame().setVisible(false); }

  @Override
  protected void ready() { 
    Handler.install(); 
    ProblemUI.getInstance().toggle();
  }

  public static void main(String[] args) {
    launch(App.class, args);
  }
  private  class InfoPane extends JPanel {
    private JTextField path, id;

    public InfoPane() {
      path = new JTextField(10);
      id = new JTextField(10);
      add(new JLabel("Path:"));
      add(path);
      add(Box.createHorizontalStrut(15)); // a spacer
      add(new JLabel("ID:"));
      add(id);
    }
    public String getPath() { return path.getText(); }
    public String getId() { return id.getText(); }
  }
}