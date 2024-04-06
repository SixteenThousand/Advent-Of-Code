#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

#define NUM_LINES 140
#define LINE_LENGTH 140

typedef struct {
	int start_col;
	int end_col;
	int value;
} part_num;

typedef struct {
	int row;
	int col;
	char ch;
} symbol;

part_num parts[NUM_LINES][LINE_LENGTH];
int row_lengths[NUM_LINES];
symbol symbols[NUM_LINES * LINE_LENGTH];

int width(int n) {
	// the length of the base 10 string representation of a given integer n
	int result = 1;
	int ubound = 10;
	while(ubound < n) {
		++result;
		ubound *= 10;
	}
	return result;
}

void get_engine(char *path, ENGINE_PART *buffer[LINE_LENGTH]) {
	FILE *fp, *fopen();
	fp = fopen(path,"r");
	char c;
	int row = 0, col = 0, value, width(), data_row_width = 0;
	while((c = getc(fp)) != EOF) {
		if(c == '\n') {
			++row;
			col = 0;
			continue;
		}
		if(isdigit(c)) {
			ungetc(c,fp);
			fscanf(fp,"%d",&value);
			printf("v:%d, ",value); // debug
			printf("len:%d\n",width(value)); // debug
		}
		++col;
	}
}

int main() {
	ENGINE_PART data[NUM_LINES][LINE_LENGTH];
	get_engine("../zeno.txt");
}
