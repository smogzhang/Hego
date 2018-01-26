package cn.ego.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.ego.search.bean.SearchResult;
import cn.ego.search.dao.ItemSearchDao;
import cn.ego.search.service.ItemSearchService;

/**
 * 处理分页数据
 * @author Sully
 *
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {

	//注入Solr对象，查询索引库
	
	@Autowired
	private ItemSearchDao itemSearchDao;
	@Value("${PAGE_SIZE}")
	private Integer PAGE_SIZE;
	
	@Override
	public SearchResult listItems(String queryString, Integer page, Integer rows) throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		//查询条件
		if (StringUtils.isEmpty(queryString)) {
			solrQuery.setQuery("*:*");
		} else {
			solrQuery.setQuery(queryString);
		}
		//分页条件
		if (page == null)
			page = 1;
		if (rows != null)
			PAGE_SIZE = rows;
		solrQuery.setStart((page -1) * PAGE_SIZE);
		solrQuery.setRows(PAGE_SIZE);
		//高亮显示
		solrQuery.setHighlight(true);
		//设置高亮显示的域
		solrQuery.addHighlightField("item_title");
		//高亮显示前缀
		solrQuery.setHighlightSimplePre("<span style=\"color:red\">");
		//后缀
		solrQuery.setHighlightSimplePost("</span>");
		//设置默认搜索域
		solrQuery.set("df", "item_keywords");

		SearchResult daoResult = itemSearchDao.listItems(solrQuery);
		
		//计算分页
		Long recordCount = daoResult.getRecordCount();
		int pageCount = (int) (recordCount / PAGE_SIZE);
		if (recordCount % PAGE_SIZE > 0) {
			pageCount++;
		}
		daoResult.setPageCount(pageCount);
		daoResult.setCurPage(page);

		return daoResult;
	}

}
