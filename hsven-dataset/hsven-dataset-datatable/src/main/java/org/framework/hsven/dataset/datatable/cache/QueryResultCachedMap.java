package org.framework.hsven.dataset.datatable.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class QueryResultCachedMap
{
	private int MAX_CACHED_QR_NUM=100;
	Map<String, QueryResult> cachedMap=new HashMap<String,QueryResult>(MAX_CACHED_QR_NUM);
	List<String> cachedList=new LinkedList<String>();
	
	/**
	 * 放置一个 QueryResult 到缓存中，相同表达式的对象将被置换
	 * @param keyExp 表达式字符串，唯一识别一个查询结果
	 * @param qr QueryResult对象
	 */
	public synchronized void put(String keyExp,QueryResult qr)
	{
		if(keyExp==null|| qr==null){return;}
		if (cachedMap.containsKey(keyExp))			//队列中有旧的缓存值
		{
			cachedMap.get(keyExp).clear();
			cachedMap.put(keyExp, qr);
			return;
		}
		if (cachedList.size()>=MAX_CACHED_QR_NUM)	//队列超过最大值，清除first
		{
			QueryResult tmpQr=cachedMap.get(cachedList.get(0));
			tmpQr.clear();		
			cachedMap.remove(cachedList.get(0));
			cachedList.remove(0);
		}
		cachedMap.put(keyExp, qr);
		cachedList.add(keyExp);
	}
	
	/**
	 * 得到一个查询结果对象
	 * @param keyExp 表达式
	 * @param updateCounterSign 更新标志，当过期时对象将被删除
	 * @return QueryResult 对象
	 */
	public synchronized QueryResult get(String keyExp,int updateCounterSign)
	{
		if(keyExp==null){return null;}
		QueryResult tmpQr=cachedMap.get(keyExp);
		if(tmpQr==null) { return null;}
		if(tmpQr.dtLatestUpdateCounter==updateCounterSign)
		{
			return tmpQr;
		}
		tmpQr.clear();
		cachedList.remove(keyExp);
		cachedMap.remove(keyExp);	
		return null;
	}
	
	/**
	 * 清空全部队列
	 */
	public synchronized void clear()
	{
		int size=cachedList.size();
		if (size==0) { return ;}
		for (int jj=0;jj<size;jj++)
		{
			(cachedMap.get(cachedList.get(jj))).clear();
		}
		cachedMap.clear();
		cachedList.clear();
	}
	
	
}
