package lambda;

import java.util.Arrays;
import java.util.Comparator;

public class Test {
	public static void main(String[] args) {
		Person[] persons = {new Person(20, "张三"), new Person(18, "李四"), new Person(26, "王五"), new Person(30, "赵六")};
//		Arrays.sort(persons, new Comparator<Person>() {
//			@Override
//			public int compare(Person o1, Person o2) {
//				return o1.getAge() - o2.getAge();
//			}
//		});
		Comparator<Person> comparator = (o1, o2) -> o1.getAge() - o2.getAge();
		Arrays.sort(persons, (Person o1, Person o2) -> {return o1.getAge() - o2.getAge();});
		for (Person person : persons) 
			System.out.println(person.toString());
	}
}

class MyComparator implements Comparator<Person> {
	@Override
	public int compare(Person o1, Person o2) {
		return o1.getAge() - o2.getAge();
	}
}

class Person {
	private int age;
	private String name;
	
	public Person(int age, String name) {
		this.age = age;
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return String.format("%s %d", this.name, this.age);
	}
}
