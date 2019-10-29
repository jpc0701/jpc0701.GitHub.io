public class MyFactory {
    public static Fowl getFowl(String name) {
        Fowl fowl = null;
        if (name.equals("Chicken"))
            fowl = new Chicken();
        else if (name.equals("Duck"))
            fowl = new Duck();
        return fowl;
    }
}
