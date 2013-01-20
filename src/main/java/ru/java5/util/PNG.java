package ru.java5.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.apache.commons.codec.binary.Base64;

public class PNG {
  public static String getEncodedStringFromImage(Image image) {
    byte[] encoded = null;
    try {
      ImageIcon icon = new ImageIcon(image);
      int w = icon.getIconWidth();
      int h = icon.getIconHeight();
      BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      Graphics2D graphics = img.createGraphics();
      graphics.drawImage(image, 0, 0, null);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(img, "PNG", baos);
      baos.flush();
      baos.close();
      byte[] data = baos.toByteArray();
      encoded = Base64.encodeBase64(data);
    } catch (IOException ex) {}
    return new String(encoded);
  }
}