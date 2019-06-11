package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class Backpack<T extends Collectible>  implements ActorContainer<Collectible> {
    private String name;
    private int capacity;
    private List<Collectible> items;

    public Backpack(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }


    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return items.iterator();
    }

    @Override
    public void add(Collectible actor) {
            if (items.size() < capacity) {
                items.add(actor);
            }
            else throw new IllegalStateException(this.name + " is full");

    }

    @Nullable
    @Override
    public Collectible peek() {
        if(items.isEmpty()) return null;
        return items.get(items.size()-1);
    }


    @Override
    public void shift() {
        Collections.rotate(items, 1);
    }

    @Override
    public void remove(Collectible actor) {
       items.remove(actor);
    }

    @NotNull
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @NotNull
    @Override
    public List<Collectible> getContent() {
        return new ArrayList<>(items);
    }
}
