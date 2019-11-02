package org.josesoto;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class AdvancedObjectXMLTraverser {
    protected String fileName;
    protected Document document;
    protected Element rootElement;


    public AdvancedObjectXMLTraverser(String fileName) throws Exception{
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.document = builder.parse(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Document Building Failed.");
        }
        this.fileName = fileName;
        this.rootElement = document.getDocumentElement();
    }

    public String getFileName() {
        return this.fileName;
    }
    public String getRootElementTagName(){
        return this.rootElement.getTagName();
    }
}
