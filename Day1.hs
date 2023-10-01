find :: Eq a => [a] -> a -> Int
find [] target = -1
find range target = if maximum [if (range!!i) == target then i else -1 | i <- [1..(pred (length range))]]

myLines :: String -> [String]
myLines str = if '\n' `elem` str then ((take (find str '\n') str) : myLines (drop (succ (find str '\n')) str)) else str

test range target i = if (range!!i) == target then i else (length range)

-- main = readFile "./Day1-input.txt" >>= \input ->
-- 	print (myLines input)
poem = "Do not ask for whom the bell tolls\nIt tolls for thee."
main = print(lines poem)
