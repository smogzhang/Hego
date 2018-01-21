package cn.ego.service;

import cn.ego.bean.EUDataGridResult;
import cn.ego.bean.EgoResult;

public interface ItemParamService {

	/**
	 * 根据商品类目id查询商品规格，看是否已经有规格，若无则添加，若有则提示
	 * @param itemCatId
	 * @return
	 */
	EgoResult getItemParamByItemCatId(long itemCatId) throws Exception;

	/**
	 * 保存商品规格参数模板
	 * @param itemCatId 商品类目id
	 * @param paramData 商品规格参数模板，json格式
	 */
	EgoResult saveItemParamWithItemCatId(long itemCatId, String paramData) throws Exception;

	/**
	 * 分页展示商品规格参数模板
	 * @param page
	 * @param rows
	 * @return 分页数据容器EUDataGridResult
	 */
	EUDataGridResult listItemParamByPage(int page, int rows);

	/**
	 * 删除商品规格模板
	 * @param itemParamId 商品规格表主键
	 */
	EgoResult deleteItemParamById(long itemParamId);
	
}
