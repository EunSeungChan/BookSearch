package com.test.dto;

// VO, DTO라고 불리는 class
// 데이터를 조직화해서 저장하는 용도
// 어떤 field를 지정할것인가가 중요
// 사용하는 DB스키마를 기반으로 작성
// 사용하는 데이터가 도서 이미지, 도서제목, 도서저자, 도서 가격
// 도서 이미지에 대한 데이터즌 데이터베이스에 2가지 종류로 들어갈수 있음
// 

public class BookVO {

	private String bimgurl;  // 도서 이미지
	private String btitle;   // 도서 제목
	private String bauthor;  // 도서 저자
	private String bprice;   // 도서 가격
	
	
	// default constructor
	public BookVO() {
		
		// 실제로 사용할 constructor
		
		
	}


	public BookVO(String bimgurl, String btitle, String bauthor, String bprice) {
		super();
		this.bimgurl = bimgurl;
		this.btitle = btitle;
		this.bauthor = bauthor;
		this.bprice = bprice;
	}


	public String getBimgurl() {
		return bimgurl;
	}


	public void setBimgurl(String bimgurl) {
		this.bimgurl = bimgurl;
	}


	public String getBtitle() {
		return btitle;
	}


	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}


	public String getBauthor() {
		return bauthor;
	}


	public void setBauthor(String bauthor) {
		this.bauthor = bauthor;
	}


	public String getBprice() {
		return bprice;
	}


	public void setBprice(String bprice) {
		this.bprice = bprice;
	}
	

	
}
