提供搜索服务，solr界面导入数据源，查询索引库
接收分页参数(page,rows)以及过滤条件
返回源数据(利用公共类EgoResult 封装数据和其它信息)
URL:/search/item/list
可选参数：1.kw(queryString) 查询语句，2.page第几页，3.rows一页多少条