package javaThread;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam07_ThreadDaemon extends Application{

	TextArea textarea;
	Button btn;
	
	
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
		
		btn = new Button("버튼 클릭!!");
		btn.setPrefSize(250, 50);
		btn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// Thread를 생성(for loop를 1초 sleep하면서 10번 반복)
			Thread thread = new Thread(()->{
				try {
					for(int i=0; i<10; i++) {
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			
			});
			thread.setDaemon(true);  // 해당 Thread를 daemon thread로 설정
			
			// 자식 thread가 되는 것임
			// 부모 thread가 죽으면 같이 죽음
			thread.start();	
			
		});
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		// flowpane에 버튼을 올림
		flowpane.getChildren().add(btn);		
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
