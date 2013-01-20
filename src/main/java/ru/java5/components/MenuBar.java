package ru.java5.components;

import javax.swing.ActionMap;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import ru.java5.main.App;
import ru.java5.model.ProblemUI;

/**
 *
 * @author Зайнуллин Радик
 * @since 2012.08.19
 */
public class MenuBar extends JMenuBar {
  public MenuBar() {
    ApplicationContext context = Application.getInstance(App.class).getContext();
    ActionMap actionMap = context.getActionManager().getActionMap(ProblemUI.class, ProblemUI.getInstance());
    
    JMenu fileMenu = new JMenu("Файл");
    JMenuItem fileMenuSaveItem = new JMenuItem(actionMap.get("save"));
    fileMenuSaveItem.add(new JSeparator());
    fileMenu.add(fileMenuSaveItem);
    add(fileMenu);
  }
}