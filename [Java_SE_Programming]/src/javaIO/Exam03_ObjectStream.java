package javaIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Exam03_ObjectStream {

	public static void main(String[] args) {
		
		Map<String,String> map = 
				new HashMap<String,String>();
		
		map.put("1", "홍길동");
		map.put("2", "강감찬");
		map.put("3", "신사임당");
		map.put("4", "최길동");
		
		// 일단 객체가 저장될 파일에 대한 File객체부터 만들기
		// 해당 파일이 존재하는지 그렇지 않은지는 상관 ㄴ
		
		File file = new File("asset/objectStream.txt");
		
		// 객체가 저장될 File에 outputStrem부터 열자
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(map);
			oos.close();
			fos.close();
			
			// 객체가 저장된 파일을 open해서 해당 객체를 프로그램으로 읽음
			// 파일에서 데이터를 읽기위해 inputstream필요
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			Object obj = ois.readObject();
			HashMap<String, String> result = null;
			if(obj instanceof Map<?,?>) {
				result = (HashMap<String, String>)obj;
				
			}
			System.out.println(result.get("3"));
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
				
	}
	
}
