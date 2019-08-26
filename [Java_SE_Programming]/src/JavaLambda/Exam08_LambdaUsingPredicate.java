package JavaLambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/*
 *  Predicate는 입력매개변수가 있음. boolean 리턴
 *  사용되는 method는 textXXX()가 사용
 *  입력매개변수값을 조사하여 true, false값을 리턴해야하는 경우
 *  
 *  학생객체를 만들어서 List로 유지
 *  static method를 만들어서 람다식으로 인자를 넘겨줌
 *  성별에 따른 특정 과목의 평균을 구할 수 있도록 method를 작성
 *  
 */
class Exam08_Student{
	private String name; // 학생이름
	private int kor;  // 국어성적
	private int eng;  // 영어성적
	private int math; // 수학성적
	private String gender; // 성별
	
	public Exam08_Student() {
		
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

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Exam08_Student(String name, int kor, int eng, int math, String gender) {
		super();
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.gender = gender;
	}
}



public class Exam08_LambdaUsingPredicate {

	private static List<Exam08_Student> students =
			Arrays.asList(new Exam08_Student("홍길동",10,20,30,"남자"),
					new Exam08_Student("박길동",20,90,60,"남자"),
					new Exam08_Student("신사임당",30,30,90,"여자"),
					new Exam08_Student("유관순",80,80,100,"여자"),
					new Exam08_Student("이순신",30,10,10,"남자"));
	
	// static method를 하나 정의하는데 성별에 따른 특정 과목의 평균을 구하는 작업
	
	private static double avg(Predicate<Exam08_Student> predicate,
			ToIntFunction<Exam08_Student> function) {
		int sum = 0;
		int count = 0;
		
		for(Exam08_Student s : students) {
			if(predicate.test(s)) {
				count++;
				sum += function.applyAsInt(s);
			}
		}
		return (double)sum / count;
		
	}
	

	public static void main(String[] args) {
		
		System.out.println(avg(t->t.getGender().equals("남자"),t->t.getMath()));
		System.out.println(avg(t->t.getGender().equals("여자"),t->t.getEng()));
		
	}

}
