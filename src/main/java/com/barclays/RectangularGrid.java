package com.barclays;

import java.util.HashSet;
import java.util.Set;

public class RectangularGrid implements Grid {
    private final int maxX;
    private final int maxY;
    private final Set<Position> scents = new HashSet<>();

    public RectangularGrid(int maxX, int maxY) {
        if (maxX < 0 || maxY < 0) {
            throw new IllegalArgumentException(
                    "Grid upper right coordinates must not be negative: (%d, %d)".formatted(maxX, maxY));
        }

        if (maxX > 50 || maxY > 50) {
            throw new IllegalArgumentException(
                    "Grid upper right coordinates must not exceed 50: (%d, %d)".formatted(maxX, maxY));
        }
        this.maxX = maxX;
        this.maxY = maxY;
    }

    @Override
    public boolean isInbound(Position position) {
        return position.x() >= 0 && position.x() <= maxX && position.y() >= 0 && position.y() <= maxY;
    }

    @Override
    public boolean hasScentAt(Position position) {
        return scents.contains(position);
    }

    @Override
    public void leaveScent(Position position) {
        scents.add(position);
    }
}
