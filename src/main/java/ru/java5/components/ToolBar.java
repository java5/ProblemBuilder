package ru.java5.components;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JToolBar;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import ru.java5.main.App;
import ru.java5.model.ProblemUI;

/**
 *
 * @author Зайнуллин Радик
 * @since 2012.08.20
 */
public class ToolBar extends JToolBar {

  public ToolBar() {
    ApplicationContext context = Application.getInstance(App.class).getContext();
    ActionMap actionMap = context.getActionManager().getActionMap(ProblemUI.class, ProblemUI.getInstance());
    setName("Работа с правой панелью");

    JButton btnAdd = new JButton("Добавить");
    btnAdd.setToolTipText("Добавить готовый параграф в задачку");
    btnAdd.addActionListener(actionMap.get("addNode"));
    add(btnAdd);

    JButton btnUndo = new JButton("Шаг назад");
    btnUndo.setToolTipText("Удалить последний добавленный параграф");
    btnUndo.addActionListener(actionMap.get("back"));
    add(btnUndo);

    JButton btnToggle = new JButton("Сменить область добавления");
    btnToggle.setToolTipText("Выбрать куда добавлять параграф");
    btnToggle.addActionListener(actionMap.get("toggle"));
    add(btnToggle);
  }
}