public class MyFactory implements AbstractFactory {
    @Override
    public Fowl createFowl(String name) {
        Fowl fowl = null;
        if (name.equals("chicken")) fowl = new Chicken();
        else if (name.equals("duck")) fowl = new Duck();
        else if (name.equals("goose")) fowl = new Goose();
        return fowl;
    }

    @Override
    public Livestock createLivestock(String name) {
        Livestock livestock = null;
        if (name.equals("pig")) livestock = new Pig();
        else if (name.equals("cattle")) livestock = new Cattle();
        return livestock;
    }
}
