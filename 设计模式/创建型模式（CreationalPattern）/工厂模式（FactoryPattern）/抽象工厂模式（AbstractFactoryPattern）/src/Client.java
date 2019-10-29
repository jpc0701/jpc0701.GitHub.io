public class Client {
    public static void main(String[] args) {
        AbstractFactory myFactory = new MyFactory();
        Chicken chicken = (Chicken) myFactory.createFowl("chicken");
        chicken.yell();
        Duck duck = (Duck) myFactory.createFowl("duck");
        duck.yell();
        Cattle cattle = (Cattle) myFactory.createLivestock("cattle");
        cattle.yell();
    }
}
