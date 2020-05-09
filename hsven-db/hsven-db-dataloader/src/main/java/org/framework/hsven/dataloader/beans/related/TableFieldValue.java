package org.framework.hsven.dataloader.beans.related;

public class TableFieldValue {

	private TableField fieldInfo;
	private Object fieldValue;
	
	public String getFieldAlias()
	{
		if(fieldInfo==null)
		{
			return null;
		}
		return fieldInfo.getFieldAlias();
	}
	public TableField getFieldInfo() {
		return fieldInfo;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldInfo(TableField fieldInfo) {
		this.fieldInfo = fieldInfo;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
}
