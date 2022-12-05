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
        String input = new ChallengeInputReader("day2/input.txt").readAsString();
        var parsed = parse(input);
        var parsed2 = parsePart2(input);
        var result = playMatch(parsed);
        var result2 = playMatch(parsed2);
        System.out.println("total points");
        System.out.println(" " + result);
        System.out.println("correct calculation");
        System.out.println(" " + result2);
    }

    public static int playMatch(List<Tuple<HandShape, HandShape>> input) {
        return input.stream().map(match -> match.y.fight(match.x) + match.y.freeScore).reduce(Integer::sum).orElse(0);
    }

    private static List<Tuple<HandShape, HandShape>> parse(String input) {
        return Arrays.stream(input.trim().split(System.lineSeparator())).map(s -> {
            var pair = s.split(" ");
            return new Tuple<>(PARSE_MAP.get(pair[0]), PARSE_MAP.get(pair[1]));
        }).toList();
    }

    private static List<Tuple<HandShape, HandShape>> parsePart2(String input) {
        return Arrays.stream(input.trim().split(System.lineSeparator())).map(s -> {
            var pair = s.split(" ");
            var opponentMove = PARSE_MAP.get(pair[0]);
            return new Tuple<>(opponentMove, getCorrectMove(opponentMove, pair[1]));
        }).toList();
    }

    private static HandShape getCorrectMove(HandShape otherMove, String move) {
        return switch (move) {
            case "X" -> otherMove.win;
            case "Y" -> otherMove.draw;
            case "Z" -> otherMove.lose;
            default -> throw new IllegalStateException("String on 2nd part hast to be X, Y or Z");
        };
    }

    private static enum HandShape {
        ROCK(1),
        PAPER(2),
        SCISSORS(3),
        ;
        public HandShape win;
        public HandShape lose;
        public HandShape draw = this;
        public int freeScore;

        static {
            ROCK.win = SCISSORS;
            ROCK.lose = PAPER;
            PAPER.win = ROCK;
            PAPER.lose = SCISSORS;
            SCISSORS.win = PAPER;
            SCISSORS.lose = ROCK;
        }

        public int fight(HandShape other) {
            if (other == win) {
                return 6;
            } else if (other == lose) {
                return 0;
            } else if (other == draw) {
                return 3;
            }
            throw new IllegalStateException("should be unreachable");
        }

        HandShape(int freeScore) {
            this.freeScore = freeScore;
        }
    }


    private record Tuple<X, Y>(X x, Y y) {
    }
}
