const fs = require("node:fs");

// ++++++++++++ PART ONE ++++++++++++
const maxColours = {
	"r": 12,
	"g": 13,
	"b": 14
};

function isValidP1(line) {
	let re = / (\d+) (r|g|b).*/g;
	let match;
	for(x of line.split(";")) {
		for(y of x.split(",")) {
			match = re.exec(y);
			re.lastIndex = 0;
			if(parseInt(match[1]) > maxColours[match[2]]) {
				return 0;
			}
		}
	}
	return parseInt(line.match(/Game (\d+): /)[1]);
}

function part1(input) {
	try {
		let data = fs.readFileSync(input,"utf8").split("\n");
		data.pop(); // on windows readFileSync adds an empty line at the end
		let ans = data.map(isValidP1).reduce((acc,x) => acc + x,0);
		console.log(ans);
	} catch(err) {
		console.error(err);
	}
}


// part1("./Day2-input.txt");


// ++++++++++++ PART TWO ++++++++++++
function powerOfMin(line) {
	let re = / (\d+) (r|g|b)/g;
	let minimums = {
		"r": 0,
		"g": 0,
		"b": 0
	};
	let colour, num, match;
	for(cubes of line.split(/;|,/)) {
		match = re.exec(cubes);
		re.lastIndex = 0;
		colour = match[2];
		num = parseInt(match[1]);
		if(num > minimums[colour]) {
			minimums[colour] = num;
		}
	}
	return minimums.r * minimums.g * minimums.b;
}
			
function part2(input) {
	try {
		let data = fs.readFileSync(input,"utf8").split("\n");
		data.pop();
		console.log(data.map(powerOfMin).reduce((acc,x) => acc+x,0));
	} catch(err) {
		console.error(err);
	}
}

// console.log(powerOfMin("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"));
part2("./Day2-input.txt");
