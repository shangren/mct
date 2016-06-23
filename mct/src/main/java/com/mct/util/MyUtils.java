package com.mct.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author yangchao.wang
 *
 * @email  yangchao.wang@flaginfo.com.cn
 * @Desc  
 * 工具类 
 * @time 2016年6月23日下午4:39:58
 */
public class MyUtils {

	/**
	 * 创建hashMap
	 * @return
	 */
	public static <K,V> Map<K, V> newMap(){
		return new HashMap<K,V>();
	} 
	
	/**
	 * 创建arrayList
	 * @return
	 */
	public static <T>  List<T> newArrayList(){
		return new ArrayList<T>();
	}
	
}
