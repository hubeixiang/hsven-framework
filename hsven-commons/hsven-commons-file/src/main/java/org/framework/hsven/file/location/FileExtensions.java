package org.framework.hsven.file.location;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sven
 * @date 2019/10/29 10:47
 * <p>
 * 系统定义的可识别的文件后缀信息
 */
public class FileExtensions {
    private static Map<String, FileExtension> fileExtensionMap = new HashMap<>();
    private static FileExtensions instance = null;

    private FileExtensions() {
        add(createFileExtension(".txt", "TEXT"));
        add(createFileExtension(".log", "LOG"));
        add(createFileExtension(".xml", "XML"));
        add(createFileExtension(".csv", "CSV"));
        add(createFileExtension(".xls", "Excel03"));
        add(createFileExtension(".xlsx", "Excel07"));
        add(createFileExtension(".doc", "Word03"));
        add(createFileExtension(".docx", "Word07"));
        add(createFileExtension(".zip", "ZIP"));
    }

    private FileExtension createFileExtension(String extension, String extensionDisplay) {
        FileExtension fileExtension = new FileExtension();
        fileExtension.setExtension(extension);
        fileExtension.setExtensionDisplay(extensionDisplay);
        return fileExtension;
    }

    private void add(FileExtension fileExtension) {
        fileExtensionMap.put(fileExtension.getExtension(), fileExtension);
    }

    public static FileExtensions getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

    private static synchronized void createInstance() {
        if (instance != null) {
            return;
        }
        instance = new FileExtensions();
    }

    public FileExtension parserFileExtension(String extension, String extensionDisplay) {
        if (fileExtensionMap.containsKey(extension)) {
            return createFileExtension(extension, fileExtensionMap.get(extension).getExtensionDisplay());
        } else {
            if (extension.startsWith(".")) {
                return createFileExtension(extension, StringUtils.isEmpty(extensionDisplay) ? "Unknow" : extensionDisplay);
            } else {
                throw new RuntimeException("file extension must starts with char '.'");
            }
        }
    }

}
