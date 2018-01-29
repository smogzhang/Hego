package cn.ego.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.ego.bean.EgoResult;
import cn.ego.pojo.TbItem;
import cn.ego.portal.service.CartService;
import cn.ego.utils.CookieUtils;
import cn.ego.utils.HttpClientUtil;
import cn.ego.utils.JsonUtils;

@Service
public class CartServiceImpl implements CartService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM}")
	private String ITEM;
	@Value("${CART_KEY_COOKIE_NAME}")
	private String CART_KEY_COOKIE_NAME;
	@Value("${CART_KEY_COOKIE_NAME_EXPIRE}")
	private int CART_KEY_COOKIE_NAME_EXPIRE;
	
	@Override
	public EgoResult addItemToCart(long itemId, HttpServletRequest request, HttpServletResponse response) {
//		CookieUtils.getCookieValue(request, cookieName);
		TbItem item = getItemById(itemId);
		if(item == null) 
			return EgoResult.build(400, "无此商品");
		//从保存在cookie中的cart获取ItemList
		List<TbItem> items = listItemsFormCart(request);
		boolean exit = false;
		//遍历购物车，判断是否存在商品，存在则数量加一
		for (TbItem i : items) {
			if(i.getId() == itemId) {
				i.setNum(i.getNum() + 1);
				exit = true;
				break;
			}
		}
		//不存在 则添加
		if(!exit) {
			item.setNum(1);
			items.add(item);
		}
		//把购物车信息写入cookie中
		CookieUtils.setCookie(request, response, CART_KEY_COOKIE_NAME, JsonUtils.objectToJson(items), CART_KEY_COOKIE_NAME_EXPIRE, true);
		return EgoResult.ok(items);
	}

	/**
	 * 根据商品id查询商品信息
	 * @param itemId
	 * @return
	 */
	private TbItem getItemById(long itemId) {
		// 调用rest服务，从数据库检索商品
		String itemJson = HttpClientUtil.doGet(REST_BASE_URL + ITEM + itemId);
		EgoResult egoResult = JsonUtils.jsonToPojo(itemJson, EgoResult.class);
		if(200 == egoResult.getStatus()) {
			return (TbItem) egoResult.getData();
		}
		return null;
	}
	
	/**
	 * 从cookie中获取cart
	 * @param request
	 * @return
	 */
	private List<TbItem> listItemsFormCart(HttpServletRequest request) {

		String cookieValue = CookieUtils.getCookieValue(request, CART_KEY_COOKIE_NAME, true);
		if(StringUtils.isEmpty(cookieValue)) 
			//购物车为空时，需要创建list对象
			return new ArrayList<TbItem>();
		return JsonUtils.jsonToList(cookieValue, TbItem.class);
	}

}