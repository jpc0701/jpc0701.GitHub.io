public class GooseFactory implements Factory {
    @Override
    public Fowl createFowl() {
        return new Goose();
    }
}
