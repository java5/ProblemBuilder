package ru.java5.util;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import net.sourceforge.jeuclid.MutableLayoutContext;
import net.sourceforge.jeuclid.context.LayoutContextImpl;
import net.sourceforge.jeuclid.context.Parameter;
import net.sourceforge.jeuclid.converter.Converter;
import net.sourceforge.jeuclid.swing.JMathComponent;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Math {
  public static Node createFormulaNode(Document doc, String text) {
    Node formulaNode = doc.createElement("formula");
    Node mathNode = createMathNode(doc, text);
    formulaNode.appendChild(mathNode);
    Node imageMathNode = createImageMathNode(doc, text);
    formulaNode.appendChild(imageMathNode);
    return formulaNode;
  }

  public static Node createMathNode(Document doc, String text) {
    Node mathNode = doc.createElement("math");
    CDATASection mathTextNode = doc.createCDATASection(text);
    mathNode.appendChild(mathTextNode);
    return mathNode;
  }

  public static Node createImageMathNode(Document doc, String text) {
    Node pngNode = null;

    JMathComponent mathComponent = new JMathComponent();
    mathComponent.setBackground(Color.white);
    mathComponent.setFont(new Font("miscfixed", 0, 16));
    mathComponent.setContent(text);
    try {
      MutableLayoutContext context = new LayoutContextImpl(LayoutContextImpl.getDefaultLayoutContext());
      context.setParameter(Parameter.ANTIALIAS, "true");
      // Workaround a XEP problem. FOP 1 is OK.
      context.setParameter(Parameter.MATHBACKGROUND, "#FFFFFF");
      context.setParameter(Parameter.MATHSIZE, "18");
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      Converter.getInstance().convert(mathComponent.getDocument(), baos, "image/png", context);
      baos.flush();
      baos.close();
      byte[] encoded = Base64.encodeBase64(baos.toByteArray());

      pngNode = doc.createElement("png");

      Node nameTextNode = doc.createTextNode("data:image/png;charset=utf-8;base64," + new String(encoded));
      pngNode.appendChild(nameTextNode);
    } catch (IOException ex) {}
    return pngNode;
  }

  public static boolean isValidMath(String text) {
    JMathComponent mathComponent = new JMathComponent();
    try {
      mathComponent.setContent(text);
      mathComponent.setBackground(Color.white);
      mathComponent.setFont(new Font("miscfixed", 0, 16));
      mathComponent.setContent(text);
      MutableLayoutContext context = new LayoutContextImpl(LayoutContextImpl.getDefaultLayoutContext());
      context.setParameter(Parameter.ANTIALIAS, "true");
      // Workaround a XEP problem. FOP 1 is OK.
      context.setParameter(Parameter.MATHBACKGROUND, "#FFFFFF");
      context.setParameter(Parameter.MATHSIZE, "18");
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      Converter.getInstance().convert(mathComponent.getDocument(), baos, "image/png", context);
      baos.flush();
      baos.close();
      byte[] encoded = Base64.encodeBase64(baos.toByteArray());
    } catch (Exception ex) { return false; } 
    return true;
  }
}