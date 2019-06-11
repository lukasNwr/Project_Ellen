package sk.tuke.kpi.oop.game;

import java.util.Random;

public enum Direction {
    EAST (1, 0),
    NONE (0, 0),
    NORTH (0, 1),
    SOUTH (0, -1),
    WEST (-1, 0),
    SOUTHEAST (1, -1),
    SOUTHWEST (-1, -1),
    NORTHEAST (1, 1),
    NORTHWEST (-1, 1);


    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;

    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public float getAngle() {
        switch (this) {
            case NORTH: return 0;
            case EAST: return 270;
            case WEST: return 90;
            case SOUTH: return 180;
            case NORTHEAST: return 315;
            case NORTHWEST: return  45;
            case SOUTHEAST: return  225;
            case SOUTHWEST: return  135;
            default: return 0;
        }
    }

    public static Direction getRandom() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

    public static Direction fromAngle(float angle) {
        switch ((int)angle) {
            case 0: return NORTH;
            case 270: return EAST;
            case 90: return WEST;
            case 180: return SOUTH;
            case 315: return NORTHEAST;
            case 45: return NORTHWEST;
            case 225: return SOUTHEAST;
            case 135: return SOUTHWEST;
            default: return NONE;
        }
    }

    private int adder(int a, int b) {
        if((a + b) > 1) return 1;
        if((a + b) < -1) return -1;
        return a + b;
    }


    public Direction combine(Direction other) {
        Direction[] directions = Direction.values();

        for (int i = 0; i < directions.length; i++)
            if (adder(dx, other.dx) == directions[i].dx && adder(dy, other.dy) == directions[i].dy)
                return directions[i];

        return this;
    }

}
