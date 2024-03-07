package com.korea.gift;

import java.util.HashMap;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.MemberDAO;
import dto.MemberDTO;
import lombok.RequiredArgsConstructor;
//import service.MemberService;
import util.Common;

@RequiredArgsConstructor
@Controller
public class MemberController {

   
   final MemberDAO memberDAO;
   


   
   HttpServletRequest request;
   
//   @Autowired
//   MemberDTO insert_dto;

   @Autowired
   HttpSession session;
   
   MemberDTO dto = null;
   


   //로그인 페이지 이동
   @RequestMapping(value="mlogin")//, method = RequestMethod.GET
   public String member() {
      return Common.Member.VIEW_PATH + "login.jsp";
   }
   
   
   @RequestMapping(value="mloginconf")//, method = RequestMethod.POST
   @ResponseBody
   public String login(String id, String pwd) {
      
      System.out.println("아이디 : " + id);
      System.out.println("비밀번호 : " + pwd);
      
      HashMap<String, String> map = new HashMap<String, String>();
      map.put("id",id);
      map.put("pwd", pwd);
      
      dto = memberDAO.login(map);
      
      if(dto == null) {
         System.out.println("아이디나 비밀번호가 일치하지 않습니다.");
         
         //return "login/[{'data':'null_data'}]";
         HashMap<String, String> not_data = new HashMap<String, String>();
         not_data.put("data", "null_data");
         
         //String not_data = "{'data':'null_data'}";
         
//         return "mlogin/"+not_data;
         //System.out.println(not_data);
         
         
         //model.addAttribute("data", "null_data");
         //System.out.println(model);
         
         String link = Common.Member.VIEW_PATH;
         
         //return not_data;
//         return link+"login.jsp";
         return "[{'data':'null_data'}]";
      }else {
         System.out.println("로그인 준비함");
      }

      //int m_idx = dto.getM_idx();
      
//      session = request.getSession();
      
      //session.getAttribute("name");
      
      //System.out.println(session.getAttribute("m_idx"));
      

      

      //The left-hand side of an assignment must be a variable - 상수로 인한 타입 미스매치
      //Invalid left-hand side in assignment
      
//      부적절한 위치에서 할당 행위를 하려고 할 때 대표적인 발생하는 에러로서, 흔한 문법 오류이다.
//      가장 흔한 실수하는 부분이 논리연산자 부분에 변수 할당하려고 했을때 자주 발생한다.
//      출처: https://inpa.tistory.com/entry/ERROR-⚠️-Invalid-left-hand-side-in-assignment [Inpa Dev 👨‍💻:티스토리]
      
      int m_idx = dto.getM_idx();
      
      //세션 설정
      
      session.setAttribute("m_idx", m_idx);
      
      int idx_session = (int) session.getAttribute("m_idx");
      System.out.println("너의 세션값은 " + idx_session);
      
      //세션 조회
      
      //MemberDTO memberDTO = (MemberDTO)session.getAttribute("m_idx");
      //System.out.println(memberDTO);
      
      //dto.getM_idx(idx_session);
      
      //세션 타이밍 설정
      session.setMaxInactiveInterval(180000); // 180,000sec
      
      //System.out.println("세션 설정함");
//      String pwd2 = dto.getBu_pwd();
//      
//
//      if(pwd2 == null) {
//         return "비밀번호가 일치하지 않습니다";
//      }
//      
//      System.out.println(id2);
//      System.out.println(pwd2);
//      
//      
//      System.out.println("일치");
      //return "redirect:mjoin";
      
      
      //HashMap<String, String> data = new HashMap<String, String>();
      //data.put("data", "having_data");
      
      String data = "[{'data':'having_data'}]";
      
      return data;
   }
//   public String 
   

   /*
    * @RequestMapping public String login(MemberDTO dto){ 
    * return
    * memberDAO.login(memberDAO); }
    */
   
   
   
   
   
   
   @RequestMapping(value="mjoin", method = RequestMethod.GET)
   public String join() {
      return Common.Member.VIEW_PATH + "member_join.jsp";
   }
   
   
   @RequestMapping("mjoininsert")
   public void memberinsert(String id, String pwd, String name, String addr, String email, String phone){
      
      
//      
//      insert_dto.setBu_id(id);
//      insert_dto.setBu_pwd(pwd);
//      insert_dto.setBu_name(name);
//      insert_dto.setBu_adress(addr);
//      insert_dto.setBu_email(email);
//      insert_dto.setBu_phone(콜);
//      System.out.println(id);
//      
//      
//      memberDAO.insert(insert_dto);
   }
   

   
   


   
   
   
   
}