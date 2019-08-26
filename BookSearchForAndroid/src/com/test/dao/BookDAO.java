package com.test.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.test.dto.BookVO;



public class BookDAO {

	private Connection con;
	
	public BookDAO(Connection con) {
		this.con =con;
	}
	
	
	public List<String> selectTitle(String keyword) {
		// keyword를 입력받아서 Database검색해서
		// String[] 만들어서 return 해준 DB처리
		// JDBC를 이용한 DB처리
		List<String> list = new ArrayList<String>();
		try {
			
			
			// 상당히 로드가 많이 걸리는 작업
			// ConnectionPool을 사용하는 코드로 재 작성
			// Appache Tomcat은 DBCP라는 Connection Pool기능을 제공
			// DBCP는 JNDI을 이용
			
			
			
			// 3. statement 작성
			String sql = "select btitle from book where btitle like ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			// 4. Query 실행
			ResultSet rs = pstmt.executeQuery();
			// 5. 결과처리
			while(rs.next()) {
				list.add(rs.getString("btitle"));
			}
			// 6. 사용한 resource 해제
			
		}catch(Exception e) {
			System.out.println(e);
		}
		return list;
	}
	public List<BookVO> select(String keyword){
		List<BookVO> list = new ArrayList<BookVO>();
		try {
			String sql = "select bimgurl, btitle, bauthor, bprice from book where btitle like ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, "%" + keyword + "%");
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			
			BookVO temp = new BookVO();
			temp.setBimgurl(rs.getString("bimgurl"));
			temp.setBtitle(rs.getString("btitle"));
			temp.setBauthor(rs.getString("bauthor"));
			temp.setBprice(rs.getString("bprice"));
			list.add(temp);
		}
		
		}catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}

}
