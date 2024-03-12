package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import dto.CartDTO;
import dto.ItemDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemDAO {

	final SqlSession sqlSession;
	
	// 상품 상세
	public ItemDTO selectOne(int item_no) {
		return sqlSession.selectOne("item.item_detail", item_no);
	}
	
	// 조회수 증가
	public int readhitUpdate(int item_no) {
		return sqlSession.update("item.readhit_update", item_no);
	}
	
	/*
	 * public ItemDTO cartItem(CartDTO cartDTO) { 
	 * return sqlSession.selectOne("item.itemMap", cartDTO); 
	 * }
	 */
	
	public ItemDTO ItemOne(CartDTO cartDTO) {
		return sqlSession.selectOne("item.itemOne", cartDTO);
	}
	

}
