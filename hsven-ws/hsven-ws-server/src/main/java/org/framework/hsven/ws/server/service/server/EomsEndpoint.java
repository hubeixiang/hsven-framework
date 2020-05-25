package org.framework.hsven.ws.server.service.server;

import com.boco.dxlte.service.proxy.eoms.service.*;
import org.framework.hsven.ws.server.autoconfigure.WServiceServerConfigure;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * 依据定义的xsd文件发布的ws服务,服务定义为Endpoint
 * 对外发布的就是ws的服务
 */
@Endpoint
public class EomsEndpoint {

    @PayloadRoot(namespace = WServiceServerConfigure.NAMESPACE_URI, localPart = "checkImportRequest")
    @ResponsePayload
    public CheckImportResponse checkImport(@RequestPayload CheckImportRequest request) {
        CheckImportResponse response = new CheckImportResponse();
        Result result = new Result();
        response.setResult(result);
        return response;
    }

    @PayloadRoot(namespace = WServiceServerConfigure.NAMESPACE_URI, localPart = "quitOrderUpdateRequest")
    @ResponsePayload
    public QuitOrderUpdateResponse quitOrderUpdate(@RequestPayload QuitOrderUpdateRequest request) {
        QuitOrderUpdateResponse response = new QuitOrderUpdateResponse();
        Result result = new Result();
        response.setResult(result);
        return response;
    }

    @PayloadRoot(namespace = WServiceServerConfigure.NAMESPACE_URI, localPart = "quitOrderRevokeRequest")
    @ResponsePayload
    public QuitOrderRevokeResponse quitOrderRevoke(@RequestPayload QuitOrderRevokeRequest request) {
        QuitOrderRevokeResponse response = new QuitOrderRevokeResponse();
        Result result = new Result();
        response.setResult(result);
        return response;
    }

    @PayloadRoot(namespace = WServiceServerConfigure.NAMESPACE_URI, localPart = "archiveRequest")
    @ResponsePayload
    public ArchiveResponse archive(@RequestPayload ArchiveRequest request) {
        ArchiveResponse response = new ArchiveResponse();
        Result result = new Result();
        response.setResult(result);
        return response;
    }

    @PayloadRoot(namespace = WServiceServerConfigure.NAMESPACE_URI, localPart = "queryDictRequest")
    @ResponsePayload
    public QueryDictResponse queryDict(@RequestPayload QueryDictRequest request) {
        QueryDictResponse response = new QueryDictResponse();
        Result result = new Result();
        response.setResult(result);
        return response;
    }

    @PayloadRoot(namespace = WServiceServerConfigure.NAMESPACE_URI, localPart = "sheetVerifyRequest")
    @ResponsePayload
    public SheetVerifyResponse sheetVerify(@RequestPayload SheetVerifyRequest request) {
        SheetVerifyResponse response = new SheetVerifyResponse();
        Result result = new Result();
        response.setResult(result);
        return response;
    }

    @PayloadRoot(namespace = WServiceServerConfigure.NAMESPACE_URI, localPart = "sheetVerifyScheduleRequest")
    @ResponsePayload
    public SheetVerifyScheduleResponse sheetVerifySchedule(@RequestPayload SheetVerifyScheduleRequest request) {
        SheetVerifyScheduleResponse response = new SheetVerifyScheduleResponse();
        Result result = new Result();
        response.setResult(result);
        return response;
    }

    @PayloadRoot(namespace = WServiceServerConfigure.NAMESPACE_URI, localPart = "artificialAcceptanceSheetRequest")
    @ResponsePayload
    public ArtificialAcceptanceSheetResponse artificialAcceptanceSheet(
            @RequestPayload ArtificialAcceptanceSheetRequest request) {
        ArtificialAcceptanceSheetResponse response = new ArtificialAcceptanceSheetResponse();
        Result result = new Result();
        response.setResult(result);
        return response;
    }

}
