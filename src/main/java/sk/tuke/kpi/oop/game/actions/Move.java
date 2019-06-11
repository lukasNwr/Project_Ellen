package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.gamelib.graphics.Point;
import sk.tuke.kpi.oop.game.other.Direction;
import sk.tuke.kpi.oop.game.other.Movable;



public class Move<T extends  Movable> implements Action<T> {

    private Direction direction;
    private float duration;
    private T movable;
    private boolean isDone;
    private float timePassed;
    private boolean firstTime;

    public Move(Direction direction) {
        this.direction = direction;
        this.isDone = false;
        this.duration = 0;
        this.timePassed = 0;
        this.firstTime = true;
    }

    public Move(Direction direction, float duration) {
        this.direction = direction;
        this.duration = duration;
        this.isDone = false;
        this.timePassed = 0;
        firstTime = true;
    }

    public void stop() {
        if(movable == null) {
            setDone(true);
            return;
        }
        movable.stoppedMoving();
        setDone(true);
    }

    @Override
    public void setActor(T actor) {
        movable = actor;
    }

    @Nullable
    @Override
    public T getActor() {
        return this.movable;
    }

    @Override
    public void reset() {
        setDone(false);
    }

    @Override
    public void execute(float deltaTime) {
        timePassed +=  deltaTime;
        if(movable == null) {
            setDone(true);
            return;
        }
        if(!isDone) {
            Point previousPosition = movable.getPosition();
            if(firstTime)
                movable.startedMoving(direction);

            movable.setPosition(movable.getPosX() + direction.getDx() * movable.getSpeed(),
                movable.getPosY() + direction.getDy() * movable.getSpeed());

            if (movable.getScene().getMap().intersectsWithWall(movable)) {
                movable.setPosition(previousPosition.getX(), previousPosition.getY());
                movable.collidedWithWall();
                setDone(true);
            }
            if(timePassed > duration) {
                movable.stoppedMoving();
                setDone(true);
                return;
            }
        }

        firstTime = false;

    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public boolean isDone() {
        if(isDone)
            return true;
        else
            return false;
    }
}
