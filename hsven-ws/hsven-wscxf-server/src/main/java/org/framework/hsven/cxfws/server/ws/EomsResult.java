package org.framework.hsven.cxfws.server.ws;

public class EomsResult {
    private String sheetNo;
    private String errList;

    public static EomsResult of(String sheetNo, String errList) {
        EomsResult eomsResult = new EomsResult();
        eomsResult.sheetNo = sheetNo;
        eomsResult.errList = errList;
        return eomsResult;
    }

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getErrList() {
        return errList;
    }

    public void setErrList(String errList) {
        this.errList = errList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("sheetNo=").append(sheetNo == null ? "" : sheetNo).append(";")
                .append("errList=").append(errList == null ? "" : errList);
        return sb.toString();
    }
}
