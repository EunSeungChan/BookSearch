package javaNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Echo program을 작성
 * 클라이언트 프로그램으로부터 문자열을 네트워크를 통해 전달
 * 
 * 
 * 
 */



public class Exam02_EchoServer {

	public static void main(String[] args) {
		
		ServerSocket server = null;
		Socket socket = null;
		
		try {
			server = new ServerSocket(5557);
			System.out.println("서버프로그램 기동  - 5557");
			socket = server.accept();
			// Stream 생성
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			PrintWriter out =
					new PrintWriter(socket.getOutputStream());
			// br로 부터 데이터를 읽어서 out을 통해 다시 전달
			String msg = "";
			while(!((msg=br.readLine()).equals("/@EXIT"))) {
				out.println(msg);
				out.flush();
			}
			out.println(msg);
			out.flush();
			// 사용된 리소스를 해제
			out.close();
			br.close();
			socket.close();
			server.close();
			System.out.println("서버프로그램 종료");
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
}
