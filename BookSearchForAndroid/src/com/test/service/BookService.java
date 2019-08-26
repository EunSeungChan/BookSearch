package com.test.service;

import java.sql.Connection;
import java.util.List;

import com.test.dao.BookDAO;
import com.test.dto.BookVO;

// Service 객체를 만들기 위한 class
public class BookService {
	
	// Business Logic(Transaction)에 대한
	// method만 나온다
	// 하나의 Transaction(기능)당 1개의 method가 이용
	public List<String> getBooksTitle(String keyword) {
		Connection con = null;
		List<String> list = null;
		try {
			con = common.DBTemplate.getConnection();
			BookDAO dao = new BookDAO(con);
			list = dao.selectTitle(keyword);
			if(list != null) {
				con.commit();
			}else {
				
				con.rollback();
			}
			
		}catch (Exception e) {
			System.out.println(e);
		}finally {
			try {
				con.close();
			}catch (Exception e1) {
				System.out.println(e1);
			}
		}
		
 		
		
		return list;
	}

	public List<BookVO> getBooks(String keyword) {
		// 로직처리(DB처리를 포함해서)
		// Transaction : 작업의 최소단위
		Connection con = null;
		List<BookVO> list = null;
		try {
			con = common.DBTemplate.getConnection();
			BookDAO dao = new BookDAO(con);
			list = dao.select(keyword);
			// 얻어온 결과를 이용해서 transaction의 commit과 rollback을 판단
			if( list != null) {
				// transaction이 정상적으로 처리되었을 경우
				con.commit();
			}else {
				// transaction 처리에 오류가 없는 경우
				con.rollback();
				
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}finally {
			try {
				con.close();
			}catch (Exception e1) {
				System.out.println(e1);
			}
		}
		
		


		
		
		
		return list;
	}

}
