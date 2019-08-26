package javaStream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/*
 * Stream은 Java 8에서 도입이 됨 주의해야할건 java.io안에 있는
 * Stream과는 다른 거
 * 사용 용도 : 컬렉션 처리를 위해서 사용
 *			컬렉션 안의 데이터를 반복 시키는 반복자의 역할을 하는게 stream
 *			예를 들어 ArrayList안에 학생 객체가 5개 있으면 그 5개를 하나씩 
 *			가져오는 역할을 수행 => 이렇게 가져온 데이터를 람다식을 이용해서 처리
 * 
 * 
 */
class Exam01_Student{
	private String name;
	private int kor;
	private int eng;
	public Exam01_Student(String name, int kor, int eng) {
		super();
		this.name = name;
		this.kor = kor;
		this.eng = eng;
	}
	
	public Exam01_Student() {
		super();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKor() {
		return kor;
	}
	public void setKor(int kor) {
		this.kor = kor;
	}
	public int getEng() {
		return eng;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	
}


public class Exam01_StreamBasic {

	private static List<String> myBuddy = 
			Arrays.asList("111","222","333","444");
	
	private static List<Exam01_Student> students =
			Arrays.asList(
					new Exam01_Student("홍길동",10,20),
					new Exam01_Student("최길동",60,30),
					new Exam01_Student("박길동",30,80),
					new Exam01_Student("이길동",90,10));
	
			
	public static void main(String[] args) {
		
		
		for(int i=0; i<myBuddy.size(); i++) {
			System.out.println(myBuddy.get(i));
		}
		// 사람이름을 출력
		// 첨자를 이용한 반복을 피하기 위해 Iteratior를 사용
		Iterator<String> iter = myBuddy.iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
		// 사람이름을 출력
		// 반복자가 필요 ㄴ
		// 병렬처리 ㅇ
		Consumer<String> consumer = t ->{
			System.out.println(t+","+Thread.currentThread().getName());
		};
		
		Stream<String> stream = myBuddy.parallelStream();
		stream.forEach(consumer);
		
		Stream<Exam01_Student> studentStream = students.stream();
		double avg =
		studentStream.mapToInt(t->t.getKor()).average().getAsDouble();
		System.out.println("국어 성적의 평균 : " +avg);

	}

}
