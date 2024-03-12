    package dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import dto.MemberDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberDAO {
	
	final SqlSession sqlSession;
//	public MemberDAO(SqlSession sqlSession) {
//		
//		this.sqlSession = sqlSession;
//	}
	
	
	public MemberDTO login(HashMap<String, String> map) {
		
		System.out.println("���̵�dao : " +map.get("id"));
		System.out.println("��й�ȣdao : " +map.get("pwd"));
		

		MemberDTO dto = sqlSession.selectOne("m.memberlogin",map);
		if(dto==null) {
			System.out.println("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
		}else {
			System.out.println(dto.getM_idx());
			System.out.println(dto.getM_name());
			System.out.println("���� ���̵� : " + dto.getM_id());
			System.out.println("���� ��й�ȣ : " + dto.getM_pwd());
			//return null;
		}
		

		return dto;
	}


	public void insert(HashMap<String, String> m_join_insert) {
		System.out.println("DAO �ڹ� ���");
		System.out.println(m_join_insert.get("id"));
		System.out.println(m_join_insert.get("pwd"));
		System.out.println(m_join_insert.get("name"));
		System.out.println(m_join_insert.get("addr"));
		System.out.println(m_join_insert.get("email"));
		System.out.println(m_join_insert.get("phone"));
		sqlSession.insert("m.memberjoin", m_join_insert);
		System.out.println("insert�Ϸ�");
		//sqlSession.insert("m.memberjoin", memberDTO);
		
	}


	public String check_id(String id) {
		// TODO Auto-generated method stub
		System.out.println("dao�� ���� "+id);
		String checked_id = sqlSession.selectOne("m.checkid", id);
//		if(checked_id==null) {System.out.println("���̵� �����ϴ�.");}
//		else {System.out.println("�ߺ��� ���̵� "+checked_id+"�� �ֽ��ϴ�.");}
		
		return checked_id;
		
		
	}


	
	
}

    
