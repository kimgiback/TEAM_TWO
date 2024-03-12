    package com.korea.gift;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dto.BusinessDTO;
import dto.QnaDTO;
import dto.ReplyDTO;
import lombok.RequiredArgsConstructor;
import service.BuBoardService;
import service.BuMemberService;
import util.Common;

@Controller
@RequiredArgsConstructor
@RequestMapping("/buBoard")
public class BuBoardController {

	private final BuMemberService memberService;
	private final BuBoardService boardService;

	@Autowired
	HttpSession session;

	@RequestMapping("qnaList")
	public String qnaList(Model model) {
		System.out.println("qnaList");

		model.addAttribute("qnaList", boardService.qnaList());

		return Common.Business.BOARD_VIEW_PATH + "/qnaList.jsp";
	}

	@RequestMapping("qnaDetail")
	public String qnaDetail(@RequestParam("qna_no") int qna_no, Model model, HttpSession session) {
		System.out.println("qnaDetail");

		// BusinessDTO buDTO = (BusinessDTO) session.getAttribute("buLogin");

		List<ReplyDTO> replies = boardService.replyList(qna_no);
		List<BusinessDTO> writers = new ArrayList<BusinessDTO>();

		for (int i = 0; i < replies.size(); i++) {
			ReplyDTO reply = replies.get(i);
			int bu_no = reply.getBu_no();

			BusinessDTO writer = memberService.buOne(bu_no);
			writers.add(writer);
		}

		model.addAttribute("qna", boardService.qnaOne(qna_no));
		model.addAttribute("replyList", replies);
		model.addAttribute("writers", writers); // bu_no, bu_writer ����Ҽ�? �� �Ѱ��ֱ�

		return Common.Business.BOARD_VIEW_PATH + "/qnaDetail.jsp";
	}

	@RequestMapping("/replyWriteForm")
	public String buReplyWriteForm(@RequestParam("qna_no") int qna_no, Model model) {
		System.out.println(qna_no);
		model.addAttribute("qna_no", qna_no);
		
		System.out.println("replyWriteForm");

		return Common.Business.BOARD_VIEW_PATH + "/replyWriteForm.jsp";
	}

	@RequestMapping("/replyWrite")
	public String buReply(ReplyDTO replyDTO, HttpSession session) {
		System.out.println(replyDTO.getQna_no());
		
		System.out.println("replyWrite");

		// fk bu_no �����ϱ�
		BusinessDTO writer = (BusinessDTO) session.getAttribute("buLogin");
		int bu_no = writer.getBu_no();
		replyDTO.setBu_no(bu_no);

		int res = boardService.replyWrite(replyDTO);

		if (res > 0) {
			return "redirect:/buBoard/qnaDetail";
		}

		return null;

	}

	@RequestMapping("/replyUpdateForm")
	public String buReplyUpdateForm(int re_no, Model model, HttpSession session, RedirectAttributes rttr) {
		System.out.println("replyUpdateForm");

		ReplyDTO reply = boardService.replyOne(re_no);

		// �ۼ��� ��ġ�ϴ��� ����
		int now_no = ((BusinessDTO) session.getAttribute("buLogin")).getBu_no();
		int bu_no = reply.getBu_no();

		if (now_no == bu_no) {
			model.addAttribute("update", reply);

			return Common.Business.BOARD_VIEW_PATH + "/buReplyUpdateForm.jsp";
		} else {
			rttr.addFlashAttribute("errUpdateForm", false);
		}

		return "redirect:/buItem/qnaDetail";
	}

	@RequestMapping(value = "/replyUpdate", method = RequestMethod.POST)
	public String buUpdate(ReplyDTO replyDTO) {
		System.out.println("replyUpdate");

		int res = boardService.replyUpdate(replyDTO);

		if (res > 0) {
			return "redirect:/buItem/qnaDetail";
		}

		return null;

	}

	@RequestMapping("/replyDelete")
	public String buDelete(int re_no, HttpSession session, RedirectAttributes rttr) {
		System.out.println("replyDelete");

		ReplyDTO reply = boardService.replyOne(re_no);

		// �ۼ��� ��ġ�ϴ��� ����
		int now_no = ((BusinessDTO) session.getAttribute("buLogin")).getBu_no();
		int bu_no = reply.getBu_no();

		if (now_no == bu_no) {
			boardService.replyDelete(re_no);
		} else {
			rttr.addFlashAttribute("errDelete", false);
		}

		return "redirect:/buItem/qnaDetail";
	}

}

    
