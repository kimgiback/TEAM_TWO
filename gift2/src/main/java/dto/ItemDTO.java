package dto;

import lombok.Data;

@Data
public class ItemDTO {

	private int item_no;
	
	//���� cate
	private int category_no;
	
	//���� business
	private int bu_no;
	
	private String item_name;
	
	private int item_price;
	
	private String item_info;
	
	private int item_stock;
	
	private String brand;
	
	private int readhit;
	
	//image file
//	private MultipartFile item_image;
	private String item_image;
	
}