package ztv.core;

import java.util.ArrayList;
import java.util.List;

public class PageController<T> {
	
	private List<T> dataList;
	private int currentPage = -1;
	private int totalPage = 0;
	private int limit = 25;
	
	public void setLimit(int l) {
		limit = l;
	}
	
	public void bind(List<T> data) {
		dataList = data;
		totalPage = data.size();
	}
	
	public boolean hasNext() {
		return currentPage < totalPage - 1;
	}
	
	public List<T> next() {
		ArrayList<T> l = new ArrayList<T>();
		
		if (dataList != null) {
			currentPage++;
			fillList(l);
		}
		
		return l;
	}
	
	public boolean hasPrevious() {
		return currentPage > 0;
	}
	
	public List<T> previous() {
		ArrayList<T> l = new ArrayList<T>();
		
		if (dataList != null) {
			currentPage--;
			fillList(l);
		}
		
		return l;
	}
	
	public int getCurrentPage() {
		return currentPage + 1;
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	
	protected void fillList(List<T> l) {
		int start = currentPage * limit;
		int end = ((start + limit) > dataList.size())? dataList.size() : (start + limit);
		
		while (start < end) {
			l.add(dataList.get(start++));
		}
	}
}
