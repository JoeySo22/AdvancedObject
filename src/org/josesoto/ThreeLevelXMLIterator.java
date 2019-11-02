package org.josesoto;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ThreeLevelXMLIterator{
    private AdvancedObjectXMLTraverser traverser;
    private Element rootElement;
    private Element currentSecondLevelElement;
    private Element currentThirdLevelElement;
    private CircularLinkedListIterator<Element> secondLevel;
    private CircularLinkedListIterator<Element> thirdLevel;
    private boolean hasStarted;

    public ThreeLevelXMLIterator(String fileName){
        try {
            this.traverser = new AdvancedObjectXMLTraverser(fileName);
        } catch (Exception e){
            System.out.println("Loader did not work. Exiting Program");
            e.printStackTrace();
            System.exit(-1);
        }
        //System.out.println(this.traverser.rootElement.getTagName());
        this.secondLevel = new CircularLinkedListIterator();
        this.thirdLevel = new CircularLinkedListIterator();
        this.rootElement = traverser.rootElement;
        loadSecondLevel();
        this.currentSecondLevelElement = this.secondLevel.getCurrentElement();
        this.hasStarted = true;
    }

    public void next(){
        if (this.hasStarted){
            this.hasStarted = false;
            return;
        }
        else this.currentSecondLevelElement = secondLevel.next();
        loadThirdLevel();
    }
    public void thirdLevelNext(){
        thirdLevel.next();
    }

    public String currentSecondLevelElementName(){
        return this.currentSecondLevelElement.getTagName();
    }
    public int getSecondLevelPosition(){
        if (this.hasStarted)
            return 0;
        return this.secondLevel.getPosition();
    }
    public int getThirdLevelPosition(){
        if (this.hasStarted)
            return -1;
        return this.thirdLevel.getPosition();
    }

    public void previous(){
        if (this.hasStarted){
            this.hasStarted = false;
            return;
        }
        else this.currentSecondLevelElement = secondLevel.previous();
        loadThirdLevel();
    }

    public int getSecondLevelLength(){
        return this.secondLevel.getSize();
    }
    public int getThirdLevelLength(){
        if (this.hasStarted)
            return 0;
        return this.thirdLevel.getSize();
    }

    private void loadSecondLevel(){
        NodeList list = this.rootElement.getChildNodes();
        for (int i = 0; i < list.getLength(); i++){
            Node n = list.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE){
                Element e = (Element) n;
                secondLevel.add(e);
            }
        }
        this.currentSecondLevelElement = secondLevel.getCurrentElement();
        loadThirdLevel();
    }

    private void loadThirdLevel(){
        this.thirdLevel = new CircularLinkedListIterator<>();
        NodeList list = this.currentSecondLevelElement.getChildNodes();
        for (int i = 0; i < list.getLength(); i++){
            Node n = list.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE){
                Element e = (Element) n;
                thirdLevel.add(e);
            }
        }
    }

    public String getTextContextOf(String tag){
        for (int i = 0; i < thirdLevel.getSize(); i++){
            if (thirdLevel.get(i).getTagName().equalsIgnoreCase(tag))
                return thirdLevel.get(i).getTextContent();
        }
        return "";
    }
}
