package org.framework.hsven.dataset.datatable.funmanager;

public class FunctionRegObj
{
	public String name;				//名称，全小写
	public String classPathName;	//函数的全路径类名
	public boolean isStatic;		//是否是静态函数
	public boolean isIdempotent;	//是否幂等性
	
	public FunctionRegObj(String name,String classPathName,boolean isStatic,boolean isIdempotent)
	{
		this.name=name;
		this.classPathName=classPathName;
		this.isStatic=isStatic;
		this.isIdempotent=isIdempotent;
	}
	
	public FunctionRegObj(String name,String classPathName,boolean isStatic)
	{
		this(name,classPathName,isStatic,true);
	}
	
}
