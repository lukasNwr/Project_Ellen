package sk.tuke.kpi.oop.game.openables;


import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    private Animation animation;
    private boolean isOpen;
    private Orientation orientation;

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    public Door(String name, Orientation orientation) {
        super(name);
       isOpen = false;
       this.orientation = orientation;
       if(this.orientation == Orientation.VERTICAL) {
           animation = new Animation("sprites/vdoor.png", 16, 32, 0.1f);
           setAnimation(animation);
           animation.stop();
       }
       if(this.orientation == Orientation.HORIZONTAL) {
           animation = new Animation("sprites/hdoor.png", 32, 16, 0.1f);
           setAnimation(animation);
           animation.stop();
       }
    }


    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);


    @Override
    public void open() {
        animation.setPlayMode(Animation.PlayMode.ONCE_REVERSED);
        isOpen = true;
        this.getScene().getMap().getTile(this.getPosX()/16, this.getPosY()/16).setType(MapTile.Type.CLEAR);
        if(this.orientation == Orientation.VERTICAL) {
            this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.CLEAR);
        } else {
            this.getScene().getMap().getTile(this.getPosX()/16+1, this.getPosY()/16).setType(MapTile.Type.CLEAR);
        }
        this.getScene().getMessageBus().publish(DOOR_OPENED, this);
    }

    @Override
    public void close() {
        animation.setPlayMode(Animation.PlayMode.ONCE);
        isOpen = false;
        this.getScene().getMap().getTile(this.getPosX()/16, this.getPosY()/16).setType(MapTile.Type.WALL);
        if(this.orientation == Orientation.VERTICAL) {
            this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.WALL);
        } else {
            this.getScene().getMap().getTile(this.getPosX()/16+1, this.getPosY()/16).setType(MapTile.Type.WALL);
        }
        this.getScene().getMessageBus().publish(DOOR_CLOSED, this);
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void useWith(Actor actor) {
        if(this.isOpen) close();
        else open();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.close();
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }
}
