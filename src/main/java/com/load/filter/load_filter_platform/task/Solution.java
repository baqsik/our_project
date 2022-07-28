package com.load.filter.load_filter_platform.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.max;

public class Solution {

    public static void main(String[] args) {

        int temp = 0;
        int result = 1;
        var pairList = new ArrayList<Pair>();
        var integerList = new ArrayList<List<Integer>>();

        integerList.add(List.of(0, 5));
        integerList.add(List.of(1, 2));
        integerList.add(List.of(3, 4));
        integerList.add(List.of(5, 6));
        integerList.add(List.of(10, 12));
        integerList.add(List.of(9, 13));

        for (var integers : integerList) {
            var firstElement = integers.get(0);
            var startPairElement = new Pair(firstElement, "start");
            pairList.add(startPairElement);
            var secondElement = integers.get(1);
            var secondPairElement = new Pair(secondElement, "end");
            pairList.add(secondPairElement);
        }

        pairList.sort(Comparator.comparing(Pair::getA));

        for (var pair : pairList) {
            if (pair.getB().equals("start")) {
                temp += 1;
            } else if (pair.getB().equals("end")) {
                temp -= 1;
            }
            result = max(result, temp);
        }
        System.out.println(result);
    }
}
