package com.mct.util;

import java.util.List;

public class Page<E> {

	private int offset;
	
	private int limit;
	
	private List<E> resultList;

	
	public Page(){
		offset = 0;
		limit = Integer.MAX_VALUE;
	}
	
	public Page(int offset, int limit){
		this.offset = offset;
		this.limit = limit;
	}
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<E> getResultList() {
		return resultList;
	}

	public void setResultList(List<E> resultList) {
		this.resultList = resultList;
	}
	
}
