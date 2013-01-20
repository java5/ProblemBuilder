package ru.java5.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XML {
  public static String toString(Node node) {
    StringWriter sw = null;
    try {
      TransformerFactory transfac = TransformerFactory.newInstance();
      Transformer trans = transfac.newTransformer();
      trans.setOutputProperty(OutputKeys.METHOD, "xml");
      trans.setOutputProperty(OutputKeys.INDENT, "yes");
      trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(2));
      sw = new StringWriter();
      StreamResult result = new StreamResult(sw);
      DOMSource source = new DOMSource(node);
      trans.transform(source, result);
    } catch (TransformerException ex) {}
    return sw.toString();
  }

  public static boolean problemXsdValidation(String xml) {
    try {
      // 1. Поиск и создание экземпляра фабрики для языка XML Schema
      SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
      // 2. Компиляция схемы
      // Схема загружается в объект типа java.io.File, но вы также можете использовать 
      // классы java.net.URL и javax.xml.transform.Source
      

      //File schemaLocation = new File("problem.xsd");
      Schema schema = factory.newSchema(
              new StreamSource(ClassLoader.getSystemResourceAsStream("problem.xsd")));
      // 3. Создание валидатора для схемы
      Validator validator = schema.newValidator();
      // 4. Разбор проверяемого документа
      Source source = new StreamSource(new StringReader(xml));
      // 5. Валидация документа

      validator.validate(source);
      Logger.getLogger(XML.class).info("Sample is valid.");
      return true;
    } catch (SAXException | IOException ex) {
      Logger.getLogger(XML.class).info("Sample is not valid because " + ex.getMessage());
      return false;
    }
  }
  public static String toString(Document doc){
    StringWriter sw = null;
    try {
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer transformer = factory.newTransformer();
      transformer.setOutputProperty(OutputKeys.METHOD, "xml");
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(2));
      sw = new StringWriter();
      StreamResult result = new StreamResult(sw);
      DOMSource source = new DOMSource(doc.getDocumentElement());
      transformer.transform(source, result);
    } catch (TransformerException ex) { }
    return sw.toString();
  }  
    public static String xsltTransform(String xmlString, String xsltFileName) {
      String result = null;
      try {
        StringWriter writer = new StringWriter();
        StreamResult sr = new StreamResult(writer);
        StringReader reader = new StringReader(xmlString);
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer(
                new StreamSource(ClassLoader.getSystemResourceAsStream(xsltFileName)));
        transformer.transform(new StreamSource(reader), sr);
        result = writer.toString();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      return result;
    }    
}