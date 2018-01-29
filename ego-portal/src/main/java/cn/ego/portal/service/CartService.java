package cn.ego.portal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ego.bean.EgoResult;

public interface CartService {

	EgoResult addItemToCart(long itemId, HttpServletRequest request, HttpServletResponse response);
}
