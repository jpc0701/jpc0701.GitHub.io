public class Client {
    public static void main(String[] args) {
        Factory chickenFactory = new ChickenFactory();
        Chicken chicken = (Chicken) chickenFactory.createFowl();
        chicken.yell();
        Factory duckFactory = new DuckFactory();
        Duck duck = (Duck) duckFactory.createFowl();
        duck.yell();
        Factory gooseFactory = new GooseFactory();
        Goose goose = (Goose) gooseFactory.createFowl();
        goose.yell();
    }
}
