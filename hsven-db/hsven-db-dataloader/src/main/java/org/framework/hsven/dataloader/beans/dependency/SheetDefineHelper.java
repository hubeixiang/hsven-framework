package org.framework.hsven.dataloader.beans.dependency;


import org.framework.hsven.dataloader.beans.related.SimpleChildTable;
import org.framework.hsven.dataloader.beans.related.SimpleMainTable;
import org.framework.hsven.dataloader.beans.related.TableField;
import org.framework.hsven.dataloader.beans.related.TableFieldSet;
import org.framework.hsven.dataloader.beans.related.TableLoadDefine;
import org.framework.hsven.dataloader.beans.related.TableRelatedField;
import org.framework.hsven.dataloader.valid.related.TableDefineUtil;
import org.framework.hsven.datasource.enums.DataSourceType;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SheetDefineHelper {

    /**
     * 解析整个模型依赖关系,生成统一查询sql
     *
     * @param sheetDefine     模型定义
     * @param sheetDependency 模型分析后的依赖关系
     * @return
     */
    public static StructSql structRecursionSql(SheetDefine sheetDefine, SheetDependency sheetDependency) {
        StructSql wholeSql = new StructSql();
        //在拼接的sql中已经出现了的字段,后面不用再次拼接
        Set<String> alreadyAppearedFieldName = new HashSet<String>();
        SheetDefineHelper.structRecursionSql(sheetDefine, sheetDependency, 1, wholeSql, alreadyAppearedFieldName);
        return wholeSql;
    }

    public static SheetDefine structSheetDefine(TableLoadDefine tableLoadDefine) {
        SimpleMainTable simpleMainTable = tableLoadDefine.getSimpleMainTable();
        String mainSheetAlais = simpleMainTable.getTableAlias();
        SheetDefine sheetDefine = new SheetDefine(mainSheetAlais, simpleMainTable.getTableDefine().getTableName());
        TableFieldSet tableFieldSet = simpleMainTable.getTableFieldSet();

        //主表
        parserSheetField(sheetDefine, tableFieldSet.getFieldSet());

        //左关联表
        Iterator<Map.Entry<String, SimpleChildTable>> it = tableLoadDefine.getChildTables().entryChildTables();
        while (it != null && it.hasNext()) {
            Map.Entry<String, SimpleChildTable> entry = it.next();

            SimpleChildTable simpleChildTable = entry.getValue();
            if (simpleChildTable == null) {
                continue;
            }
            //分析字段
            String leftTableAlias = simpleChildTable.getTableAlias();
            parserSheetField(sheetDefine, simpleChildTable.getTableFieldSet().getFieldSet());

            //分析表
            LeftInfo leftInfo = new LeftInfo(leftTableAlias, simpleChildTable.getTableDefine().getTableName());
            Collection<TableRelatedField> tableRelatedFields = simpleChildTable.getTableRelatedFieldSet().getTableRelatedFieldList();
            if (tableRelatedFields == null) {

            } else {
                for (TableRelatedField tableRelatedField : tableRelatedFields) {
                    LeftRelatedInfo leftRelatedInfo = new LeftRelatedInfo();
                    leftRelatedInfo.setChildSheetField(tableRelatedField.getChildTableField());
                    leftRelatedInfo.setMainSheetField(tableRelatedField.getMainTableField());
                    leftInfo.addLeftRelatedInfoList(leftRelatedInfo);
                }
            }
            sheetDefine.addLeftInfo(leftInfo);
        }
        return sheetDefine;
    }

    /**
     * 验证配置是否正确
     *
     * @param sheetDefine
     * @return
     */
    public static StringBuffer validSheetDefine(SheetDefine sheetDefine) {
        StringBuffer errorBuffer = new StringBuffer();
        if (!sheetDefine.haveConfig()) {
            errorBuffer.append("未配置字段或者主表别名.");
            return errorBuffer;
        }

        StringBuffer errorLeftSheetAlias = new StringBuffer();
        for (String leftSheetAlais : sheetDefine.getLeftInfo().keySet()) {
            Map<String, SheetField> tmpSheetInfo = sheetDefine.getSheetFieldBySheetAlias(leftSheetAlais);
            if (tmpSheetInfo.size() == 0) {
                //对应的左关联表并没有需要生成的字段,异常
                if (errorLeftSheetAlias.length() == 0) {
                    errorLeftSheetAlias.append(leftSheetAlais);
                } else {
                    errorLeftSheetAlias.append(",").append(leftSheetAlais);
                }
            }
        }
        if (errorLeftSheetAlias.length() > 0) {
            errorBuffer.append(String.format("左关联表[%s]冗余关联可以在模型中去掉其配置", errorLeftSheetAlias.toString()));
        }

        SheetDependency sheetDependency = parserDependency(sheetDefine);

        //再次找出未能关联的左关联表与做关联字段
        Set<String> allField = sheetDependency.getAllCurrentFieldAlias();
        StringBuffer fieldSb = new StringBuffer();
        for (String leftJoinSheet : sheetDefine.getSheetField().keySet()) {
            if (!allField.contains(leftJoinSheet)) {
                if (fieldSb.length() == 0) {
                    fieldSb.append(leftJoinSheet);
                } else {
                    fieldSb.append(",").append(leftJoinSheet);
                }
            }
        }
        Set<String> allLeftSheet = sheetDependency.getAllCurrentSheetAlias();
        StringBuffer sheetSb = new StringBuffer();
        for (String leftJoinSheet : sheetDefine.getLeftInfo().keySet()) {
            if (!allLeftSheet.contains(leftJoinSheet)) {
                if (sheetSb.length() == 0) {
                    sheetSb.append(leftJoinSheet);
                } else {
                    sheetSb.append(",").append(leftJoinSheet);
                }
            }
        }
        if (sheetSb.length() > 0) {
            errorBuffer.append(String.format("左关联表[%s]未能配置正确左关联关系.", sheetSb.toString()));
        }
        if (fieldSb.length() > 0) {
            errorBuffer.append(String.format("字段[%s]未能正确关联上左关联表.", fieldSb.toString()));
        }
        return errorBuffer;
    }

    /**
     * 分析所有表中字段左关联的来源
     *
     * @param sheetDefine
     */
    public static SheetDependency parserDependency(SheetDefine sheetDefine) {
        SheetDependency dependency = new SheetDependency();
        //1. 获取主表
        String mainSheetAlias = sheetDefine.getMainSheetAlias();
        Map<String, SheetField> mainSheetInfo = sheetDefine.getSheetFieldBySheetAlias(mainSheetAlias);
        CurrentInfo mainSheetCurrentInfo = new CurrentInfo(EnumTableType.Main);
        mainSheetCurrentInfo.setSheetFieldMap(mainSheetInfo);
        dependency.addCurrentInfo(mainSheetCurrentInfo);
        parserCurrentDependency(dependency, sheetDefine, EnumTableType.Child);
        return dependency;
    }

    private static void parserCurrentDependency(SheetDependency previous, SheetDefine sheetDefine, EnumTableType tableType) {
        if (previous == null || previous.getCurrentInfo() == null) {
            return;
        }
        //开始分析左关联表
        if (!sheetDefine.haveLeftInfo()) {
            //没有左关联信息也表示分析完毕
            return;
        }

        //当前已经分析过的左关联表
        Set<String> dependencySheetAlias = previous.getAllPreCurrentSheetAlias();
        if (dependencySheetAlias.size() >= sheetDefine.getLeftInfo().size()) {
            //所有的左关联表都已经分析完毕
            return;
        }

        Set<String> dependencyFieldAlias = previous.getAllPreCurrentFieldAlias();
        if (dependencyFieldAlias.size() >= sheetDefine.getSheetField().size()) {
            //表明所有的字段都已经分析完成了
            return;
        }

        Map<String, LeftInfo> currentLeftInfo = new HashMap<String, LeftInfo>();
        for (String leftSheetAlais : sheetDefine.getLeftInfo().keySet()) {
            if (dependencySheetAlias.contains(leftSheetAlais)) {
                continue;
            }
            //分析还未分析的左关联表
            LeftInfo leftInfo = sheetDefine.getLeftInfo().get(leftSheetAlais);
            //分析其作为关联条件的字段是否在已经依赖的字段列表中
            boolean isOk = true;
            for (LeftRelatedInfo leftRelatedInfo : leftInfo.getLeftRelatedInfoList()) {
                String mainSheetField = leftRelatedInfo.getMainSheetField();
                if (!dependencyFieldAlias.contains(mainSheetField)) {
                    //关联条件字段有未出现在已经依赖正常的字段列表中
                    isOk = false;
                    break;
                }
            }
            if (isOk) {
                currentLeftInfo.put(leftInfo.getSheetAlias(), leftInfo);
            }
        }
        if (currentLeftInfo.size() == 0) {
            //表明当前没有分析到可以用于左关联的表,异常
            return;
        }
        Map<String, SheetField> leftSheetInfo = new HashMap<String, SheetField>();
        for (String leftSheetAlais : currentLeftInfo.keySet()) {
            Map<String, SheetField> tmpSheetInfo = sheetDefine.getSheetFieldBySheetAlias(leftSheetAlais);
            leftSheetInfo.putAll(tmpSheetInfo);
        }

        //设置previous的下一级左关联表信息
        CurrentInfo currentInfo = new CurrentInfo(tableType);
        currentInfo.setSheetFieldMap(leftSheetInfo);
        currentInfo.setLeftInfoMap(currentLeftInfo);
        previous.addNextCurrentInfo(currentInfo);
        SheetDependency currentSheetDependency = previous.getNext();
        parserCurrentDependency(currentSheetDependency, sheetDefine, EnumTableType.Lazy_subtable);
    }


    private static void parserSheetField(SheetDefine sheetDefine, Collection<TableField> tableFields) {
        if (tableFields == null) {
            return;
        }
        for (TableField tableField : tableFields) {
            SheetField sheetField = new SheetField();
            sheetField.setFieldAlias(tableField.getFieldAlias());
            SheetFieldSource sheetFieldSource = new SheetFieldSource();
            sheetFieldSource.setSheetTableAlias(tableField.getTableAlias());
            sheetFieldSource.setSheetFieldName(tableField.getTableFieldName());
            sheetFieldSource.setSecondSheetFieldName(tableField.getSecondTableFieldName());
            sheetField.addSheetFieldSource(sheetFieldSource);
            sheetDefine.addSheetField(sheetField);
        }
    }

    private static void structRecursionSql(SheetDefine sheetDefine, SheetDependency sheetDependency, int recursionDepth, StructSql wholeSql, Set<String> alreadyAppearedFieldName) {
        if (recursionDepth <= 0) {
            throw new RuntimeException("SheetDefineHelper.structRecursionSql param recursionDepth must >= 1");
        }
        String thePreviousTableAlias = "vtable_" + (recursionDepth - 1);
        String theCurrentTableAlias = "vtable_" + (recursionDepth);
        if (sheetDependency.getPrevious() == null) {
            //表明是第一个主表的查询sql拼接
            String mainSheetAlias = sheetDefine.getMainSheetAlias();
            //找出主表要查询的字段
            StringBuffer mainSelectFields = new StringBuffer();
            Map<String, SheetField> mainSheetFields = sheetDefine.getMainSheetFieldBySheetAlias(mainSheetAlias);
            for (Map.Entry<String, SheetField> entry : mainSheetFields.entrySet()) {
                SheetField sheetField = entry.getValue();
                SheetFieldSource sheetFieldSource = sheetField.getSheetFieldSource(mainSheetAlias);
                if (sheetFieldSource != null) {
                    if (mainSelectFields.length() == 0) {
                        mainSelectFields.append(String.format("%s.%s as %s", mainSheetAlias, sheetFieldSource.getSheetFieldName(), sheetField.getFieldAlias()));
                    } else {
                        mainSelectFields.append(",").append(String.format("%s.%s as %s", mainSheetAlias, sheetFieldSource.getSheetFieldName(), sheetField.getFieldAlias()));
                    }
                }
            }
            String mainSql = String.format(" select %s from %s ", mainSelectFields.toString(), TableDefineUtil.structQueryTable(null, sheetDefine.getMainSheetContext(), mainSheetAlias));
            wholeSql.setWholeSql(mainSql);
            CurrentInfo currentInfo = sheetDependency.getCurrentInfo();
            for (Map.Entry<String, SheetField> entry : currentInfo.getSheetFieldMap().entrySet()) {
                String fieldName = entry.getKey();
                alreadyAppearedFieldName.add(fieldName);
            }
        } else {
            //左关联附表的拼接
            Set<String> currentSheetAlias = sheetDependency.getCurrentSheetAlias();
            if (currentSheetAlias.size() > 0) {
                StringBuffer columnsb = new StringBuffer();
                CurrentInfo currentInfo = sheetDependency.getCurrentInfo();
                Set<String> alreadyAppearedTableAlais = new HashSet<String>();
                for (Map.Entry<String, SheetField> entry : currentInfo.getSheetFieldMap().entrySet()) {
                    String fieldName = entry.getKey();
                    if (alreadyAppearedFieldName.contains(fieldName)) {
                        //当前的字段已经被查询出来过了,不用再次查询
                        continue;
                    }
                    alreadyAppearedFieldName.add(fieldName);
                    SheetField sheetField = entry.getValue();
                    for (String leftSheetAlias : currentSheetAlias) {
                        SheetFieldSource sheetFieldSource = sheetField.getSheetFieldSource(leftSheetAlias);
                        if (sheetFieldSource != null) {
                            alreadyAppearedTableAlais.add(sheetFieldSource.getSheetTableAlias());
                            String column = String.format("%s.%s as %s", sheetFieldSource.getSheetTableAlias(), sheetFieldSource.getSheetFieldName(), fieldName);
                            if (columnsb.length() == 0) {
                                columnsb.append(column);
                            } else {
                                columnsb.append(",").append(column);
                            }
                        }
                    }
                }
                StringBuffer leftsb = new StringBuffer();
                for (Map.Entry<String, LeftInfo> entry : currentInfo.getLeftInfoMap().entrySet()) {
                    String leftSheetAlias = entry.getKey();
                    if (!alreadyAppearedTableAlais.contains(leftSheetAlias)) {
                        //前面未拼接的字段的表别名,此处不用拼接
                        continue;
                    }
                    LeftInfo leftInfo = entry.getValue();
                    StringBuffer oneLeftsb = new StringBuffer();
                    for (LeftRelatedInfo leftRelatedInfo : leftInfo.getLeftRelatedInfoList()) {
                        String lefton = String.format("%s.%s = %s.%s", leftSheetAlias, leftRelatedInfo.getChildSheetField(), thePreviousTableAlias, leftRelatedInfo.getMainSheetField());
                        if (oneLeftsb.length() == 0) {
                            oneLeftsb.append(lefton);
                        } else {
                            oneLeftsb.append(" and ").append(lefton);
                        }
                    }
                    if (oneLeftsb.length() > 0) {
                        String left = String.format("left join %s %s on %s", getTableStr(leftInfo.getSheetAliasContext(), null), leftSheetAlias, oneLeftsb);
                        leftsb.append(left).append(" ");
                    }
                }

                wholeSql.setWholeSql(String.format("select %s.*,%s from %s %s %s ", thePreviousTableAlias, columnsb.toString(), getTableStr(wholeSql.getWholeSql(), null), thePreviousTableAlias, leftsb.toString()));
            }
        }
        if (sheetDependency.getNext() != null) {
            structRecursionSql(sheetDefine, sheetDependency.getNext(), recursionDepth + 1, wholeSql, alreadyAppearedFieldName);
        }
    }

    private static String getTableStr(String tableName, DataSourceType dbType) {
        String upperCase_tableName = tableName.toUpperCase();
        if (dbType != null && DataSourceType.SYSBASE.equals(dbType)) {
            if (upperCase_tableName.contains("SELECT ") && upperCase_tableName.contains(" FROM ")) {
                return " (" + tableName + ") ";
            } else {
                return " " + tableName.toUpperCase() + " ";
            }
        } else {
            if (upperCase_tableName.contains("SELECT ") && upperCase_tableName.contains(" FROM ")) {
                return "(" + tableName + ") ";
            } else {
                return " " + tableName + " ";
            }
        }
    }
}
