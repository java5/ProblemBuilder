package ru.java5.util;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 *
 * @author Зайнуллин Радик
 * @since 2012.08.18
 */
public class Doc {
  public static Document stringToDocument(String src){
    Document doc = null;
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      InputSource is = new InputSource(new StringReader(src));
      doc = builder.parse( is );
    }catch( Exception ex ) { ex.printStackTrace(); }
    return doc;
  }
  
  public static Document copyDoc(Document doc) {
    Document resultDoc = null;
    try {
      TransformerFactory tfactory = TransformerFactory.newInstance();
      Transformer tx = tfactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      DOMResult result = new DOMResult();
      tx.transform(source, result);
      resultDoc = (Document) result.getNode();
    } catch (TransformerException ex) {
      org.apache.log4j.Logger.getLogger(Doc.class).error("Doc " + ex.getMessage());
    }
    return resultDoc;
  }

  public static Document getEmptyProblemDoc() {
    Document doc = null;
    // инициализируется "пустая" задачка
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true); // never forget this!
      doc = factory.newDocumentBuilder().newDocument();
      Element root = doc.createElement("root");
      root.setAttribute("xmlns", "http://www.java5.ru/problem");
      Element info = doc.createElement("info");
      info.appendChild(doc.createElement("id"));
      info.appendChild(doc.createElement("path"));
      root.appendChild(info);
      root.appendChild(doc.createElement("task"));
      root.appendChild(doc.createElement("solution"));
      doc.appendChild(root);
    } catch (ParserConfigurationException ex) {
      org.apache.log4j.Logger.getLogger(Doc.class).error(ex.getMessage());
    }
    return doc;
  }
}