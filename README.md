# Martian Robots

Domain model for the classic "Martian Robots": robots receive a string of `L`/`R`/`F`
instructions and move around a rectangular grid; a robot that falls off the edge of the grid is
reported as `LOST` — but leaves a scent behind that stops any later robot from falling off at
that same point.

## Requirements

- JDK 21+ (no toolchain is pinned in `build.gradle`, so Gradle uses whatever JDK is on your
  `PATH` — the code uses Java 21 language features, e.g. `List.getFirst()`)
- No local Gradle install needed — use the included wrapper (`./gradlew`)

## Running the tests

```
./gradlew test

The tests can also be run in the IDE.
```

A coverage report (JaCoCo) is generated automatically after the test run:

```
build/reports/tests/test/index.html
```

Coverage currently sits at 100% (lines, branches, methods, and classes).

## Decisions and assumptions

- **Built test-first throughout (red/green per class)**, which is why the test suite is organized
one-to-one with the production classes (`RobotTest`, `RectangularGridTest`,
`SimulationTest`, etc.) rather than by feature/scenario.
- **Grid bounds are validated**: coordinates must be non-negative and no greater than 50 in
  either axis (`RectangularGrid`). `IllegalArgumentException` rather than silently accepted.
- **Instruction strings must be under 100 characters** (`Simulation`), per the same constraint;
  enforced the same way.
- **Scent is per-position, not per-direction.** A scent left when a robot falls off protects any
  later robot from falling off *that same cell*, regardless of which direction it was heading.
- **Once a robot is `LOST`, its remaining instructions are ignored.** This rule lives in
  `Simulation`'s instruction loop, not in `Robot` or the `Command` classes — those stay dumb and
  will happily execute a move on an already-lost robot if called directly (which only matters if
  something other than `Simulation` ever drives them).
- **A robot that's merely scent-protected (its fatal move was a no-op) is not lost**, and keeps
  executing the rest of its instructions normally — only the one fatal instruction is skipped.
- **Malformed input fails fast** with `IllegalArgumentException` (blank input, a robot position
  line with no matching instruction line, an unparseable orientation letter, non-numeric
  coordinates) rather than attempting partial recovery
- **Unknown instruction characters throw** (`CommandFactory`) rather than being silently
  skipped, on the same fail-fast reasoning.
