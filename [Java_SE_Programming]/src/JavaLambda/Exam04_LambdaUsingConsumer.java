package JavaLambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.ObjIntConsumer;

/*
 * 람다식은 추상메소드가 1개인 인터페이스의 객체를 생성하는
 * 표현식  => 이떄 사용하는 인터페이스를 우리가 직접 만들어서 사용하는가?
 * ==> 그렇지 않다 람다식이 대입되는 target type은
 * 일반적으로 Java가 제공하는 API를 이용,
 * 대표적인게 Runnable, Event처리 interface를 람다의
 * target type으로 사용 
 * 
 * Java에서는 람다의 target type으로 사용할 수 있는
 * interface를 여러개 만들어서 우리에게 package형태로 제공
 * (java.util.function package)
 * 제공되는 interface는 총 5 가지 종류로 분류할 수 있음
 * Consumer, Supplier, Function, Operation,
 * Predicate 총 5 가지 분류가 존재,
 * 
 * Consumer : 함수적 인터페이스 ( 람다식이 대입될 수 있는 target type으로 
 * 							 사용할 수 있는 interface를 지칭)
 * 
 * Consumer는 Java가 우리에게 제공하는 ineterface이고 추상 메소드를 단
 * 1개만 가지고 있다. accept() 라는 method를 제공.
 * 값을 소비만 하는 역할을 담당. accept()라는 함수의 리턴 타입은 void
 * 
 */



public class Exam04_LambdaUsingConsumer {
	// method를 하나 정의하는데 static으로 정의 , 편하게 쓸려고
	public static List<String> names = Arrays.asList("홍길동",
			"김길동","최길동","박길동");
			
	public static void printName(Consumer<String> consumer) {
		for(String name : names) {
			consumer.accept(name);
		}
		
	}

	// 일반적인 method호출은 사용하는 data가 인자로 전달되는 형태,
	// 람다식을 사용하면 method를 호출할 떄 data가 아니라 실행 코드를
	// 넘겨줄수 있음
	
	public static void main(String[] args) {
		
		printName(t->{System.out.println(t);});
		
		Consumer<String> consumer = t -> {
			System.out.println(t);
		};
			
		consumer.accept("흐흐흐흐흐");
		
		BiConsumer<String, String> biConsumer = (a,b) -> {
			System.out.println(a+b);
		};
		biConsumer.accept("흐흐흐흐흐", "ㅎㅎㅎ");
		
		IntConsumer intConsumer = i -> System.out.println(i);
		intConsumer.accept(100);
		
		ObjIntConsumer<String> objIntConsumer = (a,b) ->{
			System.out.println(a+b);
		};
		objIntConsumer.accept("Hello", 100);
	}
	
}
