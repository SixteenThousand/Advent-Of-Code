package main

import (
	"fmt"
	"os"
	"slices"
	"strconv"
	"strings"
)

func main() {
	fmt.Println(part2("Day01-input"))
}

type Lists struct {
	list1 []int
	list2 []int
}

func getLists(inputFile string) Lists {
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
	return Lists{
		list1,
		list2,
	}
}

func part1(inputFile string) int {
	data := getLists(inputFile)
	result := 0
	for i := 0; i < len(data.list1); i++ {
		result += max(data.list1[i]-data.list2[i], data.list2[i]-data.list1[i])
	}
	return result
}

func part2(inputFile string) int {
	data := getLists(inputFile)
	result := 0
	var count int
	for i := 0; i < len(data.list1); i++ {
		count = 0
		for j := 0; j < len(data.list2); j++ {
			if data.list2[j] != data.list1[i] {
				if count > 0 {
					break
				} else {
					continue
				}
			}
			count++
		}
		result += count * data.list1[i]
	}
	return result
}
