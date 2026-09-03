package com.barclays;

public record Position(int x, int y) {

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position move(Orientation orientation) {
        return new Position(x + orientation.deltaX(), y + orientation.deltaY());
    }

}
