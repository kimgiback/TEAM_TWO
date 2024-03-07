package dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import dto.ItemDTO;
import dto.PayingDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PayingDAO {
	

final SqlSession sqlSession;


/*
 * public List<PayingDTO> pay_info() { 
 * List<PayingDTO> Payinglist = sqlSession.selectList("p.pay_info"); 
 * return Payinglist; 
 * }
 */
	
	public int pay_info_update(Map<String, Object> map) {
		int res = sqlSession.update("p.paying_info_update", map);
	
		return res;
	}
	
	public List<PayingDTO> buy_info() {
		List<PayingDTO> Buyinglist = sqlSession.selectList("p.pay_info");
		return Buyinglist;
	}
	
	public int BuyingCheck(PayingDTO dto) {
		int res = sqlSession.update("p.BuyingCheck", dto);
		return res;
	}
	
	public ItemDTO payitem(int item_no) {
		ItemDTO itemDTO = sqlSession.selectOne("p.payitem", item_no);
		return itemDTO;
	}
	
	public List<PayingDTO> pay_info(int item_no) {
		List<PayingDTO> Payinglist = sqlSession.selectList("p.payitem", item_no);
		System.out.println("Payinglist3="+Payinglist);
		return Payinglist;
	}
}
