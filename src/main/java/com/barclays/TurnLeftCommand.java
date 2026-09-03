package com.barclays;

public class TurnLeftCommand implements Command {
    @Override
    public void execute(Robot robot, Grid grid) {
        robot.turnLeft();
    }
}
