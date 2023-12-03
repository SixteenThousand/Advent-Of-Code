// note that the problem is silent on whether 0 should count as a digit or not,
// and in any case the problem's input has no 0's in it anyway
const fs = require('node:fs');

function getCalibration1(line) {
	let match1 = /\D*(\d)/.exec(line);
	let match2 = /.*(\d)\D*/.exec(line);
	return 10*parseInt(match1[1]) + parseInt(match2[1]);
}

let digits = [
	null,
	"one",
	"two",
	"three",
	"four",
	"five",
	"six",
	"seven",
	"eight",
	"nine"
];

function matchToI(m) {
	return m.length > 1 ? digits.indexOf(m) : parseInt(m);
}

function getCalibration2(line) {
	let re = /(one|two|three|four|five|six|seven|eight|nine|\d)/g;
	let d1 = matchToI(line.match(re)[0]);
	let i = line.length-1;
	let lastDigit;
	while(true) {
		lastDigit = line.slice(i).match(re);
		if(lastDigit !== null) {
			lastDigit = lastDigit[0];
			break;
		}
		i--;
	}
	let d2 = matchToI(lastDigit);
	return 10*d1 + d2;
}


function solution(input) {
	try {
		let data = fs.readFileSync(input,"utf8").split("\n");
		// for(let datum of data) {
		// 	console.log(`--${datum}--`);
		// }
		data.pop();
		// data = data.slice(900,data.length);
		let ans = data 
			.map(getCalibration2)
			.reduce((acc,x) => acc+x, 0);
		console.log(ans);
	} catch(err) {
		console.error(err);
	}
}

// ++++++++++++ PUT RUNNING CODE HERE ++++++++++++
solution("Day1-input.txt");
// console.log(getCalibration2("eighthree"));
