package com.barclays;

public class CommandFactory {

    public Command from(char instruction) {
        return switch (instruction) {
            case 'L' -> new TurnLeftCommand();
            case 'R' -> new TurnRightCommand();
            case 'F' -> new MoveForwardCommand();
            default -> throw new IllegalArgumentException("Unknown instruction: " + instruction);
        };
    }
}
