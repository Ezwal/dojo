package main

import (
	"fmt"
	"os"
	"io/ioutil"
	"math"
	"strings"
)

func facto(x int) (acc int) {
	acc = 1
	for i := 1; i <= x; i++ {
		acc *= i
	}
	return
}

func palyndrome(word string) bool {
	lenWord := len(word)
	halfWordLen := int(math.Floor(float64(lenWord / 2)))
	for i := 0; i < halfWordLen; i ++ {
		if word[i] != word[lenWord-i-1] {
			return false
		}
	}
	return true
}

func isOrNot(is bool) (bs string) {
	bs = "not"
	if is {
		return ""
	}
	return
}

func testManyPalyn() {
	palynList := [...]string{"abc", "abba", "yolo", "aaabaaa"}
	for _, palyn := range palynList {
		fmt.Println(palyn, "is", palyndrome(palyn))
	}
}

func freqs(s string) map[rune]int {
	charFreq := make(map[rune]int)
	for _, char := range s {
		charFreq[char] += 1
	}
	return charFreq
}

func contains(freqA, freqB map[rune]int) bool {
	for key, value := range freqA {
		if freqB[key] != value {
			return false
		}
	}
	return true
}

func isAnagramOf(a string, b string) (is bool) {
	freqA := freqs(a)
	freqB := freqs(b)
	return contains(freqA, freqB) && contains(freqB, freqA)
}

func freqStringifyRoutine(word string, c chan string) {
	freq := freqs(word)
	c <- freqStringify(freq)
}

func freqStringify(freq map[rune]int) (acc string) {
	alphabet := "abcdefghijklmnopqrstuvwxyz"
	for _, letter := range alphabet {
		occurence := freq[letter]
		acc = fmt.Sprintf("%s%s", acc, strings.Repeat(string(letter), occurence))
	}
	return
}

// Perf summary
// With go routine for 40K words :
// 0.21user 0.02system 0:00.21elapsed 109%CPU
// Vanilla NO routine for 40K :
// 0.25user 0.04system 0:00.23elapsed 126%CPU
func main() {
	wordsByFrequencies := make(map[string][]string)
	file, _ := os.Open("dictionary.txt")
	b, _ := ioutil.ReadAll(file)
	words := strings.Split(string(b), "\n")

	for _, word := range words {
		c := make(chan string)
		go freqStringifyRoutine(word, c)

		freqStr := <-c
		wordsByFrequencies[freqStr] = append(wordsByFrequencies[freqStr], word)
	}
	for _, val := range wordsByFrequencies {
		anagramFamilyStr := fmt.Sprintf("%d ", len(val))
		for _, anagramWord := range val {
			anagramFamilyStr = fmt.Sprintf("%s %s", anagramFamilyStr, anagramWord)
		}

		fmt.Println(anagramFamilyStr)
	}
}
