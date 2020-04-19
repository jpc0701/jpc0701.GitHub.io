import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws Exception {
//        //第一种方式获取Class对象
//        Student student = new Student();//这一new 产生一个Student对象，一个Class对象。
//        Class stuClass1 = student.getClass();//获取Class对象
//        System.out.println(stuClass1.getName());
//
//        //第二种方式获取Class对象
//        Class stuClass2 = Student.class;
//        System.out.println(stuClass1 == stuClass2);//判断第一种方式获取的Class对象和第二种方式获取的是否是同一个
//
//        //第三种方式获取Class对象
//        try {
//            Class stuClass3 = Class.forName("Student");//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名
//            System.out.println(stuClass3 == stuClass2);//判断三种方式是否获取的是同一个Class对象
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

//        System.out.println("获取Class对象");
//        Class stuClass = Class.forName("Student");
//        System.out.println("获取所有公有构造函数");
//        Constructor[] constructors = stuClass.getConstructors();
//        for (Constructor constructor : constructors)
//            System.out.println(constructor);
//        System.out.println("获取所有构造函数");
//        constructors = stuClass.getDeclaredConstructors();
//        for (Constructor constructor : constructors)
//            System.out.println(constructor);
//        System.out.println("调用公有无参构造函数");
//        Constructor constructor = stuClass.getConstructor();
//        Student student = (Student) constructor.newInstance();
//        System.out.println("调用私有构造函数");
//        constructor = stuClass.getDeclaredConstructor(String.class, String.class, String.class);
//        constructor.setAccessible(true);  //设置权限可以访问私有构造函数
//        student = (Student) constructor.newInstance("name1", "name3", "name4");

//        System.out.println("获取Class对象");
//        Class stuClass = Class.forName("Student");
//        System.out.println("通过反射创建Student对象");
//        Student student = (Student) stuClass.getConstructor(String.class, String.class, String.class, int.class).newInstance("name1", "name3", "name4", 18);
//        System.out.println("获取方法");
//        Method[] methods = stuClass.getMethods();
//        for (Method method : methods)
//            System.out.println(method);
//        System.out.println("获取所有方法");
//        methods = stuClass.getDeclaredMethods();
//        for (Method method : methods)
//            System.out.println(method);
//        System.out.println("调用公有方法");
//        Method method = stuClass.getMethod("printName1");
//        method.invoke(student);
//        System.out.println("调用有返回值的方法");
//        method = stuClass.getMethod("getAge");
//        System.out.println(method.invoke(student));
//        System.out.println("调用私有方法");
//        method = stuClass.getDeclaredMethod("printName3");
//        method.setAccessible(true);
//        method.invoke(student);
//        System.out.println("调用有参方法");
//        method = stuClass.getMethod("printSlogan",  String.class);
//        method.invoke(student, "调用了有参方法");

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
