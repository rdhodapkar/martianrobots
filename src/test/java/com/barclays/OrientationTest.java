package com.barclays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class OrientationTest {


    @Test
    void turningLeftFromNorthFacesWest() {
        // given
        Orientation north = Orientation.N;

        // when
        Orientation actual = north.turnLeft();

        //then
        assertThat(actual).isEqualTo(Orientation.W);
    }

    @Test
    void turningLeftFromWestFacesSouth() {
        // given
        Orientation west = Orientation.W;

        // when
        Orientation actual = west.turnLeft();

        //then
        assertThat(actual).isEqualTo(Orientation.S);
    }

    @Test
    void turningLeftFromSouthFacesEast() {
        // given
        Orientation south = Orientation.S;

        // when
        Orientation actual = south.turnLeft();

        //then
        assertThat(actual).isEqualTo(Orientation.E);
    }

    @Test
    void turningLeftFromEastFacesNorth() {
        // given
        Orientation east = Orientation.E;

        // when
        Orientation actual = east.turnLeft();

        //then
        assertThat(actual).isEqualTo(Orientation.N);
    }

    @Test
    void turningRightFromWestFacesNorth() {
        // given
        Orientation west = Orientation.W;

        // when
        Orientation actual = west.turnRight();

        //then
        assertThat(actual).isEqualTo(Orientation.N);
    }

    @Test
    void turnRightFromNorthFacesEast() {
        // given
        Orientation north = Orientation.N;

        // when
        Orientation actual = north.turnRight();

        // then
        assertThat(actual).isEqualTo(Orientation.E);
    }

    @Test
    void turnRightFromEastFacesSouth() {
        // given
        Orientation east = Orientation.E;

        // when
        Orientation actual = east.turnRight();

        // then
        assertThat(actual).isEqualTo(Orientation.S);
    }

    @Test
    void turnRightFromSouthFacesWest() {
        // given
        Orientation south = Orientation.S;

        // when
        Orientation actual = south.turnRight();

        // then
        assertThat(actual).isEqualTo(Orientation.W);
    }


    @ParameterizedTest
    @CsvSource({
            "N, 0, 1",
            "S, 0, -1",
            "E, 1, 0",
            "W, -1, 0"
    })
    void deltaMatchesExpectedDirection(Orientation orientation, int expectedDx, int expectedDy) {
       assertThat(orientation.deltaX()).isEqualTo(expectedDx);
       assertThat(orientation.deltaY()).isEqualTo(expectedDy);
    }
}
