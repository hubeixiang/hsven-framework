package org.framework.hsven.dataloader.beans.dependency;

import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.dataloader.related.dependency.CallableRelatedTaskDependency;
import org.framework.hsven.dataloader.related.dependency.SimpleChildTableCallableTaskDependency;
import org.framework.hsven.dataloader.related.dependency.SimpleChildTableLazyCallableTaskDependency;
import org.framework.hsven.dataloader.related.dependency.SimpleMainTableCallableTaskDependency;

public class SheetDependencyHelper {
    public static SimpleMainTableCallableTaskDependency structCallableRelatedTaskDependency(SheetDependency sheetDependency, TableLoadDefine tableLoadDefine) {
        SimpleMainTableCallableTaskDependency simpleMainTableCallableTaskDependency = new SimpleMainTableCallableTaskDependency();
        insideStructCallableRelatedTaskDependency(simpleMainTableCallableTaskDependency, sheetDependency, tableLoadDefine);
        return simpleMainTableCallableTaskDependency;
    }

    private static void insideStructCallableRelatedTaskDependency(CallableRelatedTaskDependency callableRelatedTaskDependency, SheetDependency currentSheetDependency, TableLoadDefine tableLoadDefine) {
        EnumTableType enumTableType = callableRelatedTaskDependency.getEnumTableType();
        switch (enumTableType) {
            case Main:
                insideStructSimpleMainTableCallableTaskDependency((SimpleMainTableCallableTaskDependency) callableRelatedTaskDependency, currentSheetDependency, tableLoadDefine);
                break;
            case Child:
                insideStructSimpleChildTableCallableTaskDependency((SimpleChildTableCallableTaskDependency) callableRelatedTaskDependency, currentSheetDependency, tableLoadDefine);
                break;
            case Lazy_subtable:
                insideStructSimpleIndirectChildTableCallableTaskDependency((SimpleChildTableLazyCallableTaskDependency) callableRelatedTaskDependency, currentSheetDependency, tableLoadDefine);
                break;
            default:
                throw new RuntimeException("Error EnumTableType = " + enumTableType);
        }
    }


    private static void insideStructSimpleMainTableCallableTaskDependency(SimpleMainTableCallableTaskDependency callableRelatedTaskDependency, SheetDependency currentSheetDependency, TableLoadDefine tableLoadDefine) {

        //主表的任务只需要设置一个主表信息
        callableRelatedTaskDependency.addCurrentTable(tableLoadDefine.getSimpleMainTable());

        if (currentSheetDependency.getNext() != null && currentSheetDependency.getNext().getCurrentSheetAlias().size() > 0) {
            SimpleChildTableCallableTaskDependency next = new SimpleChildTableCallableTaskDependency();
            callableRelatedTaskDependency.setNext(next);
            insideStructCallableRelatedTaskDependency(next, currentSheetDependency.getNext(), tableLoadDefine);
        }
    }

    private static void insideStructSimpleChildTableCallableTaskDependency(SimpleChildTableCallableTaskDependency callableRelatedTaskDependency, SheetDependency currentSheetDependency, TableLoadDefine tableLoadDefine) {



        if (currentSheetDependency.getNext() != null && currentSheetDependency.getNext().getCurrentSheetAlias().size() > 0) {
            SimpleChildTableCallableTaskDependency next = new SimpleChildTableCallableTaskDependency();
            callableRelatedTaskDependency.setNext(next);
            insideStructCallableRelatedTaskDependency(next, currentSheetDependency.getNext(), tableLoadDefine);
        }
    }

    private static void insideStructSimpleIndirectChildTableCallableTaskDependency(SimpleChildTableLazyCallableTaskDependency callableRelatedTaskDependency, SheetDependency currentSheetDependency, TableLoadDefine tableLoadDefine) {

        if (currentSheetDependency.getNext() != null && currentSheetDependency.getNext().getCurrentSheetAlias().size() > 0) {
            SimpleChildTableCallableTaskDependency next = new SimpleChildTableCallableTaskDependency();
            callableRelatedTaskDependency.setNext(next);
            insideStructCallableRelatedTaskDependency(next, currentSheetDependency.getNext(), tableLoadDefine);
        }
    }
}
