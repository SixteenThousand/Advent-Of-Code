package main

import (
	"fmt"
	// "math"
	"os"
	"slices"
	"strconv"
	"strings"
)

func main() {
	fmt.Println(part1("Day01-input"))
}

func part1(inputFile string) int {
	input, err := os.ReadFile(inputFile)
	if err != nil {
		panic(err)
	}
	list1 := make([]int, 0)
	list2 := make([]int, 0)
	var num_strs []string
	var num int
	for _, line := range strings.Split(string(input), "\n") {
		num_strs = strings.Split(line, "   ")
		if len(num_strs) != 2 {
			continue
		}
		num, err = strconv.Atoi(num_strs[0])
		if err != nil {
			panic(err)
		}
		list1 = append(list1, num)
		num, err = strconv.Atoi(num_strs[1])
		if err != nil {
			panic(err)
		}
		list2 = append(list2, num)
	}
	slices.Sort(list1)
	slices.Sort(list2)
	result := 0
	for i := 0; i < len(list1); i++ {
		result += max(list1[i]-list2[i], list2[i]-list1[i])
	}
	return result
}
