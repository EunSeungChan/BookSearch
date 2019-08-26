package javaThread;

/*
 * 1부터 100까지 숫자의 합을 구하기
 * 1~10까지 1개의 Thread가 합을 계산해서 결과 반환
 * 11~20까지 1개의 Thread가 합을 계산해서 결과 반환
 * ...
 * 91~100까지 1개의 Thread가 합을 계산해서 결과 반환
 * =>Thread Pool 이용해야 하고, Callable을 이용해서 return값을 받아야함.
 * =>10개의 Thread로 부터 각각 데이터를 받아들이는 Thread 필요.
 * 
 * 
 * 
 * 
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam10_ThreadCompleteService extends Application{

	TextArea textarea;
	Button initBtn, startBtn,stopBtn;
	//initBtn: 쓰레드풀 생성하는 버튼
	//startBtn: 쓰레드풀을 이용해서 쓰레드를 실행시키는 버튼
	//stopBtn:쓰레드풀 종료하는 버튼
	ExecutorService executorService;
	//executorService: Thread pool
	ExecutorCompletionService<Integer> executorCompletionService;
	private int total=0;
	
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg+"\n");
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
		
		initBtn = new Button("Thread Pool 생성");
		initBtn.setPrefSize(250, 50);
		initBtn.setOnAction(t->{
			//버튼에서 액션이 발생(클릭)했을 때 호출
			
			executorService = Executors.newCachedThreadPool();			
			executorCompletionService = 
					new ExecutorCompletionService<Integer>(executorService);
			
			
			
			
		});
		
		stopBtn = new Button("Thread Pool 종료");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
			executorService.shutdown();
		});
		
		startBtn = new Button("Thread Pool 실행");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			for(int i=1; i<101; i=i+10) {
				final int k = i;
				
				Callable<Integer> callable = new Callable<Integer>() {

					@Override
					public Integer call() throws Exception {	
						IntStream intStream = 
								IntStream.rangeClosed(k,k+9);
						int sum = intStream.sum();
						
						return sum;
					}
					
					
				};
				
				executorCompletionService.submit(callable);
			}
			
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					for(int i=0; i<10; i++) {
						try {
							Future<Integer> future =
									
									executorCompletionService.take();
							total += future.get();
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
					}
					printMsg("최종 결과값은 :" + total);
				}
			};
			executorService.execute(runnable);
			
		});
		
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700,50);
		flowpane.getChildren().add(initBtn);
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
