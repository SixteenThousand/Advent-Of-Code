package main

import (
	"fmt"
	"os"
	"slices"
	"strconv"
	"strings"
)

func main() {
	fmt.Println(part1("Day02-input"))
}

func check(err error) {
	if err != nil {
		panic(err)
	}
}

func getInputLines(inputName string) []string {
	input, err := os.ReadFile(inputName)
	check(err)
	input = slices.Delete(input, len(input)-1, len(input))
	return strings.Split(string(input), "\n")
}

func isReportSafe(levels []int) bool {
	if levels[1] == levels[0] {
		return false
	}
	isIncreasing := true
	if levels[1] < levels[0] {
		isIncreasing = false
	}
	if isIncreasing {
		for i := 1; i < len(levels)-1; i++ {
			if levels[i] >= levels[i+1] {
				return false
			}
			diff := levels[i+1] - levels[i]
			if diff > 3 {
				return false
			}
		}
	} else {
		for i := 1; i < len(levels)-1; i++ {
			if levels[i] <= levels[i+1] {
				return false
			}
			diff := levels[i] - levels[i+1]
			if diff > 3 {
				return false
			}
		}
	}
	return true
}

func part1(inputName string) int {
	lines := getInputLines(inputName)
	numSafeReports := 0
	var err error
	var levels []int
	for _, line := range lines {
		rawLevels := strings.Split(line, " ")
		if len(rawLevels) < 2 {
			numSafeReports++
			continue
		}
		levels = make([]int, len(rawLevels))
		for index, num := range rawLevels {
			levels[index], err = strconv.Atoi(num)
			check(err)
		}
		if isReportSafe(levels) {
			numSafeReports++
		}
	}
	return numSafeReports
}
