package com.barclays;

public class TurnRightCommand implements Command {
    @Override
    public void execute(Robot robot, Grid grid) {
        robot.turnRight();
    }
}
