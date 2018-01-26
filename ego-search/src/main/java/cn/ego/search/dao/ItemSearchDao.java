package cn.ego.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import cn.ego.search.bean.SearchResult;

public interface ItemSearchDao {

	SearchResult listItems(SolrQuery solrQuery) throws Exception;

}
