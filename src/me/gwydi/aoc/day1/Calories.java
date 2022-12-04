package me.gwydi.aoc.day1;

import me.gwydi.aoc.ChallengeInputReader;

import java.util.Arrays;
import java.util.List;

public class Calories {
    public static void main(String[] args) {
        String input = new ChallengeInputReader("src/me/gwydi/aoc/day1/input.txt").readAsString();
        System.out.println("Top Elf");
        System.out.println(getHighestCalories(parse(input)));
        System.out.println("Top 3 Elves");
        System.out.println(getTopThreeTotal(parse(input)));
    }

    static List<Integer> parse(String input) {
        return Arrays.stream(input.split("\n\n"))
                .map(s -> Arrays.stream(s.split("\n"))
                        .map(Integer::parseInt).reduce(Integer::sum).orElse(0)).toList();
    }

    static int getHighestCalories(List<Integer> input) {
        var result = input.stream().max(Integer::compare);
        if (result.isPresent()) {
            return result.get();
        }
        throw new IllegalArgumentException("String cannot be empty");
    }

    static int getTopThreeTotal(List<Integer> input) {
        if (input.size() < 3) {
            throw new IllegalArgumentException("Input length must be bigger than 3");
        }
        var rangeStartIndex = input.size() - 3;
        return input
                .stream()
                .sorted()
                .toList()
                .subList(rangeStartIndex, input.size())
                .stream()
                .reduce(Integer::sum)
                .orElse(0);

    }
}
