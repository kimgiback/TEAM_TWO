package dto;

import java.util.Date;

import lombok.Data;

@Data
public class QnaDTO {

	//member, busi ����
	private int m_idx;
	
	private int bu_no;
	
	//qna ����
	private int qna_no;
	
	private String qna_title;
	
	private String qna_content;
	
	private String qna_select;
	
	private Date regidate;
	
	private String term_check;
	
}
