<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" omit-xml-declaration="yes" version="4.0" encoding="UTF-8" indent="yes"/>
  <xsl:template match="root">
    <html>
      <h4>
        <xsl:text>Идентификация задачи</xsl:text>
      </h4>
      <h5>
        <xsl:text>Номер источника: </xsl:text>
        <xsl:value-of select="info/id"/>
      </h5>
      <h5>
        <xsl:text>Путь к задачке: </xsl:text>
        <xsl:value-of select="info/path"/>
      </h5>
      <xsl:apply-templates select="task|solution"/>
    </html>
  </xsl:template>
  <xsl:template match="task">
    <h4>
      <xsl:text>Условие задачи</xsl:text>
    </h4>
    <xsl:apply-templates/>
  </xsl:template>
  <xsl:template match="solution">
    <h4>
      <xsl:text>Решение задачи</xsl:text>
    </h4>
    <xsl:apply-templates/>
  </xsl:template>
  
  <xsl:template match="img/png">
    <p>
      <xsl:element name="img">
        <xsl:attribute name="src">
          <xsl:value-of select="."/>
        </xsl:attribute>
      </xsl:element>
    </p>
  </xsl:template>  
  <xsl:template match="p[not(formula) and not(img)]">
    <p>
      <xsl:value-of select="."/>
    </p>          
  </xsl:template>
  <xsl:template match="formula/png">
    <p>
      <xsl:element name="img">
        <xsl:attribute name="src">
          <xsl:value-of select="."/>
        </xsl:attribute>
      </xsl:element>
    </p>
  </xsl:template>
  <xsl:template match="p/formula/png">
    <xsl:element name="img">
      <xsl:attribute name="src">
        <xsl:value-of select="."/>
      </xsl:attribute>
    </xsl:element>
  </xsl:template>  
  <xsl:template match="formula/math"/>
</xsl:stylesheet>