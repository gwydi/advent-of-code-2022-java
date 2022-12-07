package me.gwydi.aoc.day3;

import me.gwydi.aoc.ChallengeInputReader;

import java.util.*;

public class Rucksack {
    public static void main(String[] args) {
        var input = new ChallengeInputReader("day3/input.txt").readAsString();
        var parsed = parse(input);
        var priority = getDuplicatePriority(parsed);
        System.out.println("Combined priority: " + priority);
        var parsed2 = parse2(input);
        var priority2 = getBatchSum(parsed2);
        System.out.println("Group Batch Sum: " + priority2);
    }

    private static List<Tuple<Set<Character>, Set<Character>>> parse(String input) {
        return Arrays.stream(input.split("\n")).map(s -> {
            var first = s.substring(0, s.length() / 2);
            var second = s.substring(s.length() / 2);
            System.out.println("first: " + first + " second: " + second);
            return new Tuple<>(charArrayToSet(first.toCharArray()), charArrayToSet(second.toCharArray()));
        }).toList();
    }

    private static List<List<Set<Character>>> parse2(String input) {
        var rucksacks = Arrays.stream(input.split("\n")).map(s -> charArrayToSet(s.toCharArray())).toList();
        List<List<Set<Character>>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for (var rucksack: rucksacks) {
            var value = result.get(result.size() -1);
            System.out.println("current rucksack is: " + rucksack + " size is "+ value.size());
            if (value.size() > 2) {
                var newEntry = new ArrayList<Set<Character>>();
                result.add(newEntry);
                newEntry.add(rucksack);
            } else  {
                value.add(rucksack);
            }
        }
        return result;
    }

    private static int getBatchSum(List<List<Set<Character>>> elfGroups) {
        return elfGroups.stream()
                .map(sets -> {
                    var intersection = new HashSet<>(Set.copyOf(sets.get(0)));
                    for (int i = 1; i < sets.size(); i++) {
                        intersection.retainAll(sets.get(i));
                        System.out.println("rucksack is" + sets.get(i));
                        System.out.println("retained: " + intersection);
                    }
                    return intersection.stream().findFirst().orElse(null);
                })
                .filter(Objects::nonNull)
                .map(Rucksack::getPriority)
                .reduce((integer, integer2) -> integer + integer2)
                .orElse(0);
    }

    private static int getDuplicatePriority(List<Tuple<Set<Character>, Set<Character>>> input) {
        int sum = 0;
        for (var rucksack : input) {
            rucksack.x.retainAll(rucksack.y);
            var value = rucksack.x.stream().findFirst().orElse(null);
            sum += getPriority(value);
        }
        return sum;
    }

    private static Set<Character> charArrayToSet(char[] chars) {
        Set<Character> set = new HashSet<>();
        for (char aChar : chars) {
            set.add(aChar);
        }
        return set;
    }

    private static int getPriority(char character) {
        if (Character.isUpperCase(character)) {
            return (int) character - 64 + 26;
        } else if (Character.isLowerCase(character)) {
            return (int) character - 96;
        }
        throw new IllegalArgumentException("Invalid character " + character);
    }

    private record Tuple<X, Y>(X x, Y y) {
    }
}
