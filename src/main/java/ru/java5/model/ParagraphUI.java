package ru.java5.model;

import java.awt.Image;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jdesktop.application.AbstractBean;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import static ru.java5.util.Math.*;

/**
 * Диспетчер для параграфа
 * @author Зайнуллин Радик
 * @since 2012.08.18
 */
public class ParagraphUI extends AbstractBean {
  public static String STATUS_EMPTY = "empty", STATUS_IMAGE = "image", STATUS_TEXT  = "text";
  public String status; // состояние узла: пустой, добавлен текст, добавлено фото
  
  private static ParagraphUI instance;
  private Paragraph paragraph;

  private ParagraphUI() {
    paragraph = new Paragraph();
    status = STATUS_EMPTY;
  }

  public static ParagraphUI getInstance() {
    if (instance == null) instance = new ParagraphUI();
    return instance;
  }

  public void add(String text) {
    if (isValidMath(text)) paragraph.addFormula(text);
    else paragraph.addText(text);
    status = STATUS_TEXT;
    firePropertyChange("node", null, null);
  }

  public void add(Image image, String caption) {
    paragraph.addImage(image, caption);
    status = STATUS_IMAGE;
    firePropertyChange("node", null, null);
  }
  
  public String getNodeAsString(){
    return ru.java5.util.XML.toString(paragraph.node);
  }
  public Node reset() { // инициализируется "пустой" узел
    Node oldNode = paragraph.node;
    paragraph = new Paragraph();
    status = STATUS_EMPTY;
    firePropertyChange("node", null, null);    
    return oldNode;
  }   

  private class Paragraph {
    private Node node;
    private Document doc;

    protected Paragraph() { 
      doc = ru.java5.util.Doc.getEmptyProblemDoc();
      try { // инициализируется "пустой" узел
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        doc = factory.newDocumentBuilder().newDocument();
        node = doc.createElement("p");
      } catch (ParserConfigurationException ex) {
        org.apache.log4j.Logger.getLogger(Paragraph.class).error(ex.getMessage());
      }
    }
    public void addFormula(String text) {
      Node formulaNode = doc.createElement("formula");
      formulaNode.appendChild(createMathNode(doc, text));
      formulaNode.appendChild(createImageMathNode(doc, text));
      node.appendChild(formulaNode);
    }
    void addText(String text) { node.appendChild(doc.createTextNode(text)); }
    void addImage(Image image, String caption) {
      String encodedStr = ru.java5.util.PNG.getEncodedStringFromImage(image);
      Node img = doc.createElement("img");
      Node data = doc.createElement("png");
      data.appendChild(doc.createTextNode("data:image/png;charset=utf-8;base64," + encodedStr));
      img.appendChild(data);
      Node captionNode = doc.createElement("caption");
      captionNode.appendChild(doc.createTextNode(caption));
      img.appendChild(captionNode);
      node.appendChild(img);
    }
  }
}