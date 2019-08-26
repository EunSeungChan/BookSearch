package JavaLambda;
/*
 * 람다식을 정의해서 사용할 때 주의해야 할 점이 몇가지 있음
 * 클래스의 멤버(필드 + 메소드)와 로컬변수(지역변수)의 사용에 약간의 제약이 있음
 * 
 * 특히 this keyword를 사용할 때 주의해야 함
 * this : 현재 사용되는 객체의 reference
 * 람다식은 익명 객체를 만들어 내는 코드, 람다식의 실행코드 내에서 
 * this keyword를 쓰면 익명 객체를 지칭하지 않음
 * 
 * 
 */
@FunctionalInterface
interface Exam03_LambdaIF {
	public void myFunc();
	
}
	
class OuterClass{
	//Field (기본적으로 class의 field는 private)
		public  int outerField = 100;
		public OuterClass() {
			// default 생성자
		System.out.println(this.getClass().getName());	
		}
		
		
		
		// class안에 다른 class를 정의
		class InnerClass{
			int innerField = 200;
			
			Exam03_LambdaIF fieldLambda = ()-> {
				System.out.println("outerField :" + outerField);
				System.out.println("OuterClass의 객체를 찾아요:" +
				OuterClass.this.outerField);
				System.out.println("innerField:" + innerField);
				System.out.println("this.innerFiled :" +
				                          this.innerField);
				System.out.println(this.getClass().getName());
			};  // Field
			
			public InnerClass() {
				System.out.println(this.getClass().getName());
			}
		}
		public void innerMethod() {	// method
			int localVal = 100;    	// 지역변수(local veriable)
									// 지역변수는 stack영역에 저장이 되고
									// method가 호출되면 생기고
									// method가 끝나면 없어짐
			Exam03_LambdaIF localLambda = () -> {
				System.out.println(localVal);
				// 
			};
			
			localLambda.myFunc();
		}
}

// 프로그램의 시작을 위한 dummy class로 사용
public class Exam03_LambdaUsingVariable {
			

	public static void main(String[] args) {
		// 람다식을 사용하려면 InnerClass의 interface가 존재해야 함
		// 그런데 하필이면 이 InnerClass가 inner class
		// inner class의 instance를 생성하려면 outer class의 instance부터
		// 생성
		OuterClass outer = new OuterClass(); 
		OuterClass.InnerClass inner = outer.new InnerClass();
		inner.fieldLambda.myFunc();
		
		
	}

}
