package cn.ego.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ego.bean.EgoResult;
import cn.ego.portal.service.CartService;

@Controller
@RequestMapping("/portal/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	/**
	 * 根据商品id添加商品进购物车
	 * @param itemId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/add/{itemId}")
	public String addCartWithoutLogin(@PathVariable long itemId, HttpServletRequest request, HttpServletResponse response, Model model) {
		EgoResult result = cartService.addItemToCart(itemId, request, response);
		model.addAttribute("itemList", result.getData());
		return "cart";
	}
	
}
