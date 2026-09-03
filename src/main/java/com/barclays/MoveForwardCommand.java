package com.barclays;

public class MoveForwardCommand implements Command {
    @Override
    public void execute(Robot robot, Grid grid) {
        robot.moveForward(grid);
    }
}
