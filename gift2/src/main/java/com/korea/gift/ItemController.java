package com.korea.gift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.ItemDAO;
import dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import util.Common;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private final ItemDAO itemDAO;
	
	
	@GetMapping("/{item_no}")
	public String itemDetail(@PathVariable("item_no") int item_no, Model model) {
		
		// �긽�뭹 �긽�꽭
		ItemDTO item = itemDAO.selectOne(item_no);
		
		model.addAttribute("item", item);
		
		return Common.Item.VIEW_PATH + "item_detail.jsp";
	}
	
	
}
