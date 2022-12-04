package me.gwydi.aoc.day2;

import me.gwydi.aoc.ChallengeInputReader;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RockPaperScissors {
    private static final Map<String, HandShape> PARSE_MAP = Map.of("A", HandShape.ROCK,
            "B", HandShape.PAPER,
            "C", HandShape.SCISSORS,
            "X", HandShape.ROCK,
            "Y", HandShape.PAPER,
            "Z", HandShape.SCISSORS
    );

    public static void main(String[] args) {
        String input = new ChallengeInputReader("src/me/gwydi/aoc/day2/input.txt").readAsString();
        var parsed = parse(input);
        var result = parsed.stream().map(match -> match.y.match.fight(match.x) + match.y.freeScore).reduce(Integer::sum).orElse(0);
        System.out.println("total points");
        System.out.println(" " + result);
    }

    private static List<Tuple<HandShape, HandShape>> parse(String input) {
        return Arrays.stream(input.trim().split("\n")).map(s -> {
            var pair = s.split(" ");
            return new Tuple<>(PARSE_MAP.get(pair[0]), PARSE_MAP.get(pair[1]));
        }).toList();
    }

    private static enum HandShape {
        ROCK(1),
        PAPER(2),
        SCISSORS(3),
        ;
        public RockPaperScissorsMatch match;
        public int freeScore;

        static {
            ROCK.match = shape -> switch (shape) {
                case ROCK -> 3;
                case PAPER -> 0;
                case SCISSORS -> 6;
            };
            PAPER.match = shape -> switch (shape) {
                case ROCK -> 6;
                case PAPER -> 3;
                case SCISSORS -> 0;
            };
            SCISSORS.match = shape -> switch (shape) {
                case ROCK -> 0;
                case PAPER -> 6;
                case SCISSORS -> 3;
            };
        }

        HandShape(int freeScore) {
            this.freeScore = freeScore;
        }
    }

    private interface RockPaperScissorsMatch {
        int fight(HandShape shape);
    }

    private record Tuple<X, Y>(X x, Y y) { }
}
