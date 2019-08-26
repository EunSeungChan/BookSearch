package javaIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

class MyClass implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	String name;   // 직렬화가 가능한 형태여야 함
	int kor;       // 직렬화가 가능
	
	// transient는 직렬화 대상에서 제외
	transient Socket socket;  // 직렬화 ㄴ
	
	
	public MyClass(String name, int kor) {
		super();
		this.name = name;
		this.kor = kor;
	}
	
	
	
}
public class Exam04_Serialization {

	public static void main(String[] args) {

		// ObjectOutStream을 이용해서 File에 내가 만든 class의 
		// instance를 저장
		// 1. 저장할 객체를 생성
		MyClass obj = new MyClass("홍길동", 70);
		// 2. 객체를 저장할 파일 객체를 생성
		File file = new File("asset/student.txt");
		try {
			// 3. 파일 객체를 이용해서 ObjectOutputStream을 생성
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			// 4. ObjectOutputStream을 이용해서 객체를 파일에 저장
			//    저장될 객체는 반드시 직렬화가 가능한 객체이여야함
			// 	    우리가 생성한 객체는 직렬화가 가능한 객체 ㄴ
			//    객체 직렬화가 가능하려면 어떻게?
			//    Serializable interface를 구현
			//    class의 필드가 모두 직렬화가 가능해야함
			
			
			
			oos.writeObject(obj);
			// 5. 저장이 끝나면 Stream을 close해 줘야 함
			oos.close();
			fos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
				
	}

}
