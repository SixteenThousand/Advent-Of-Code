readInts :: String -> [Int]
readInts s = [read line | line <- (lines s)]

prod :: [Int] -> Int
prod [] = 1
prod (x:xs) = x * (prod xs)

findSumPair :: [Int] -> Int -> [Int]
findSumPair [] sum = []
findSumPair [_] sum = []
findSumPair (x:xs) sum = if (sum-x) `elem` xs
	then [x,sum-x]
	else findSumPair xs sum

findSumTriplet :: [Int] -> Int -> [Int]
findSumTriplet [] sum = []
findSumTriplet [_] sum = []
findSumTriplet [_,_] sum = []
findSumTriplet (x:xs) sum = if (findSumPair xs $ sum-x) /= []
	then x:(findSumPair xs (sum-x))
	else findSumTriplet xs sum

main = readFile "./Day1-input.txt" >>=
		\x -> print $ prod $ findSumTriplet (readInts x) 2020
