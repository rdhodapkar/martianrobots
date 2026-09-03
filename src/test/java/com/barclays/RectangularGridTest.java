package com.barclays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class RectangularGridTest {

    private final RectangularGrid grid = new RectangularGrid(5, 5);

    @ParameterizedTest
    @CsvSource({
            "0, 0, true",
            "4, 4, true",
            "5, 5, true",
            "5, 4, true",
            "4, 5, true",
            "-1, 3, false",
            "3, -1, false",
            "6, 3, false",
            "3, 6, false",
            "6,6, false"

    })
    void isInboundMatchesExpectedResult(int x, int y, boolean expected) {

        // when
        boolean result = grid.isInbound(new Position(x, y));

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void newGridHasNoScentsYet() {
        assertThat(grid.hasScentAt(new Position(3, 3))).isFalse();
    }

    @Test
    void aLeftScentIsRememberedAtThatCoordinateOnly() {
        grid.leaveScent(new Position(3, 3));

        assertThat(grid.hasScentAt(new Position(3, 3))).isTrue();
        assertThat(grid.hasScentAt(new Position(3, 2))).isFalse();
    }

    @Test
    void negativeGridDimensionsAreRejected() {
        assertThatIllegalArgumentException().isThrownBy(() -> new RectangularGrid(-1, 3));
        assertThatIllegalArgumentException().isThrownBy(() -> new RectangularGrid(3, -1));
    }

    @Test
    void gridDimensionsGreaterThan50AreRejected() {
        assertThatIllegalArgumentException().isThrownBy(() -> new RectangularGrid(51, 3));
        assertThatIllegalArgumentException().isThrownBy(() -> new RectangularGrid(3, 51));
    }

}
