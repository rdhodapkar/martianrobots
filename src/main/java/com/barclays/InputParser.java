package com.barclays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputParser {

    public ParsedInput parse(String input) {
        List<String> lines = Arrays.stream(input.split("\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .toList();

        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Input must contain at least a grid size line");
        }

        Grid grid = parseGrid(lines.getFirst());

        List<RobotInstruction> robotInstructions = new ArrayList<>();
        for (int i = 1; i < lines.size(); i += 2) {
            if (i + 1 >= lines.size()) {
                throw new IllegalArgumentException(
                        "Robot position line has no matching instruction line: " + lines.get(i));
            }
            Robot robot = parseRobot(lines.get(i));
            String instructions = lines.get(i + 1);
            robotInstructions.add(new RobotInstruction(robot, instructions));
        }

        return new ParsedInput(grid, robotInstructions);
    }

    private Grid parseGrid(String line) {
        String[] parts = line.split("\\s+");
        return new RectangularGrid(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    private Robot parseRobot(String line) {
        String[] parts = line.split("\\s+");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        Orientation orientation = Orientation.valueOf(parts[2]);
        return new Robot(new Position(x, y), orientation);
    }
}
