package javaStream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
 * reduction
 * 
 * => 대량의 데이터를 가공해서 축소하는 개념
 * => sum,average,count,max,min
 * 
 * Collection을 사용할 때 Stream을 이용해서 이런  reduction작업을 
 * 쉽게 할 수 있음
 * 
 * 만약 Collection안에 reduction하기가 쉽지 않은 형태로 데이터가 들어가 있으면
 * 중간처리과정을 거쳐서 reduction하기 좋은 형태로 변환,
 * 
 * Stream은 pipeline을 지원(stream을 연결해서 사용할 수 있음)
 * 
 * 직원객체를 생성, ArrayList안에 여러명의 직원을 저장,
 * 이 직원중에 IT에 종사하고 남자인 직원을 추려서 연봉 평균을 출력
 * 
 */

class Exam03_Employee implements Comparable<Exam03_Employee>{
	private String name; // 이름
	private int age;     // 나이
	private String dept; // 부서
	private String gender; // 성별
	private int salary;   // 연봉
	
	@Override
	public boolean equals(Object obj) {
		// 만약 overriding을 하지 않으면 메모리 주소가지고 비교
		// overriding을 해서 특정 조건을 만족 
		
		boolean result =false;
		Exam03_Employee target = (Exam03_Employee)obj;
		if(this.getName().equals(target.getName())) {
			
		} else {
			result = false;
		}
		
		return result;
	}
	@Override
	public int compareTo(Exam03_Employee o) {
		// 정수값을 리턴.
		// 양수가 리턴되면 순서를 바꿈
		// 0이나 음수가 리턴되면 순서를 바꾸지 않음
		int result = 0;
		if(this.getSalary() > o.getSalary()) {
			result =1;
			
		}else if(this.getSalary() == o.getSalary()) {
			result = 0;
		} else {
			result = -1;
		}
		
		return result;
	}
	
	
	public Exam03_Employee() {
		super();
	}
	public Exam03_Employee(String name, int age, String dept, String gender, int salary) {
		super();
		this.name = name;
		this.age = age;
		this.dept = dept;
		this.gender = gender;
		this.salary = salary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	
}
	


public class Exam03_StreamPipeline {
	private static List<Exam03_Employee> employees =
			Arrays.asList(
					new Exam03_Employee("홍길동",20,"IT","남자",2000),
					new Exam03_Employee("김길동",30,"Sales","여자",3000),
					new Exam03_Employee("최길동",40,"IT","남자",1000),
					new Exam03_Employee("이순신",50,"Sales","남자",3500),
					new Exam03_Employee("유관순",35,"IT","여자",2000),
					new Exam03_Employee("신사임당",60,"IT","여자",4000),
					new Exam03_Employee("강감찬",30,"IT","남자",1000),
					new Exam03_Employee("이황",45,"Sales","남자",5000),
					new Exam03_Employee("홍길동",20,"IT","남자",2000));
	public static void main(String[] args) {
		// 부서가 IT인 사람들 중 남자에 대한 연봉 평균
		Stream<Exam03_Employee> stream = employees.stream();
		// stream의 중간처리와 최종처리를 이용
		// filter method는 결과값을 가지고 있는 stream을 리턴.
		/*
		 * double avg = stream.filter(t->t.getDept().equals("IT"))
		 * .filter(t->t.getGender().equals("남자")) .mapToInt(t->t.getSalary())
		 * .average().getAsDouble(); // lazy 처리 System.out.println("IT부서의 남자 평균 연봉 : " +
		 * avg );
		 */
		
		/*
		 * // 나이가 35 이상인 직원 중 남자 직원의 이름 stream.filter(t->(t.getAge() >= 35))
		 * .filter(t->t.getGender().equals("남자"))
		 * .forEach(t->System.out.println(t.getName()));
		 */
		
		// 중복제거를 위한 중간처리
		int temp[] = { 10,20,30,40,50,30,40};
		IntStream s = Arrays.stream(temp);
		s.distinct().forEach(t->System.out.println(t));
		
		// 객체에 대한 중복처리를 제거
		// VO안에서 equals() method를 overriding해서 처리
		
		/*
		 * employees.stream() .distinct() .forEach(t->System.out.println(t.getName()));
		 * // mapToInt() =>mapXXX() // 정렬 (부서가 IT인 사람을 연봉순으로 출력) employees.stream()
		 * .filter(t->t.getDept().equals("IT")) .sorted( Comparator.reverseOrder()) //
		 * 오름차순 정렬 .forEach(t->System.out.println(t.getName() + " , "+t.getSalary()));
		 */
		/*
		 * employees.stream() .filter(t->t.getDept().equals("IT"))
		 * .peek(t->System.out.println(t.getName())) .mapToInt(t->t.getSalary())
		 * .forEach(t->System.out.println(t));
		 */
	    // 반복
		// forEach()를 이용하면 스트림안의 요소를 반복 할 수 있음
		// forEach()는 최종처리 함수
/*		boolean result =employees.stream()
				 .filter(t->(t.getAge() >= 50))
				 .allMatch(t->(t.getAge()>55));
		System.out.println(result);*/
		
		// 최종 확인용 함수로 forEach를 많이 사용
		// forEach말고 collect()를 이용
		// 나이가 50살 이상인 사람들의 연봉을 구해서
		// List<Integer>형태의 ArrayList에 저장
		List<Integer> tmp = employees.stream()
				 .filter(t->(t.getAge() >= 50))
				 .map(t->t.getSalary())
				 .collect(Collectors.toList());
		System.out.println(tmp);
		
	}
}
