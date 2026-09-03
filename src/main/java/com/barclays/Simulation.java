package com.barclays;

public class Simulation {

    private final CommandFactory commandFactory = new CommandFactory();

    public void run(Robot robot, Grid grid, String instructions) {

        if (!grid.isInbound(robot.getPosition())) {
            throw new IllegalArgumentException("Robot is placed outside the grid");
        }
        if (instructions.length() >= 100) {
            throw new IllegalArgumentException(
                    "Instruction string must be less than 100 characters: %d".formatted(instructions.length()));
        }
        for (char instruction : instructions.toCharArray()) {
            if (robot.isLost()) {
                return;
            }
            Command command = commandFactory.from(instruction);
            command.execute(robot, grid);
        }
    }
}
