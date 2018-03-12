/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.painelnfce2017.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author root
 */
public class Utils {

    private static DocumentBuilder xmlDocumentBuilder;

    static {
        Utils.init();
    }
    
    
    private static void init() {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            Utils.xmlDocumentBuilder = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Document parseXml(String xml) {
        Document doc = null;
        InputSource is = new InputSource(new ByteArrayInputStream(xml.getBytes()));
        try {
            doc = xmlDocumentBuilder.parse(is);
            
        } catch (SAXException ex) {
            //Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
               
        return doc;
    }
    
    
    private static String pad(String str, int len, char chr, int side) {
        if ( str == null ) {
            return null;
        }
        
        String fmt;

        if (str.length() > len) {
            fmt = str.substring(0, len);
        } else {
            fmt = str;

            while (fmt.length() < len) {
                if ( side == 0 ) {
                  fmt = chr + fmt;
                } else {
                    fmt = fmt + chr;
                }
            }
        }

        return fmt;
    }
    
    public static String lpad(String str, int len, char chr) {
        return pad(str, len, chr, 0);
    }
    
    public static String rpad(String str, int len, char chr) {
        return pad(str, len, chr, 1);
    }
}
