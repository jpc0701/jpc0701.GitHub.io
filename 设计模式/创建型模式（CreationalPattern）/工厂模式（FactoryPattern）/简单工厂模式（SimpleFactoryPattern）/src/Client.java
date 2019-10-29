public class Client {
    public static void main(String[] args) {
        Chicken chicken = (Chicken) MyFactory.getFowl("Chicken");
        chicken.yell();
        Duck duck = (Duck) MyFactory.getFowl("Duck");
        duck.yell();
    }
}
