package com.korea.gift;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.MainDAO;
import dto.CategoryDTO;
import dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import util.Common;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private final MainDAO mainDAO;
	
	// 
	@GetMapping
	public String mainPage(Model model) {
		
		// 
		List<CategoryDTO> cateInfo = mainDAO.categoryInfo();
		
		// 
		int itemCount = mainDAO.itemCountAll();
		
		// 
		List<ItemDTO> itemList = mainDAO.itemListAll();
		
		model.addAttribute("cateInfo", cateInfo);
		model.addAttribute("itemCount", itemCount);
		model.addAttribute("itemList", itemList);
		
		return Common.Main.VIEW_PATH + "main.jsp";
	}
	
	// 
	@GetMapping("/list_ajax")
	@ResponseBody
	public Map<String, Object> cateCountAjax(@RequestParam("category") Integer cate_no, Model model) {
		
		Map<String, Object> map = new HashMap<>();
		
		Integer itemCount;
		List<ItemDTO> itemList;
		
		// 
		if (cate_no == 0) {
			itemCount = mainDAO.itemCountAll();
			itemList = mainDAO.itemListAll();
		} else {
			// 
			itemCount = mainDAO.itemCountCate(cate_no);
			itemList = mainDAO.itemListCate(cate_no);			
		}
		
		map.put("count", itemCount);
		map.put("list", itemList);
		
		return map;
	}
	
	
}
