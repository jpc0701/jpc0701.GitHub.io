# 举个栗子

我们在讨论lambda表达式之前先看个例子。

现在有个类`Person`，并且我们创建了数组，要求时根据这些人的年龄排序，我们可以用`Arrays`类的`sort`方法进行排序，但是要求需要传入一个比较器，而比较器的具体内容是要我们自己实现的。现在我们可以写成这样：

```java
import java.util.Arrays;
import java.util.Comparator;

public class Test {
	public static void main(String[] args) {
		Person[] persons = {new Person(20, "张三"), new Person(18, "李四"), new Person(26, "王五"), new Person(30, "赵六")};
		Arrays.sort(persons, new MyComparator());
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
```

因为`Comparator`是个接口，所有我们要完成一个实现这个接口的类`MyComparator`。这样写虽然可以完成，但是有点臃肿，当比较方法不一样了还要写个实现`Comparator`接口的类，所以我们可以改成用匿名函数的写法：

```java
	public static void main(String[] args) {
		Person[] persons = {new Person(20, "张三"), new Person(18, "李四"), new Person(26, "王五"), new Person(30, "赵六")};
		Arrays.sort(persons, new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2) {
				return o1.getAge() - o2.getAge();
			}
		});
		for (Person person : persons) 
			System.out.println(person.toString());
	}
```

这样写我们就不需要再完成一个实现`Comparator`接口的类了，减少了代码量，也显得优雅。但是现在再看一下代码，其实起到作用的只有`return o1.getAge() - o2.getAge();`，那能不能再精简一下能，这时候就需要lambda表达式了。



# lambda表达式

## 什么是lambda表达式

什么是lambda表达式？lambda表达式就是一种匿名函数，我们先看一下上面的例子用lambda表达式怎么写：

```java
		Person[] persons = {new Person(20, "张三"), new Person(18, "李四"), new Person(26, "王五"), new Person(30, "赵六")};
		Arrays.sort(persons, (Person o1, Person o2) -> {return o1.getAge() - o2.getAge();});
		for (Person person : persons) 
			System.out.println(person.toString());
```

之前传入的比较器现在被`(Person o1, Person o2) -> {return o1.getAge() - o2.getAge();}`代替。我们先看一下为什么原本的用匿名类传参可以写成现在这种样子。

在java8中的新特性中，如果接口只有一个抽象接口则这个接口叫做函数式接口（我们创建函数式接口时可以用`@FunctionalInterface`进行注解，同时java中也提供了一些常用的函数式接口，如：`Runnable`、`Supplier<T>`等），就可以使用lambda表达式，而`Comparator`接口的源码中只有`compare`是抽象接口，因此`Comparator`接口就是函数式接口，所以一个比较器我们还可以写成这样：

```java
Comparator<Person> comparator = (Person o1, Person o2) -> {return o1.getAge() - o2.getAge();};
```

代码可以进行根据上下文进行判断，用lambda表达式写成的匿名函数就是接口中唯一一个抽象接口的实现，同时再创建一个实现函数式接口的具体类。

## lambda表达式的语法

在上面的示例中，`Comparator`接口中`compare`是抽象接口已经定义了是两个参数，所以在lambda表达式中是`(Person o1, Person o2)`，如果只有一个参数可以写成`(Person o1)`，若没有参数必须有括号`()`。

```java
(Person o1) -> {return o1.getAge();};
() -> {return 1;};
```

在函数式接口中的抽象方法中我们已经定义了入参的类型，因此可以吧声明类型去掉：

```java
Arrays.sort(persons, (o1, o2) -> {return o1.getAge() - o2.getAge();});
```

如果只有一个入参，把声明类型去掉后还可以吧括号去掉：

```java
o1 -> {return o1.getAge();};
```

说完入参我们再来看看代码块。若只有一个`return`语句，我们可以把`return`和`{}`都去掉：

```java
Arrays.sort(persons, (o1, o2) -> o1.getAge() - o2.getAge());
```

## lambda代码中的作用域

lambda代码块中的引用的外部变量必须是final修饰的常量。因为lambda代码有可能是多线程或者延时执行，如果引用了外部普通变量，当lambda代码中使用此变量时，此时变量的值已经改变或者，这样可能导致严重问题。

在lambda代码中变量名称不能与父级方法中的变量相同。

## 方法引用

在了解方法引用之前，来先看个例子：

```java
public class Test1 {
	public static void main(String[] args) {
		System.out.println(test(-10, x -> {
			if (x < 0) 
				return 0 - x;
			else 
				return x;
		}));
	}
	
	private static int test(int x, MyFunctionInterface functionalInterface) {
		return functionalInterface.operate(x);
	}
}

@FunctionalInterface
interface MyFunctionInterface {
	int operate(int x);
}
```

这段代码，创建了一个函数式接口`MyFunctionInterface`和一个静态方法`test`，在静态方法`test`中调用函数式接口中的`operate`方法处理入参，最后我们用lambda表达式实现了取绝对值的功能。不过在java的`Math`中也有取绝对值的方法`abs`，我们可以改写成：

```java
System.out.println(test(-10, x -> Math.abs(x)));
```

这时候可以用到lambda中的方法引用：

```java
System.out.println(test(-10, Math::abs));
```

从上面可以看出，其实就是把Math的abs方法作为函数式接口抽象接口的具体实现。

上面的例子中引用的是Math类的静态方法abs，方法引用主要分三种：

1. Class::staticMethod
2. object::instanceMethod
3. Class::instanceMethod

对于前两种引用很好理解，都可以等价于`x -> Class.staticMethod(x)`或者`x -> object.instanceMethod(x)`，而第三种引用等价于`(x, y) -> x.instanceMethod(y)`。
