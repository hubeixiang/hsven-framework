package org.framework.hsven.file.location;


import org.junit.Test;

import java.io.File;

/**
 * @date 2019/10/29 10:20
 */
public class TestFileLocation {
    @Test
    public void testFileLocal() {
        String fileName = "uuid.xls";
        FileLocation fileLocation = new FileLocation();
        FileNameDescribe fileNameDescribe = FileLocationManager.getInstance().parserFileNameDescribe(fileName);
        fileLocation.setFileExtension(fileNameDescribe.getFileExtension());
        fileLocation.setFileUuid(fileNameDescribe.getFileName());
        fileLocation.setServiceUrl("E:\\");

        System.out.println(fileNameDescribe);

        fileName = "E20.1.8.xls";
        fileNameDescribe = FileLocationManager.getInstance().parserFileNameDescribe(fileName);
        System.out.println(fileNameDescribe.getFileName().indexOf("."));
        System.out.println(fileNameDescribe.getFileExtension().getExtension().indexOf("."));
        System.out.println(fileNameDescribe);

        System.out.println(FileLocationManager.getInstance().fileUrl(fileLocation));
        File file = new File(FileLocationManager.getInstance().fileUrl(fileLocation));
        System.out.println(FileLocationManager.getInstance().parserFileLocalion(file));
    }
}
