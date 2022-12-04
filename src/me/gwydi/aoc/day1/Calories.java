package me.gwydi.aoc.day1;

import me.gwydi.aoc.ChallengeInputReader;

import java.util.Arrays;

public class Calories {
    public static void main(String[] args) {
        String input = new ChallengeInputReader("src/me/gwydi/aoc/day1/input.txt").readAsString();
        System.out.println(getHighestCalories(input));
    }

    static int getHighestCalories(String input) {
        var result = Arrays.stream(input.split("\n\n"))
                .map(s -> Arrays.stream(s.split("\n"))
                        .map(Integer::parseInt).reduce(Integer::sum).orElse(0))
                .max(Integer::compare);
        if (result.isPresent()) {
            return result.get();
        }
        throw new IllegalArgumentException("String cannot be empty");
    }


}
