package org.framework.hsven.dataloader.beans.dependency;

import java.util.HashSet;
import java.util.Set;

public class SheetDependency {
    private SheetDependency previous = null;
    private SheetDependency next = null;
    //当前层级的信息
    private CurrentInfo currentInfo = null;

    public SheetDependency getPrevious() {
        return previous;
    }

    public SheetDependency getNext() {
        return next;
    }

    public CurrentInfo getCurrentInfo() {
        return currentInfo;
    }

    public void addCurrentInfo(CurrentInfo currentInfo) {
        this.currentInfo = currentInfo;
    }

    /**
     * 获取当前对象的字段信息
     *
     * @return
     */
    public Set<String> getCurrentFieldAlias() {
        HashSet<String> set = new HashSet<String>();
        if (currentInfo != null) {
            set.addAll(currentInfo.getSheetFieldMap().keySet());
        }
        return set;
    }

    /**
     * 获取当前对象,以及当前对象上级的字段信息
     *
     * @return
     */
    public Set<String> getAllPreCurrentFieldAlias() {
        Set<String> fieldAlias = getCurrentFieldAlias();
        if (previous != null) {
            Set<String> preFieldAlias = previous.getAllPreCurrentFieldAlias();
            fieldAlias.addAll(preFieldAlias);
        }
        return fieldAlias;
    }

    /**
     * 获取当前对象,以及当前对象下级的字段信息
     *
     * @return
     */
    public Set<String> getAllSuffixCurrentFieldAlias() {
        Set<String> fieldAlias = getCurrentFieldAlias();
        if (next != null) {
            Set<String> preFieldAlias = next.getAllSuffixCurrentFieldAlias();
            fieldAlias.addAll(preFieldAlias);
        }
        return fieldAlias;
    }

    /**
     * 获取所有对象的字段信息
     *
     * @return
     */
    public Set<String> getAllCurrentFieldAlias() {
        Set<String> fieldAlias = getCurrentFieldAlias();
        Set<String> prefieldAlias = getAllPreCurrentFieldAlias();
        fieldAlias.addAll(prefieldAlias);
        Set<String> suffixfieldAlias = getAllSuffixCurrentFieldAlias();
        fieldAlias.addAll(suffixfieldAlias);
        return fieldAlias;
    }

    /**
     * 获取当前对象的左关联表信息
     *
     * @return
     */
    public Set<String> getCurrentSheetAlias() {
        HashSet<String> set = new HashSet<String>();
        if (currentInfo != null) {
            set.addAll(currentInfo.getLeftInfoMap().keySet());
        }
        return set;
    }

    /**
     * 获取当前对象,以及当前对象上级的左关联表信息
     *
     * @return
     */
    public Set<String> getAllPreCurrentSheetAlias() {
        Set<String> sheetAlias = getCurrentSheetAlias();
        if (previous != null) {
            Set<String> preSheetAlias = previous.getAllPreCurrentSheetAlias();
            sheetAlias.addAll(preSheetAlias);
        }
        return sheetAlias;
    }

    /**
     * 获取当前对象,以及当前对象下级的左关联表信息
     *
     * @return
     */
    public Set<String> getAllSuffixCurrentSheetAlias() {
        Set<String> sheetAlias = getCurrentSheetAlias();
        if (next != null) {
            Set<String> preSheetAlias = next.getAllSuffixCurrentSheetAlias();
            sheetAlias.addAll(preSheetAlias);
        }
        return sheetAlias;
    }

    /**
     * 获取所有对象的左关联表信息
     *
     * @return
     */
    public Set<String> getAllCurrentSheetAlias() {
        Set<String> sheetAlias = getCurrentSheetAlias();
        Set<String> preSheetAlais = getAllPreCurrentSheetAlias();
        sheetAlias.addAll(preSheetAlais);
        Set<String> suffixSheetAlais = getAllSuffixCurrentSheetAlias();
        sheetAlias.addAll(suffixSheetAlais);
        return sheetAlias;
    }

    public void addNextCurrentInfo(CurrentInfo nextCurrentInfo) {
        if (this.currentInfo == null) {
            throw new RuntimeException("必须先设置当前信息,才能设置下一级的信息");
        }
        next = new SheetDependency();
        next.previous = this;
        next.currentInfo = nextCurrentInfo;
        next.next = null;
    }
}
