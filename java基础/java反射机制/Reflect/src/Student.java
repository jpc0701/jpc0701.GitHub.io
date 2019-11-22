public class Student {
    private String name1;
    private static final String name2 = "name2";
    protected String name3;
    public String name4;
    public int age;

    public Student() {
        System.out.println("调用了公有无参构造函数");
    }

    public Student(String name1, String name3, String name4, int age) {
        this.name1 = name1;
        this.name3 = name3;
        this.name4 = name4;
        this.age = age;
        System.out.println("调用了公有构造函数");
    }

    private Student(String name1, String name3, String name4) {
        this.name1 = name1;
        this.name3 = name3;
        this.name4 = name4;
        System.out.println("调用了私有构造函数");
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


}
