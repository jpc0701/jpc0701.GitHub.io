public class ChickenFactory implements Factory {
    @Override
    public Fowl createFowl() {
        return new Chicken();
    }
}
