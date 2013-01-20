package ru.java5.model;

import java.io.File;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdesktop.application.AbstractBean;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Зайнуллин Радик
 * @since 2012.08.18
 */
public class ProblemUI extends AbstractBean {
  private JFrame mainFrame;
  private static ProblemUI instance;
  private Problem problem;
  private History history;
  public boolean isTask = false;
  public File file;    

  private ProblemUI() {
    mainFrame = ((SingleFrameApplication)(SingleFrameApplication.getInstance())).getMainFrame();
    problem = new Problem();
    history = new History();
  }
  public static ProblemUI getInstance() {
    if (instance == null) instance = new ProblemUI();
    return instance;
  }
  @Action
  public void back() {
    problem.doc = history.back();
    firePropertyChange("doc", null, null);    
  }
  @Action
  public void toggle(){
    isTask = !isTask;
    if (isTask) JOptionPane.showMessageDialog(mainFrame, "Добавляем в сектор \"Условие\"");
    else JOptionPane.showMessageDialog(mainFrame, "Добавляем в сектор \"Решение\"");
  }
  @Action
  public void save(){ ru.java5.util.HardDisk.saveProblem(file, mainFrame, getProblemAsString()); }
  public void setIdentification(String id, String path) {
    problem.setIdentification(id, path);
    history.add(problem.doc);
    firePropertyChange("doc", null, null);
  }
  @Action
  public void addNode() {
    Node node = ParagraphUI.getInstance().reset();
    // если правая панель пуста, ничего не добавляем    
    if (node.getChildNodes().getLength() == 0) return; 
    
    if (isTask) problem.addNode(node, "task");
    else problem.addNode(node, "solution");
   
    history.add(problem.doc);   
    firePropertyChange("doc", null, null);    
  }
  public String getProblemAsString(){ return ru.java5.util.XML.toString(problem.doc); }

  private class Problem {
    private Document doc;
    // инициализируется "пустая" задачка
    protected Problem() { doc = ru.java5.util.Doc.getEmptyProblemDoc(); }
    public void setIdentification(String id, String path) {
      doc.getElementsByTagName("id").item(0).appendChild(doc.createTextNode(id));
      doc.getElementsByTagName("path").item(0).appendChild(doc.createTextNode(path));
    }
    void addNode(Node node, String section) {
      NodeList list = doc.getElementsByTagName(section);
      list.item(0).appendChild(doc.importNode(node, true));
    }
  }
  private class History {
    private Stack<String> stack;

    public History() { stack = new Stack<String>(); }
    public Document back(){
      if (stack.size() > 1) stack.pop();
      return ru.java5.util.Doc.stringToDocument(stack.peek());
    }
    public void add(Document doc){ stack.push(ru.java5.util.XML.toString(doc)); }
  }
}