package cn.ego.bean.search;

import java.util.List;

/**
 * 搜索服务最后返回数据,用于分页
 * 
 * @author Sully
 *
 */
public class SearchResult {

	private long recordCount;
	private List<Item> itemList;
	private Integer pageCount;
	private Integer curPage;

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
}
