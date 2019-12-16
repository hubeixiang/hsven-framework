package org.framework.hsven.file.location;


/**
 * @author sven
 * @date 2019/10/29 10:20
 */
public class TestMain {
    public static void main(String[] args) {
        String fileName = "uuid.xls";
        FileLocation fileLocation = new FileLocation();
        FileNameDescribe fileNameDescribe = FileLocationManager.getInstance().parserFileNameDescribe(fileName);
        fileLocation.setFileExtension(fileNameDescribe.getFileExtension());
        fileLocation.setFileUuid(fileNameDescribe.getFileName());
        fileLocation.setService("ser");


        System.out.println(fileNameDescribe);

        fileName = "E20.1.8.xls";
        fileNameDescribe = FileLocationManager.getInstance().parserFileNameDescribe(fileName);
        System.out.println(fileNameDescribe.getFileName().indexOf("."));
        System.out.println(fileNameDescribe.getFileExtension().getExtension().indexOf("."));
        System.out.println(fileNameDescribe);
    }
}
