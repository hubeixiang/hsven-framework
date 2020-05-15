package org.framework.hsven.dataloader.related.child;


import org.framework.hsven.dataloader.beans.EnumDbDataType;

import java.util.ArrayList;
import java.util.List;

public class MainTableFieldValue {

	private EnumDbDataType dataType;
	private String fieldValueStr;
	private final List<Integer> mainTableRowIndexList = new ArrayList<Integer>();

	public void addRowIndex(Integer rowIndex) {
		if (rowIndex == null) {
			return;
		}
		mainTableRowIndexList.add(rowIndex);
	}

	public EnumDbDataType getDataType() {
		return dataType;
	}

	public void setDataType(EnumDbDataType dataType) {
		this.dataType = dataType;
	}

	public String getFieldValueStr() {
		return fieldValueStr;
	}

	public void setFieldValueStr(String fieldValueStr) {
		this.fieldValueStr = fieldValueStr;
	}

	public List<Integer> getMainTableRowIndexList() {
		return mainTableRowIndexList;
	}

	@Override
	public String toString() {
		return "MainTableFieldValue [dataType=" + dataType + ", fieldValueStr=" + fieldValueStr + ", mainTableRowIndexList=" + mainTableRowIndexList + "]";
	}

}
