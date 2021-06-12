import java.util.ArrayList;

 class Stopka{
    private ArrayList<Card> lst;
    public Stopka() {
        lst = new ArrayList<Card>();
    }
    public Card get(int nom) {
        return lst.get(nom);
    }
    public void add(Card elem) {
        lst.add(elem);
    }
    public void remove(int nom) {
        lst.remove(nom);
    }
    public int size() {
        return lst.size();
    }
    public void clear() {
        lst.clear();
    }
}