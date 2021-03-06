package ru.java5.data;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class Handler extends URLStreamHandler {
  @Override
  protected URLConnection openConnection(URL u) throws IOException {
    return new DataConnection(u);
  }

  public static void install() {
    String pkgName = Handler.class.getPackage().getName();
    String pkg = pkgName.substring(0, pkgName.lastIndexOf('.'));

    String protocolHandlers = System.getProperty("java.protocol.handler.pkgs", "");
    if (!protocolHandlers.contains(pkg)) {
      if (!protocolHandlers.isEmpty()) protocolHandlers += "|";
      protocolHandlers += pkg;
      System.setProperty("java.protocol.handler.pkgs", protocolHandlers);
    }
  }
}