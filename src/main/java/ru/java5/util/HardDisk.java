package ru.java5.util;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.apache.commons.io.FileUtils;
/**
 *
 * @author Зайнуллин Радик
 * @since 2012.08.18
 */
public class HardDisk {
  public static void saveProblem(File file, JFrame mainFrame, String str){
    try {
      if (file == null) {
        JFileChooser c = new JFileChooser();
        int rVal = c.showSaveDialog(mainFrame);
        if (rVal == JFileChooser.APPROVE_OPTION) {
          String fileName = c.getSelectedFile().getName();
          if (!fileName.endsWith(".xml")) fileName = new String(fileName + ".xml");
          file = new File(c.getCurrentDirectory(), fileName);
        }
      }
      FileUtils.writeStringToFile(file, str, "UTF-8");
    } catch (IOException ex) { org.apache.log4j.Logger.getLogger(HardDisk.class).error(ex); }
  }
}
