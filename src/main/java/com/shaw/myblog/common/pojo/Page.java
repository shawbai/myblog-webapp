package com.shaw.myblog.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable {

	private static final long serialVersionUID = -6582473365671012877L;
	public static final int MAX_PAGE_SIZE = 500;// 每页最大记录数限制

	/**
	 * 当前第几页
	 */
	private int pageNumber = 1;
	/**
	 * 每页记录数
	 */
	private int pageSize = 20;
	/**
	 * 总条数
	 */
	private Long totalCount = 0L;
	/**
	 * 总页数
	 */
	private Long pageCount = 0L;
	
	/**
	 * 分页list
	 */
	private  List<T> content = new ArrayList<T>();

	public Page() {

	}

	public Page(List<T> content) {
		if (null == content) {
			throw new IllegalArgumentException("Content must not be null!");
		}
		this.content.addAll(content);
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		if (pageNumber < 1)
			this.pageNumber = 1;
		else
			this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if (pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
		// 设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
		Long pageCount = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
		this.setPageCount(pageCount);
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}

	public Long getPageCount() {
		return pageCount;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + (pageNumber ^ pageNumber >>> 32);
		result = 31 * result + (int) (totalCount ^ totalCount >>> 32);
		result = 31 * result + (int) (pageCount ^ pageCount >>> 32);
		result = 31 * result + content.hashCode();
		return result;
	}
}
