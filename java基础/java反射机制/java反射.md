# java反射机制

## java反射能做什么？

好多文章和教程在讲解java反射的时候往往都是上来就说java反射机制是什么，
这次我们先来看看java发射机制能做什么，或者说为什么要设计出java的反射机制。

我们先来看一看这个场景：

程序员小A在做一个项目，写了一个类`MyObject`，其中有个方法`myMethod`，在项目中某个地方要调用`MyObject`中的`myMethod`。
那么他就要在这个地方先通过`new`方法创建`MyObject`的实例`myObject`，再调用`myMethod`。
突然有一天，需求变了，需要在此处调用`MyObject1`的`myMethod1`方法。

这时候程序员小A需要在此处删除或者注释掉原来的代码，创建`MyObject1`的实例并调用`myMethod1`方法，然后Git代码，重新部署。

这时候程序员老B的写法就和小A不一样了。他先会写个配置文件，内容如下：

```properties
properties.class = MyObject
properties.method = MyMethod
```

当需求变更时，只需要在配置文件中更改类的名字和方法的名字。

这时候就一个疑问了，那老B在代码中是怎样实例化创建对象的，这时候就要用到java的反射机制，我们不需要在代码中使用`new`方法创建对象，只需要知道类的名字就可以创建对象。

## java反射机制是什么

```text
JAVA反射机制是在运行状态中，对于任意一个实体类，都能够知道这个类的所有属性和方法；
对于任意一个对象，都能够调用它的任意方法和属性；
这种动态获取信息以及动态调用对象方法的功能称为java语言的反射机制。
                                                                      ——百度百科
```
上面这句话最重要的两个字是`动态`，我们可以很灵活的获取某个类的相关信息，而且还可以创建对象并调用方法。

## 举个栗子

我们先来创建一个类`Student`

```java
public class Student {
    private String name1;
    private static final String name2 = "name2";
    protected String name3;
    public String name4;
    public int age;

    public Student() {}

    public Student(String name1, String name3, String name4, int age) {
        this.name1 = name1;
        this.name3 = name3;
        this.name4 = name4;
        this.age = age;
    }

    private Student(String name1, String name3, String name4) {
        this.name1 = name1;
        this.name3 = name3;
        this.name4 = name4;
    }

    public void printName1() {
        System.out.println(this.name1);
    }

    private void printName3() {
        System.out.println(this.name3);
    }

    public int getAge() {
        return this.age;
    }

    public void printSlogan(String slogan) {
        System.out.println(slogan);
    }

    private void printSex(String sex) {
        System.out.println(sex);
    }
}
```
我们目前在代码中是怎样使用这个`Student`类的呢？`Student student = new Student();`

### Class类

我们在使用某个类时，jvm都会创建一个关于这个类的`Class`类的对象，每个类只有一个`Class`对象，就算创建多个`Student`的对象，`Student`类的`Class`对象也只有一个。

是不是有点晕。首先`Class`是一个类，它的名字叫Class，这只是个类名。这个类有什么用呢？
这个类用于记录加载到JVM中实体类的相关信息，JVM中的每个实体类都只有一个`Class`类的对象。

我们可以用以下三种方法获取某个类的`Class`对象：
```java
public class Main {
    public static void main(String[] args) {
        //第一种方式获取Class对象
        Student student = new Student();//这一new 产生一个Student对象，一个Class对象。
        Class stuClass1 = student.getClass();//获取Class对象
        System.out.println(stuClass1.getName());

        //第二种方式获取Class对象
        Class stuClass2 = Student.class;
        System.out.println(stuClass1 == stuClass2);//判断第一种方式获取的Class对象和第二种方式获取的是否是同一个

        //第三种方式获取Class对象
        try {
            Class stuClass3 = Class.forName("Student");//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名
            System.out.println(stuClass3 == stuClass2);//判断三种方式是否获取的是同一个Class对象
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```
输出结果：
```text
Student
true
true
```
以上三种方法中，最有用的还是方法三。在方法一和二中，我们都已经在代码中引入`Student`类了，还需要`Class`对象获取它的相关信息吗？
方法三中，我们只要知道实体类的名字就可以获取它的所有信息，甚至可以实例化创建对象。

已经介绍了java反射机制中的`Class`类了，接下来介绍三个重要的类：`Constructor`类、`Method`类和`Field`类。

### Constructor类

我们已经知道怎样获取一个类的Class对象了，那么就可以通过这个对象获取这个类的构造函数等信息，这就需要Constructor类了。

```java
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("获取Class对象");
        Class stuClass = Class.forName("Student");
        System.out.println("获取所有公有构造函数");
        Constructor[] constructors = stuClass.getConstructors();
        for (Constructor constructor : constructors)
            System.out.println(constructor);
        System.out.println("获取所有构造函数");
        constructors = stuClass.getDeclaredConstructors();
        for (Constructor constructor : constructors)
            System.out.println(constructor);
        System.out.println("调用公有无参构造函数");
        Constructor constructor = stuClass.getConstructor();
        Student student = (Student) constructor.newInstance();
        System.out.println("调用私有构造函数");
        constructor = stuClass.getDeclaredConstructor(String.class, String.class, String.class);
        constructor.setAccessible(true);  //设置权限可以访问私有构造函数
        student = (Student) constructor.newInstance("name1", "name3", "name4");
    }
}

```
通过Class对象我们可以访问任意一个构造函数。

### Method类

```java
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("获取Class对象");
        Class stuClass = Class.forName("Student");
        System.out.println("通过反射创建Student对象");
        Student student = (Student) stuClass.getConstructor(String.class, String.class, String.class, int.class).newInstance("name1", "name3", "name4", 18);
        System.out.println("获取方法");
        Method[] methods = stuClass.getMethods();
        for (Method method : methods)
            System.out.println(method);
        System.out.println("获取所有方法");
        methods = stuClass.getDeclaredMethods();
        for (Method method : methods)
            System.out.println(method);
        System.out.println("调用公有方法");
        Method method = stuClass.getMethod("printName1");
        method.invoke(student);
        System.out.println("调用有返回值的方法");
        method = stuClass.getMethod("getAge");
        System.out.println(method.invoke(student));
        System.out.println("调用私有方法");
        method = stuClass.getDeclaredMethod("printName3");
        method.setAccessible(true);
        method.invoke(student);
        System.out.println("调用有参方法");
        method = stuClass.getMethod("printSlogan",  String.class);
        method.invoke(student, "调用了有参方法");
    }
}
```
除了可以获取对象的方法外，也可以获取静态方法，当然也包括main方法

### Field类
```java
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("获取Class对象");
        Class stuClass = Class.forName("Student");
        System.out.println("通过反射创建Student对象");
        Student student = (Student) stuClass.getConstructor(String.class, String.class, String.class, int.class).newInstance("name1", "name3", "name4", 18);
        System.out.println("获取公有变量");
        Field[] fields = stuClass.getFields();
        for (Field field : fields)
            System.out.println(field);
        System.out.println("获取所有变量");
        fields = stuClass.getDeclaredFields();
        for (Field field : fields)
            System.out.println(field);
        System.out.println("设置获取公有变量");
        Field field = stuClass.getField("name4");
        field.set(student, "name4-4");
        System.out.println(field.get(student));
        System.out.println("设置获取私有变量");
        field = stuClass.getDeclaredField("name1");
        field.setAccessible(true);
        field.set(student, "name1-1");
        System.out.println(field.get(student));
    }
}
```
## 总结

通过栗子我们可以看到java的反射机制有以下特点：
* 在运行时判断任意一个对象所属的类。
* 在运行时构造任意一个类的对象。
* 在运行时判断任意一个类所具有的成员变量和方法。
* 在运行时调用任意一个对象的方法。
* 生成动态代理。
java反射机制的作用不仅仅局限于文章开头讲到的场景，主要适用于各种框架的开发，比如Spring等。

java反射机制给框架设计带来灵活性，但它还是存在缺点：
* 性能首先，利用反射获取对象比直接写在代码中要慢很多
* 内部私有方法或者变量可以通过反射访问，造成了内部数据的暴露，会有安全问题
* 反射的滥用会导致程序内部逻辑模糊
