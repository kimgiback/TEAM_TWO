package com.korea.gift;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.MemberDAO;
import dao.UserQnaDAO;
import dto.MemberDTO;
import dto.UserQnaDTO;
import lombok.RequiredArgsConstructor;
import util.Common;
import util.Page;

@Controller
@RequiredArgsConstructor
public class UserQnaController {
	
	final UserQnaDAO userQnaDAO;	
//	final MemberDAO memberDAO;
	
	@Autowired
	HttpServletRequest request;	
	
	@Autowired
	HttpSession session;
	
	// ����� qna(�Խñ�) �۾��� ȭ������ �̵�
	@RequestMapping("userQna_form")
	public String insert_form(Model model) {

		MemberDTO show = (MemberDTO)session.getAttribute("m_idx");
		
		model.addAttribute("show",show);

		// �α��� ������ �ۼ� �Ϸ� �� �׽�Ʈ �ʿ�
//		if(show == null) {
//			return Common.Member.VIEW_PATH + "login.jsp";
//		}
		
		
//		int m_idx = show.getM_idx();	
//		dto.setM_idx(m_idx);
//		
//		// Ÿ�� �̸� Ȯ�� �� ���� �ʿ�
//		MemberDTO memberDTO = UserQnaDAO.selectOne(m_idx);	
//		
//		model.addAttribute("memberDTO", memberDTO);
		
		List<MemberDTO> memberList = userQnaDAO.memberSelect();				
		
		return Common.Board.VIEW_PATH + "userQna_form.jsp";
	}
	
	// ����� qna(�Խñ�) �߰�
	@RequestMapping("userQna_insert")
	public String insert(UserQnaDTO dto) {
		
		MemberDTO show = (MemberDTO)session.getAttribute("m_idx");
		
		// �α��� ������ �ۼ� �Ϸ� �� �׽�Ʈ �ʿ�
		if(show == null) {
			return Common.Member.VIEW_PATH + "login.jsp";
		}
		
		int m_idx = show.getM_idx();
		dto.setM_idx(m_idx);

		int res = userQnaDAO.insert(dto);

		if(res > 0) {
			//��ϿϷ��� �Խ����� ù �������� ����
			return "redirect:userQna_list";
		}
		return null;
	}	
	
	// ����� qna(�Խñ�) ��ȸ ȭ������ �̵�
	@RequestMapping("userQna_list")
	public String list(Model model, @RequestParam(required= false, defaultValue="1") int page) {		
		
		//�� �������� ǥ�õ� �Խù��� ���۰� ����ȣ ���
		//page�� 1�̸� 1~10���� ���Ǿ���
		//page�� 2�̸� 11~20���� ���Ǿ���
		int start = (page - 1) * Common.Board.BLOCKLIST + 1;
		int end = start + Common.Board.BLOCKLIST - 1;		
		
		HashMap<String, Integer> map = new HashMap<>();
		map.put("start",start);
		map.put("end",end);
		
		//List<UserQnaDTO> list = userQnaDAO.selectList(map);
		
		//��ü �Խù� �� ��ȸ
		int rowTotal = userQnaDAO.getRowTotal();
		
		//������ �޴� �����ϱ�
		String pageMenu = Page.getPaging(
				"board_list",
				page, //���� ������ ��ȣ
				rowTotal, //��ü �Խù� ��
				Common.Board.BLOCKLIST, //�� �������� ǥ���� �Խù� ��
				Common.Board.BLOCKPAGE); //������ �޴� ��
		
		MemberDTO show = (MemberDTO)session.getAttribute("m_idx");		
		
		List<UserQnaDTO> list = userQnaDAO.selectList();
		
		model.addAttribute("list",list);
		model.addAttribute("pageMenu",pageMenu);
		
		return Common.Board.VIEW_PATH + "userQna_list.jsp?page="+page;
	}
	

}
