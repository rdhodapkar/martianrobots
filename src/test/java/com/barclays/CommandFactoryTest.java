package com.barclays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class CommandFactoryTest {

    private final CommandFactory factory = new CommandFactory();

    @ParameterizedTest
    @CsvSource({
            "L, com.barclays.TurnLeftCommand",
            "R, com.barclays.TurnRightCommand",
            "F, com.barclays.MoveForwardCommand"
    })
    void shouldMapInstructionCharacterToExpectedCommand(char instruction, Class<? extends Command> expectedType) {
        assertThat(factory.from(instruction)).isInstanceOf(expectedType);
    }


    @Test
    void shouldRejectUnknownInstruction() {
        assertThatIllegalArgumentException().isThrownBy(() -> factory.from('X'));
    }
}
