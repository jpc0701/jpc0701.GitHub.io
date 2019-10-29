public interface AbstractFactory {
    Fowl createFowl(String name);
    Livestock createLivestock(String name);
}
