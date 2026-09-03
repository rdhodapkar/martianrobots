package com.barclays;

public class Robot {
    private Position position;
    private Orientation orientation;
    private boolean lost;

    public Robot(Position position, Orientation orientation) {
        this.position = position;
        this.orientation = orientation;
    }

    public Position getPosition() {
        return position;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public boolean isLost() {
        return lost;
    }

    public void turnLeft() {
        orientation = orientation.turnLeft();
    }

    public void turnRight() {
        orientation = orientation.turnRight();
    }

    public void moveForward(Grid grid) {
        Position nextPosition = position.move(orientation);
        if (grid.isInbound(nextPosition)) {
            position = nextPosition;
            return;
        }
        if (grid.hasScentAt(position)) {
            return;
        }
        lost = true;
        grid.leaveScent(position);
    }

    public String getOutputLine() {
        String base =  position.x() + " " + position.y() + " " + orientation;
        return lost ? base + " LOST" : base;
    }
}
