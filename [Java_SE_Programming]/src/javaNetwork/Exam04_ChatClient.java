package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam04_ChatClient extends Application {

	TextArea textarea;
	Button connBtn,disConnBtn;
	
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	TextField idTf,msgTf;
	// 클라이언트쪽 Thread는 1개만 만들어짐 ThreadPool을 사용할 경우
	// overhead발생.
	
	ExecutorService executorService =
			Executors.newCachedThreadPool();
	
	
	private void printMsg(String msg) {
		Platform.runLater(() -> {
			textarea.appendText(msg + "\n");
		});
	}
	
	// 서버로부터 들어오는 메시지를 계속 받아서 화면에 출력하기 위한 용도의 Thread
	class RecevieRunnable implements Runnable{
		// 서버로 부터 들어오는 메시지를 받아들이는 역할을 수행
		// 소켓에 대한 입력스트림만 있음 됨
		
		private BufferedReader br; 
		
		public RecevieRunnable(BufferedReader br) {
			super();
			this.br = br;
		}

		@Override
		public void run() {
			String line = "";
			
			try {
				while((line = br.readLine()) != null) {
					printMsg(line);
				}
			} catch (Exception e) {
			}
		}
		
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// 화면구성해서 window 띄우는 코드
		// 화면 기본 layout 설정=>화면을 동서남북중앙(5개 영역)으로 분리
		BorderPane root = new BorderPane();
		// BorderPane의 크기를 설정=>화면에 띄우는 window크기설정
		root.setPrefSize(700, 500);

		// Component 생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);

		connBtn = new Button("채팅 서버  접속");
		connBtn.setPrefSize(250, 50);
		connBtn.setOnAction(t -> {

			try {				
				socket = new Socket("70.12.115.80",6789);				
				InputStreamReader isr = new InputStreamReader(socket.getInputStream());
				 br = new BufferedReader(isr);
				 out = new PrintWriter(socket.getOutputStream());
				printMsg("Echo  서버 접속 성공!!");
				
				// 접속을 성공했으니 이제 Thead를 만들어서 서버가 보내준
				// 데이터를 받을 준비
				RecevieRunnable runnable = new
						RecevieRunnable(br);
				executorService.execute(runnable);

			} catch (Exception e) {
				System.out.println(e);
			}

		});
		
		disConnBtn = new Button("접속 종료");
		disConnBtn.setPrefSize(250, 50);
		disConnBtn.setOnAction(t -> {

			try {				
				out.println("/EXIT/");
				out.flush();

			} catch (Exception e) {
				System.out.println(e);
			}

		});
		
		
		idTf = new TextField();
		idTf.setPrefSize(100, 40);
		
		msgTf = new TextField();
		msgTf.setPrefSize(200,40);
		msgTf.setOnAction(t->{
			//입력상자에서 enter key가 입력되면 호출
			String msg = idTf.getText() + ">" +msgTf.getText();
		
			out.println(msg);//서버로 문자열 전송!
			out.flush();
			msgTf.setText("");
		});

		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(connBtn);
		flowpane.getChildren().add(disConnBtn);
		flowpane.getChildren().add(idTf);
		flowpane.getChildren().add(msgTf);
		root.setBottom(flowpane);

		// Scene객체 필요
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread  예제");
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(); // start method 시작

	}

}
