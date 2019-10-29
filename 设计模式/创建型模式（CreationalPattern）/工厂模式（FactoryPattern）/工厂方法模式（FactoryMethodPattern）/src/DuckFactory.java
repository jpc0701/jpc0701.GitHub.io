public class DuckFactory implements Factory {
    @Override
    public Fowl createFowl() {
        return new Duck();
    }
}
