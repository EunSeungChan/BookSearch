package javaThread;

// 공용객체를 생성하기 위한 class 정의
class MyShared {
	public synchronized void printNum() {
		for(int i=0;i<10;i++) {
			try {
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName()+" : "+i);
				notify(); // 현재 wait()상태에 있는 Thread를 꺠워서 
						  // runnable상태로 전환
				wait();   // 자기가 가지고 있는 monitor객체를 놓고 
				          // 스스로 wait block에 들어감
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}

class Exam05_Runnable implements Runnable{
	MyShared obj;
	
	public Exam05_Runnable(MyShared obj) {
		this.obj=obj;
	}
	
	@Override
	public void run() {
		obj.printNum();
		
	}
}

public class Exam05_ThreadwaitNotify {

	public static void main(String[] args) {
		
		MyShared shared = new MyShared();
		
		Thread t1 = new Thread(new Exam05_Runnable(shared));
		Thread t2 = new Thread(new Exam05_Runnable(shared));
		
		t1.start();
		t2.start();
	}

}
