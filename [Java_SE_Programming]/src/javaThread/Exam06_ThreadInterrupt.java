package javaThread;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam06_ThreadInterrupt extends Application{

	TextArea textarea;
	Button startBtn, stopBtn;
	Thread counterThread;
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// 화면구성해서 window 띄우는 코드
		// 화면 기본 layout을 설정 => 화면을 동서남북 중앙 (5개의 영역)으로 분리
		BorderPane root =  new BorderPane();
		// BorderPane의 크기를 설정 => 화면에 띄우는 window의 크기 설정
		root.setPrefSize(700, 500);
		
		// Component 생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);
		
		startBtn = new Button("Thread 시작");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			counterThread = new Thread(()-> {
				try {
					for(int i=0; i<10; i++) {
						Thread.sleep(1000);
						printMsg(i+":" + Thread.currentThread().getName());
					}
				} catch (Exception e) {
					// 만약 interrupt()가 걸려있는 상태에서 block상태로 진입하면
					// Exception을 내면서 catch문으로 이동
					System.out.println(e);
					printMsg("Thread가 종료");
				}
			});
			
			counterThread.start();
			
		});
		stopBtn = new Button("Thread 중지");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			counterThread.interrupt();  // method가 실행된다고 바로 
										// Thread가 종료되지 않음

			// interrupt() method가 호출된 Thread는 sleep()과 같이
			// block 상태에 들어가야지 interrupt를 시킬수 있음
		});
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		// flowpane에 버튼을 올림
		flowpane.getChildren().add(startBtn);		
		flowpane.getChildren().add(stopBtn);		
		root.setBottom(flowpane);
		
		// Scene 객체 필요
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread 예제 ^^");
		primaryStage.show();
	}

	public static void main(String[] args) {

		launch();
	}

}
