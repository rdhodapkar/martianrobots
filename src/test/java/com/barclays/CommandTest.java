package com.barclays;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandTest {

    @Test
    void shouldFaceWestAfterTurningLeftFromNorth() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot robot = new Robot(new Position(1, 1), Orientation.N);

        //when
        Command turnLeftCommand = new TurnLeftCommand();
        turnLeftCommand.execute(robot, grid);

        // then
         assertThat(robot.getOrientation()).isEqualTo(Orientation.W);
    }

    @Test
    void shouldFaceEastAfterTurningRightFromNorth() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot robot = new Robot(new Position(1, 1), Orientation.N);

        //when
        Command turnRightCommand = new TurnRightCommand();
        turnRightCommand.execute(robot, grid);

        // then
        assertThat(robot.getOrientation()).isEqualTo(Orientation.E);
    }

    @Test
    void shouldMoveForwardFromNorth() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot robot = new Robot(new Position(1, 1), Orientation.N);

        //when
        Command moveCommand = new MoveForwardCommand();
        moveCommand.execute(robot, grid);

        // then
        assertThat(robot.getPosition()).isEqualTo(new Position(1, 2));
    }

    @Test
    void moveCommandShouldLeaveRobotLostWhenMovingOffGrid() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot robot = new Robot(new Position(5, 5), Orientation.N);

        //when
        Command moveCommand = new MoveForwardCommand();
        moveCommand.execute(robot, grid);

        // then
        assertThat(robot.isLost()).isTrue();
        assertThat(grid.hasScentAt(new Position(5, 5))).isTrue();
    }

    @Test
    void moveCommandShouldIgnoreFatalMoveWhenScentAlreadyPresent() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        grid.leaveScent(new Position(5, 5));
        Robot robot = new Robot(new Position(5, 5), Orientation.N);

        //when
        Command moveCommand = new MoveForwardCommand();
        moveCommand.execute(robot, grid);

        // then
        assertThat(robot.isLost()).isFalse();
        assertThat(robot.getPosition()).isEqualTo(new Position(5, 5));
    }

    @Test
    void turnLeftCommandShouldNotChangePosition() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot robot = new Robot(new Position(1, 1), Orientation.N);

        //when
        Command turnLeftCommand = new TurnLeftCommand();
        turnLeftCommand.execute(robot, grid);

        // then
        assertThat(robot.getPosition()).isEqualTo(new Position(1, 1));
    }

    @Test
    void turnRightCommandShouldNotChangePosition() {
        // given
        Grid grid = new RectangularGrid(5, 5);
        Robot robot = new Robot(new Position(1, 1), Orientation.N);

        //when
        Command turnRightCommand = new TurnRightCommand();
        turnRightCommand.execute(robot, grid);

        // then
        assertThat(robot.getPosition()).isEqualTo(new Position(1, 1));
    }
}
