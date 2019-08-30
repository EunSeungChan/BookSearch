package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.ws.handler.MessageContext.Scope;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

class EchoRunnable implements Runnable{
	// 가지고 있어야되는 필드
	Socket socket;
	BufferedReader br;    // 입력을 위한 스트림
	PrintWriter out;      // 출력을 위한 스트림
	
	
	
	public EchoRunnable(Socket socket) {
		super();
		this.socket = socket;
		try {
			this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	@Override
	public void run() {
		// 클라이언트와 echo처리 구현
		// 클라이언트가 문자열을 보내면 해당 문자열을 받아서 다시 클라이언트에게 
		// 전달, 한번하고 종료하는게 아니라 클라이언트가 "EXIT"라는 문자열을 
		// 보낼떄까지 지속,
		String line = "";
		try {
			while((line = br.readLine()) != null ) {
				if(line.equals("/EXIT/")) {
					break;
					
				} else {
					out.println(line);
					out.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}

public class Exam03_EchoServerMultiClient extends Application {
	
	
	TextArea textarea;					
	Button startBtn, stopBtn;          // 서버 시작 서버 중지
	// ThreadPool을 생성
	ExecutorService executorservice = Executors.newCachedThreadPool();
	
	ServerSocket server;
	
	
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg+"\n");
		});
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//화면구성해서 window 띄우는 코드
		//화면 기본 layout 설정=>화면을 동서남북중앙(5개 영역)으로 분리
		BorderPane root = new BorderPane();
		//BorderPane의 크기를 설정=>화면에 띄우는 window크기설정
		root.setPrefSize(700, 500);
		
		//Component 생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);
		
		startBtn = new Button("Echo서버 시작");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 떄 호출
			// 서버 프로그램을 시작
			// 클라이언트의 접속을 ㄱㄷ -> 접속이 되면 Thread를 하나 생성
			// -> Thread를 시작해서 클라이언트와 Thread가 통신
			// 서버는 다시 다른 클라이언트의 접속을 ㄱㄷ
			Runnable runnable = ()->{
				try {
					server = new ServerSocket(7777);
					printMsg("Echo 서버 기동");
					
					while(true) {
						printMsg("클라이언트 접속 대기");
						
						Socket s = server.accept();
						// 클라이언트가 접속했으니 Thread 만들고 시작
						EchoRunnable r = new EchoRunnable(s);
						executorservice.execute(r);
					}
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			};
			executorservice.execute(runnable);
			
			
			
			
		});
		stopBtn = new Button("Echo서버 종료");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
			
			
			
		});
		
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700,50);
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);
	
		root.setBottom(flowpane);
		
		//Scene객체 필요
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("다중 클라이언트 Echo Server");
		primaryStage.show();
		
		
	}

	public static void main(String[] args) {
		launch(); //start method 시작

	}

}
