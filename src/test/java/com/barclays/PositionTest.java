package com.barclays;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PositionTest {

    @Test
    void validateMoveWhenFacingNorth() {
        // given
        Orientation north = Orientation.N;
        Position position = new Position(2,2);

        // when
        Position actual = position.move(north);

        // then
        assertThat(actual).isEqualTo(new Position(2,3));
    }

    @Test
    void validateMoveWhenFacingSouth() {
        // given
        Orientation south = Orientation.S;
        Position position = new Position(2,2);

        // when
        Position actual = position.move(south);

        // then
        assertThat(actual).isEqualTo(new Position(2,1));
    }

    @Test
    void validateMoveWhenFacingEast() {
        // given
        Orientation east = Orientation.E;
        Position position = new Position(2,2);

        // when
        Position actual = position.move(east);

        // then
        assertThat(actual).isEqualTo(new Position(3,2));
    }

    @Test
    void validateMoveWhenFacingWest() {
        // given
        Orientation west = Orientation.W;
        Position position = new Position(2,2);

        // when
        Position actual = position.move(west);

        // then
        assertThat(actual).isEqualTo(new Position(1,2));
    }
}
