package com.barclays;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class SimulationTest {

    private final Simulation simulation = new Simulation();

    @Test
    void executesEachInstructionInOrder() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot robot = new Robot(new Position(1, 1), Orientation.N);

        // when
        simulation.run(robot, grid, "RFRFRFRF");

        // then
        assertThat(robot.getPosition()).isEqualTo(new Position(1, 1));
        assertThat(robot.getOrientation()).isEqualTo(Orientation.N);
        assertThat(robot.isLost()).isFalse();
        assertThat(robot.getOutputLine()).isEqualTo("1 1 N");
    }

    @Test
    void ignoresInstructionsAfterRobotIsLost() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot robot = new Robot(new Position(5, 5), Orientation.N);

        // when
        simulation.run(robot, grid, "FR");

        // then
        assertThat(robot.isLost()).isTrue();
        assertThat(robot.getPosition()).isEqualTo(new Position(5, 5));
        assertThat(robot.getOrientation()).isEqualTo(Orientation.N);
        assertThat(robot.getOutputLine()).isEqualTo("5 5 N LOST");
    }

    @Test
    void scentLeftByOneRobotProtectsALaterRobotRunOnTheSameGrid() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot firstRobot = new Robot(new Position(5, 5), Orientation.N);
        simulation.run(firstRobot, grid, "F");
        assertThat(firstRobot.isLost()).isTrue();

        // when
        Robot secondRobot = new Robot(new Position(5, 5), Orientation.E);
        simulation.run(secondRobot, grid, "F");

        // then
        assertThat(secondRobot.isLost()).isFalse();
        assertThat(secondRobot.getPosition()).isEqualTo(new Position(5, 5));
    }

    @Test
    void scentProtectedRobotKeepsExecutingInstructionsAfterTheIgnoredFatalMove() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot firstRobot = new Robot(new Position(5, 5), Orientation.N);
        simulation.run(firstRobot, grid, "F");

        // when
        Robot secondRobot = new Robot(new Position(5, 5), Orientation.E);
        simulation.run(secondRobot, grid, "FL");

        // then
        assertThat(secondRobot.isLost()).isFalse();
        assertThat(secondRobot.getPosition()).isEqualTo(new Position(5, 5));
        assertThat(secondRobot.getOrientation()).isEqualTo(Orientation.N);
    }

    @Test
    void shouldThrowExceptionWhenRobotIsPlacedOutsideGrid() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot robot = new Robot(new Position(6, 6), Orientation.N);

        // when & then
        assertThatIllegalArgumentException().isThrownBy(() -> simulation.run(robot, grid, "FR"));
    }

    @Test
    void shouldThrowExceptionWhenInstructionsAreTooLong() {
        // given
        Grid grid = new RectangularGrid(50, 50);
        Robot robot = new Robot(new Position(1, 1), Orientation.N);
        String tooLong = "F".repeat(100);

        // when & then
        assertThatIllegalArgumentException().isThrownBy(() -> simulation.run(robot, grid, tooLong));
    }

    @Test
    void shouldAllowInstructionsUpToNinetyNineCharacters() {
        // given
        Grid grid = new RectangularGrid(50, 50);
        Robot robot = new Robot(new Position(1, 1), Orientation.N);
        String maxLength = "L".repeat(99);

        // when
        simulation.run(robot, grid, maxLength);

        // then
        assertThat(robot.getPosition()).isEqualTo(new Position(1, 1));
        assertThat(robot.getOrientation()).isEqualTo(Orientation.E);
        assertThat(robot.isLost()).isFalse();
    }
}
