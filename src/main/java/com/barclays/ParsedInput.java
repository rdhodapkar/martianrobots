package com.barclays;

import java.util.List;

public record ParsedInput(Grid grid, List<RobotInstruction> robotInstructions) {
}
