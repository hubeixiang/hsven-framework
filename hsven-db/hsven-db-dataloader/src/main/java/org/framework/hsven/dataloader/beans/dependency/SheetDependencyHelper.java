package org.framework.hsven.dataloader.beans.dependency;

import org.framework.hsven.dataloader.beans.loader.DefineRelatedFields;
import org.framework.hsven.dataloader.beans.related.ChildTables;
import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.dataloader.related.dependency.CallableRelatedTaskDependency;
import org.framework.hsven.dataloader.related.dependency.SimpleChildTableCallableTaskDependency;
import org.framework.hsven.dataloader.related.dependency.SimpleChildTableGroup;
import org.framework.hsven.dataloader.related.dependency.SimpleChildTableLazyCallableTaskDependency;
import org.framework.hsven.dataloader.related.dependency.SimpleLazyChildTableGroup;
import org.framework.hsven.dataloader.related.dependency.SimpleMainTableCallableTaskDependency;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
                insideStructSimpleLazyChildTableCallableTaskDependency((SimpleChildTableLazyCallableTaskDependency) callableRelatedTaskDependency, currentSheetDependency, tableLoadDefine);
                break;
            default:
                throw new RuntimeException("Error EnumTableType = " + enumTableType);
        }
    }


    private static void insideStructSimpleMainTableCallableTaskDependency(SimpleMainTableCallableTaskDependency callableRelatedTaskDependency, SheetDependency currentSheetDependency, TableLoadDefine tableLoadDefine) {

        //主表的任务只需要设置一个主表信息
        callableRelatedTaskDependency.addCurrentTable(tableLoadDefine.getSimpleMainTable());
        //找出需要预先缓存的字表
        if (tableLoadDefine.hasSimpleChildTable()) {
            Iterator<Map.Entry<String, SimpleChildTable>> it = tableLoadDefine.getChildTables().entryChildTables();
            while (it != null && it.hasNext()) {
                SimpleChildTable simpleChildTable = it.next().getValue();
                if (simpleChildTable.isConfigCache()) {
                    callableRelatedTaskDependency.getPrefetchChildTableList().add(simpleChildTable);
                }
            }
        }

        if (currentSheetDependency.getNext() != null && currentSheetDependency.getNext().getCurrentSheetAlias().size() > 0) {
            SimpleChildTableCallableTaskDependency next = new SimpleChildTableCallableTaskDependency();
            callableRelatedTaskDependency.setNext(next);
            insideStructCallableRelatedTaskDependency(next, currentSheetDependency.getNext(), tableLoadDefine);
        }
    }

    private static void insideStructSimpleChildTableCallableTaskDependency(SimpleChildTableCallableTaskDependency callableRelatedTaskDependency, SheetDependency currentSheetDependency, TableLoadDefine tableLoadDefine) {

        Set<String> sheetAlias = currentSheetDependency.getCurrentSheetAlias();
        if (!CollectionUtils.isEmpty(sheetAlias)) {
            callableRelatedTaskDependency.getCurrentTableAlias().addAll(sheetAlias);
            ChildTables childTables = tableLoadDefine.getChildTables();
            if (childTables.hasSimpleChildTable()) {
                for (String tableAlias : sheetAlias) {
                    SimpleChildTable simpleChildTable = childTables.getChildTable(tableAlias);
                    if (simpleChildTable == null) {
                        continue;
                    }
                    DefineRelatedFields defineRelatedFields = simpleChildTable.getDefineRelatedFields();
                    SimpleChildTableGroup simpleChildTableGroup = callableRelatedTaskDependency.getCurrentTable(defineRelatedFields.getRelatedFieldsKeys());
                    if (simpleChildTableGroup == null) {
                        simpleChildTableGroup = new SimpleChildTableGroup(defineRelatedFields);
                        callableRelatedTaskDependency.addCurrentTable(simpleChildTableGroup);
                    }
                    simpleChildTableGroup.getSimpleChildTableList().add(simpleChildTable);
                }
            }
        }
        if (currentSheetDependency.getNext() != null && currentSheetDependency.getNext().getCurrentSheetAlias().size() > 0) {
            SimpleChildTableLazyCallableTaskDependency next = new SimpleChildTableLazyCallableTaskDependency();
            callableRelatedTaskDependency.setNext(next);
            insideStructCallableRelatedTaskDependency(next, currentSheetDependency.getNext(), tableLoadDefine);
        }
    }

    private static void insideStructSimpleLazyChildTableCallableTaskDependency(SimpleChildTableLazyCallableTaskDependency callableRelatedTaskDependency, SheetDependency currentSheetDependency, TableLoadDefine tableLoadDefine) {

        Set<String> sheetAlias = currentSheetDependency.getCurrentSheetAlias();
        if (!CollectionUtils.isEmpty(sheetAlias)) {
            callableRelatedTaskDependency.getCurrentTableAlias().addAll(sheetAlias);
            ChildTables childTables = tableLoadDefine.getChildTables();
            if (childTables.hasSimpleChildTable()) {
                for (String tableAlias : sheetAlias) {
                    SimpleChildTable simpleChildTable = childTables.getChildTable(tableAlias);
                    if (simpleChildTable == null) {
                        continue;
                    }
                    DefineRelatedFields defineRelatedFields = simpleChildTable.getDefineRelatedFields();
                    SimpleLazyChildTableGroup simpleLazyChildTableGroup = callableRelatedTaskDependency.getCurrentTable(defineRelatedFields.getRelatedFieldsKeys());
                    if (simpleLazyChildTableGroup == null) {
                        simpleLazyChildTableGroup = new SimpleLazyChildTableGroup(defineRelatedFields);
                        callableRelatedTaskDependency.addCurrentTable(simpleLazyChildTableGroup);
                    }
                    simpleLazyChildTableGroup.getSimpleChildTableList().add(simpleChildTable);
                }
            }
        }
        if (currentSheetDependency.getNext() != null && currentSheetDependency.getNext().getCurrentSheetAlias().size() > 0) {
            SimpleChildTableLazyCallableTaskDependency next = new SimpleChildTableLazyCallableTaskDependency();
            callableRelatedTaskDependency.setNext(next);
            insideStructCallableRelatedTaskDependency(next, currentSheetDependency.getNext(), tableLoadDefine);
        }
    }
}
