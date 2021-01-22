package org.framework.hsven.cxfws.server.ws;

public class EntityXmlResult {
    private String root;
    private String elemnt;

    public static EntityXmlResult of(String root, String elemnt) {
        EntityXmlResult entityXmlResult = new EntityXmlResult();
        entityXmlResult.root = root;
        entityXmlResult.elemnt = elemnt;
        return entityXmlResult;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getElemnt() {
        return elemnt;
    }

    public void setElemnt(String elemnt) {
        this.elemnt = elemnt;
    }
}
