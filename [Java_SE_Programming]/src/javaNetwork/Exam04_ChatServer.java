package javaNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam04_ChatServer extends Application {
	
	TextArea textarea;          // 메시지 창.
	Button startBtn, stopBtn;   // 서버시작, 서버종료버튼
	ServerSocket server;        // 클라이언트의 접속을 받아들이는 놈.
	ExecutorService executorService = 
			Executors.newCachedThreadPool();  // ThreadPool
	// singleton형태의 공유객체를 생성
	SharedObject sharedObject = new SharedObject();
	
	
	private void printMsg(String msg) {
		Platform.runLater(()-> {
			textarea.appendText(msg + "\n");
		});
	}
	
	// 클라이언트와 연결된 Thread가 사용하는 공유객체를 만들기 위한
	// 클래스를 선언
	// inner class 형태로 선언. ( 사용하기 편해요!! )
	// Thread의 공유객체는 Thread가 가지고 있어야 하는 자료구조,
	// 기능을 구현해 놓은 객체를 지칭해요!
	class SharedObject {
		// 클라이언트 Thread를 저장하고 있어야 해요!
		List<ClientRunnable> clients = 
				new ArrayList<ClientRunnable>();
		 
		// 우리가 필요한 기능은.. Broadcast예요.
		// Thread가 클라이언트로부터 데이터를 받아서 모든 클라이언트 Thread\
		// 에게 데이터를 전달하는 기능을 구현해요!
		// 공유객체의 method는 여러 Thread에 의해서 동시에 사용될 수 있어요.
		// 이런경우에는 동기화 처리를 해 줘야지 문제없이 출력될수 있어요.
		public synchronized void broadcast(String msg) {
			clients.stream().forEach(t->{
				t.out.println(msg);
				t.out.flush();
			});		
		}
	}
	
	// 클라이언트와 매핑되는 Thread를 만들기위한 Runnable class.
	class ClientRunnable implements Runnable {
	
		private SharedObject sharedObject; // 공유객체
		private Socket socket;   // 클라이언트와 연결된 socket
		private BufferedReader br;
		private PrintWriter out;
		
		// 클라이언트가 서버에 접속해서 해당 클라이언트에 대한 Thread가
		// 생성될 때 Thread에는 2개의 객체가 전달되어야 해요!!
		// 생성자를 2개의 인자(공유객체와 소켓)를 받는 형태로 작성
		// 일반적으로 생성자는 필드초기화를 담당하기 때문에 여기에서 Stream을
		// 생성
		public ClientRunnable(SharedObject sharedObject, 
				Socket socket) {
			super();
			this.sharedObject = sharedObject;
			this.socket = socket;
			try {
				this.br = 
						new BufferedReader(
								new InputStreamReader(
										socket.getInputStream()));
				this.out = 
						new PrintWriter(socket.getOutputStream());
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		// 클라이언트의 Thread가 시작되면 run method()가 호출되서 
		// 클라이언트와 데이터 통신을 시작
		// 반복적으로 클라이언트의 데이터를 받아서 공유객체를 이용해서 
		// broadcasting
		@Override
		public void run() {
			String msg = "";
			try {
				while((msg = br.readLine()) != null) {
					// 클라이언트가 채팅을 종료하면
					if(msg.equals("/EXIT/")) {
						break;
					}
					// 일반적인 채팅 메시지인경우 모든 클라이언트에게 전달
					sharedObject.broadcast(msg);	
				}
					
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// 화면구성해서 window 띄우는 코드
		// 화면기본 layout을 설정 => 화면을 동서남북중앙(5개의 영역)으로 분리
		BorderPane root = new BorderPane();
		// BorderPane의 크기를 설정 => 화면에 띄우는 window의 크기 설정
		root.setPrefSize(700, 500);
		
		// Component생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);
		
		startBtn = new Button("채팅 서버 시작");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			textarea.clear();
			printMsg("[채팅서버기동 - 6789]");
			// 서버소켓을 만들어서 클라이언트 접속을 기다려야 해요!
			// JavaFX thread가 blocking되지 않도록 새로운 Thread를
			// 만들어서 클라이언트 접속을 기다려야 해요!
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						server = new ServerSocket(6789);
						while(true) {
							printMsg("[클라이언트 접속 대기중!!]");
							Socket socket = server.accept();
							printMsg("[클라이언트 접속 성공!!]");
							// 클라이언트가 접속했으니 Thread를 하나
							// 생성하고 실행해야 해요!
							ClientRunnable cRunnable = 
									new ClientRunnable(sharedObject,socket);
							// 새로운 클라이언트가 접속되었으니 
							// 공용객체의 List안에 들어가야겠죠?
							sharedObject.clients.add(cRunnable);
							printMsg("[현재 클라이언트 수 : " + 
									sharedObject.clients.size() + "]");
							executorService.execute(cRunnable);
						}
					} catch (Exception e) {
						System.out.println(e);	
					}
				}
			};
			executorService.execute(runnable);
		});

		stopBtn = new Button("채팅 서버 종료");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
		});
		
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		// flowpane에 버튼을 올려요!
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);
		root.setBottom(flowpane);
		
		// Scene객체가 필요해요.
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("방 1개짜리 채팅입니다.!!");
		primaryStage.show();
	}

	public static void main(String[] args) {		
		launch();
	}

}
