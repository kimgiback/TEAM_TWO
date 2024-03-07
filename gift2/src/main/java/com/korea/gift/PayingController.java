package com.korea.gift;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.ItemDAO;
import dao.MemberDAO;
import dao.PayingDAO;
import dto.ItemDTO;
import dto.PayingDTO;
import lombok.RequiredArgsConstructor;
import util.Common;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class PayingController {

	@Autowired
	HttpSession session;

	final SqlSession sqlSession;

	final PayingDAO pay_dao;
	final MemberDAO member_dao;
	final ItemDAO item_dao;

	@RequestMapping("payitem")
	public String payitem(@RequestParam("item_no") int item_no, Model model) {
		ItemDTO itemDTO = pay_dao.payitem(item_no);
		model.addAttribute("itemDTO", itemDTO);

		List<PayingDTO> Payinglist = pay_dao.pay_info(item_no);
		model.addAttribute("Payinglist", Payinglist);

		return Common.Paying.VIEW_PATH + "paying.jsp";

	}

	@RequestMapping("card")
	@ResponseBody
	public String card(PayingDTO dto, int item_no) {
		Map<String, Object> map = new HashMap<>();
		map.put("item_no", item_no);
		map.put("payment", dto.getPayment());

		int res = pay_dao.pay_info_update(map);
		System.out.println("Res=" + res);
		String result = "";

		if (res > 0) {
			result = "[{'result':'success'}]";
		} else {
			result = "[{'result':'fail'}]";
		}
		return result;
	}

	@RequestMapping("buy_info")
	public String buy_info(Model model) {
		List<PayingDTO> Buyinglist = pay_dao.buy_info();
		model.addAttribute("Buyinglist", Buyinglist);
		return Common.Paying.VIEW_PATH + "buy_info.jsp";
	}

	@RequestMapping("BuyingCheck")
	@ResponseBody
	public String BuyingCheck(PayingDTO dto) {
		int res = pay_dao.BuyingCheck(dto);
		System.out.println("res=" + res);

		if (res >= 0) {
			return "[{'result':'yes'}]";
		} else {
			return "{['result':'no'}]";
		}

	}

}
