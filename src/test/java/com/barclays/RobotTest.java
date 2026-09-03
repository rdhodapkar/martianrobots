package com.barclays;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RobotTest {
    @Test
    void validateNewRobotState() {

        Robot newRobot = new Robot(new Position(0, 0), Orientation.N);

        assertThat(newRobot.getPosition()).isEqualTo(new Position(0, 0));
        assertThat(newRobot.getOrientation()).isEqualTo(Orientation.N);
        assertThat(newRobot.isLost()).isFalse();
    }

    @Test
    void validateTurnLeft() {
        // given
        Robot robot = new Robot(new Position(0, 0), Orientation.N);

        // when
        robot.turnLeft();

        // then
        assertThat(robot.getOrientation()).isEqualTo(Orientation.W);
    }

    @Test
    void validateTurnRight() {
        // given
        Robot robot = new Robot(new Position(0, 0), Orientation.N);

        // when
        robot.turnRight();

        // then
        assertThat(robot.getOrientation()).isEqualTo(Orientation.E);
    }

    @Test
    void validateMoveForwardForward() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot robot = new Robot(new Position(0, 0), Orientation.N);
        // when
        robot.moveForward(grid);
        // then
        assertThat(robot.getPosition()).isEqualTo(new Position(0, 1));
    }

    @Test
    void robotLostShouldLeaveScent() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot robot = new Robot(new Position(5, 5), Orientation.N);
        // when
        robot.moveForward(grid);
        // then
        assertThat(robot.isLost()).isTrue();
        assertThat(grid.hasScentAt(new Position(5, 5))).isTrue();

    }

    @Test
    void robotOnPreviouslyScentedPointIgnoresFatalMoveInsteadOfBeingLost() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot robot = new Robot(new Position(5, 3), Orientation.E);
        robot.moveForward(grid);
        assertThat(robot.isLost()).isTrue();
        assertThat(grid.hasScentAt(new Position(5, 3))).isTrue();

        // when
        Robot newRobot = new Robot(new Position(5, 3), Orientation.E);
        newRobot.moveForward(grid);

        //then
        assertThat(newRobot.isLost()).isFalse();
        assertThat(newRobot.getPosition()).isEqualTo(new Position(5, 3));
    }

    @Test
    void scentProtectsAgainstFatalMoveInADifferentDirectionThanTheOneThatLeftIt() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot firstRobot = new Robot(new Position(5, 5), Orientation.N);
        firstRobot.moveForward(grid);
        assertThat(firstRobot.isLost()).isTrue();
        assertThat(grid.hasScentAt(new Position(5, 5))).isTrue();

        // when
        Robot secondRobot = new Robot(new Position(5, 5), Orientation.E);
        secondRobot.moveForward(grid);

        // then
        assertThat(secondRobot.isLost()).isFalse();
        assertThat(secondRobot.getPosition()).isEqualTo(new Position(5, 5));
    }

    @Test
    void movingForwardAgainAfterAlreadyLostStaysLostInsteadOfRecovering() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot robot = new Robot(new Position(5, 5), Orientation.N);
        robot.moveForward(grid);
        assertThat(robot.isLost()).isTrue();

        // when
        robot.moveForward(grid);

        // then
        assertThat(robot.isLost()).isTrue();
        assertThat(robot.getPosition()).isEqualTo(new Position(5, 5));
    }

    @Test
    void shouldReportFinalPositionWhenInbound() {

        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot robot = new Robot(new Position(0, 0), Orientation.N);

        // when
        robot.moveForward(grid);
        robot.turnLeft();

        // then
        assertThat(robot.getPosition()).isEqualTo(new Position(0, 1));
        assertThat(robot.getOrientation()).isEqualTo(Orientation.W);
        assertThat(robot.getOutputLine()).isEqualTo("0 1 W");

    }

    @Test
    void shouldReportFinalPositionWhenLost() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot robot = new Robot(new Position(5, 5), Orientation.N);

        // when
        robot.moveForward(grid);

        // then
        assertThat(robot.isLost()).isTrue();
        assertThat(robot.getOutputLine()).isEqualTo("5 5 N LOST");
    }
}
