package dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import dto.MemberDTO;
import dto.UserQnaDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserQnaDAO {

	
	final SqlSession sqlSession;	
	
	// ����� ȸ���� �̸�, ��ȭ��ȣ ��ȸ ��ȸ
	// -> Ÿ���̸� Ȯ�� �� ���� �ʿ�
	public List<MemberDTO> memberSelect() {
		List<MemberDTO> list = sqlSession.selectList("userQna.member_one");
		return list;
	}
	
	// ����� qna(�Խñ�) �߰�
	public int insert(UserQnaDTO dto) {
		return sqlSession.insert("userQna.qna_insert", dto);
	}
	
	// ����� ��ü qna(�Խñ�) ��ȸ
	public List<UserQnaDTO> selectList(){		
		List<UserQnaDTO> list = sqlSession.selectList("userQna.qna_list");	
		return list;
	}
	
	// ��ü �Խù� �� ��ȸ
	public int getRowTotal() {
		int count = sqlSession.selectOne("userQna.board_count");
		return count;
	}
	
}
