package com.barclays;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class InputParserTest {

    private final InputParser parser = new InputParser();

    @Test
    void parsesGridDimensionsFromFirstLine() {
        // given
        String input = """
                5 3
                1 1 E
                RFRFRFRF
                """;

        // when
        ParsedInput parsed = parser.parse(input);

        // then
        assertThat(parsed.grid().isInbound(new Position(5, 3))).isTrue();
        assertThat(parsed.grid().isInbound(new Position(6, 3))).isFalse();
    }

    @Test
    void parsesSingleRobotWithPositionOrientationAndInstructions() {
        // given
        String input = """
                5 3
                1 1 E
                RFRFRFRF
                """;

        // when
        ParsedInput parsed = parser.parse(input);

        // then
        assertThat(parsed.robotInstructions()).hasSize(1);
        RobotInstruction robotInstruction = parsed.robotInstructions().getFirst();
        assertThat(robotInstruction.robot().getPosition()).isEqualTo(new Position(1, 1));
        assertThat(robotInstruction.robot().getOrientation()).isEqualTo(Orientation.E);
        assertThat(robotInstruction.instructions()).isEqualTo("RFRFRFRF");
    }

    @Test
    void parsesMultipleRobotsSeparatedByBlankLines() {
        // given
        String input = """
                5 3
                1 1 E
                RFRFRFRF

                3 2 N
                FRRFLLFFRRFLL

                0 3 W
                LLFFFLFLFL
                """;

        // when
        ParsedInput parsed = parser.parse(input);

        // then
        assertThat(parsed.robotInstructions()).hasSize(3);
        assertThat(parsed.robotInstructions().get(1).robot().getPosition()).isEqualTo(new Position(3, 2));
        assertThat(parsed.robotInstructions().get(1).robot().getOrientation()).isEqualTo(Orientation.N);
        assertThat(parsed.robotInstructions().get(1).instructions()).isEqualTo("FRRFLLFFRRFLL");
    }

    @Test
    void endToEndParseThenSimulateProducesExpectedOutputLines() {
        // given
        String input = """
                5 3
                1 1 E
                RFRFRFRF
                3 2 N
                FRRFLLFFRRFLL
                0 3 W
                LLFFFLFLFL
                """;
        Simulation simulation = new Simulation();

        // when
        ParsedInput parsed = parser.parse(input);
        List<String> outputLines = parsed.robotInstructions().stream()
                .map(robotInstruction -> {
                    simulation.run(robotInstruction.robot(), parsed.grid(), robotInstruction.instructions());
                    return robotInstruction.robot().getOutputLine();
                })
                .toList();

        // then
        assertThat(outputLines).containsExactly(
                "1 1 E",
                "3 3 N LOST",
                "2 3 S"
        );
    }

    @Test
    void shouldThrowExceptionWhenInputIsBlank() {
        // given
        String input = "   \n\n  ";

        // when & then
        assertThatIllegalArgumentException().isThrownBy(() -> parser.parse(input));
    }

    @Test
    void shouldThrowExceptionWhenRobotPositionLineHasNoInstructionLine() {
        // given
        String input = """
                5 5
                1 1 N
                """;

        // when & then
        assertThatIllegalArgumentException().isThrownBy(() -> parser.parse(input));
    }
}
