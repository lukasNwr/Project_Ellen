package sk.tuke.kpi.oop.game.Memento;

import java.util.ArrayList;

public class Caretaker {

    private ArrayList<Memento> mementos;

    public Caretaker() {
        this.mementos = new ArrayList<>();
    }

    public void addMemento(Memento m) {
        mementos.add(m);
    }

    public Memento getMemento() {
        return mementos.get(0);
    }
}
