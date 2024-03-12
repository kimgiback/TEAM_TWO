package com.korea.gift;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import dao.buItemDAO;
import dto.BusinessDTO;
import dto.CategoryDTO;
import dto.ItemDTO;
import dto.ItemImagefileDTO;
import lombok.RequiredArgsConstructor;
import util.Common;

@Controller
@RequestMapping("/buItem")
@RequiredArgsConstructor
public class BuItemController {

	private final buItemDAO itemDAO;

	@Autowired
	HttpSession session;

	@Autowired
	HttpServletRequest request;

	private static int IMG_NO_SEQ = 0;

	@RequestMapping(value = "/insertForm", method = RequestMethod.GET)
	public String insertForm() {
		System.out.println("insertForm");

		return Common.Business.ITEM_VIEW_PATH + "/buItemRegister.jsp";
	}

	@RequestMapping("/insert")
	public String insert(@ModelAttribute("dto") ItemDTO dto) {
		System.out.println("insert");

		BusinessDTO writer = (BusinessDTO) session.getAttribute("buLogin");
		int writer_no = writer.getBu_no();
		System.out.println("get bu no");

		// 占쏙옙占�, 占쏙옙占싹몌옙, dto占쏙옙 image 占쌨아울옙占쏙옙
		String webPath = "/resources/images/item";
		System.out.println("11");

		String savePath = request.getServletContext().getRealPath(webPath);
		System.out.println("22");

		MultipartFile photo = dto.getItem_image();
		CategoryDTO cate = itemDAO.selectCateOne("스타벅스");
		String filename = photo.getOriginalFilename();

		System.out.println(filename);

		// String占쏙옙 integer占쏙옙
		dto.setBu_no(writer_no);

		dto.setItem_price((int) dto.getItem_price());
		dto.setItem_stock((int) dto.getItem_stock());

		dto.setItem_name(filename);
		dto.setCategory_no(cate.getCategory_no());
		dto.setBrand(cate.getCategory_name());

		System.out.println(dto.toString());

//		int item_no = itemDAO.insert(dto);

		// imagefileDTO占쏙옙占쏙옙
		ItemImagefileDTO fileDTO = new ItemImagefileDTO();
		fileDTO.setImg_name(filename);

		IMG_NO_SEQ++;
		fileDTO.setImg_no((int) IMG_NO_SEQ);
		fileDTO.setItem_no((int) IMG_NO_SEQ);

		itemDAO.insertImageFile(fileDTO);

		if (!photo.isEmpty()) {
			File saveFile = new File(savePath, filename);

			if (!saveFile.exists()) {
				saveFile.mkdirs();
			} else {
				long time = System.currentTimeMillis();
				filename = String.format("%d_%s", time, filename);
				saveFile = new File(savePath, filename);
			}

			try {
				photo.transferTo(saveFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "redirect:/buItem/insertForm";

	}

}
