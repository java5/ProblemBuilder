<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
  <xsl:output method="html" omit-xml-declaration="yes" version="4.0" encoding="UTF-8" indent="yes"/>
  <xsl:template match="/">
    <html>
      <xsl:apply-templates/>
    </html>
  </xsl:template>
  <xsl:template match="p[not(formula) and not(img)]">
    <xsl:element name="h4">
      <xsl:value-of select="."/>
    </xsl:element>
  </xsl:template>
  <xsl:template match="p/formula/png">
    <xsl:element name="img">
      <xsl:attribute name="src">
        <xsl:value-of select="."/>
      </xsl:attribute>
    </xsl:element>
  </xsl:template>  
  <xsl:template match="p/formula/math"/>
  <xsl:template match="p/formula/svg"/>
  <xsl:template match="p/img/png">
    <xsl:element name="img">
      <xsl:attribute name="src">
        <xsl:value-of select="."/>
      </xsl:attribute>
    </xsl:element>
  </xsl:template>  
  <xsl:template match="p/img/caption">
    <xsl:element name="h4">
      <xsl:value-of select="."/>
    </xsl:element>
  </xsl:template>    
</xsl:stylesheet>