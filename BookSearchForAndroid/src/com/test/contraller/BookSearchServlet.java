package com.test.contraller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.dto.BookVO;
import com.test.service.BookService;

/**
 * Servlet implementation class BookSearchServlet
 */
@WebServlet("/search")
public class BookSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 입력받고
		String keyword = request.getParameter("search_keyword");
		
		// 2. 로직처리해서
		// 로직처리하기 위한 객체인 Service객체를 생성
		BookService service = new BookService();
		// Service객체를 생성한 후 실제 작업을 지시
		List<BookVO> result = service.getBooks(keyword);
		// 3. 결과데이터 출력
		// JSON으로 변형해서 클라이언트에게 전달,
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(result);
		response.setContentType("text/plain; charset=utf8"); 
		PrintWriter out  = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
