#include <stdio.h>
#include <stdlib.h>

#define LINE_LEN 100

typedef LOCATION struct {
	long address;
	long value;
};

LOCATION memory[1000];
int memory_size = 0;
char line[LINE_LEN];
long ERROR = -1;

long *get_loc_value(long address) {
	for(int i=0; i<memory_size; i++) {
		if(memory[i].address == address) {
			return &memory[i].value;
		}
	}
	return &ERROR;
}

void add_loc(long address, long value) {
	for(int i=0; i<memory_size; i++) {
		if(memory[i].address == address) {
			memory[i].value = value;
			return;
		}
	}
	memory[memory_size].address = address;
	memory[memory_size].value = value;
	memory_size++;
}

long long sum() {
	long long result = 0;
	for(int i=0; i<memory_size; i++) {
		result += memory[i].value;
	}
	return result;
}
	

long long run(char *path) {
	char mask[LINE_LEN];
	long mask0;
	long mask1;
	long power;
	long address;
	long number;
	FILE *fp, *fopen();
	fp = fopen(path,"r");
	while (feof(fp) != 0) {
		fgets(line,LINE_LEN,fp);
		if(line[1] == 'a') {
			// mask stuff
			sscanf(line,"mask = %s",&mask);
		} else if(line[1] == 'e') {
			// mem stuff
			sscanf(line,"mem[%d] = %d",&address,&number);
			number = number | mask1;
			number = number & mask0;
			memory[
		} else {
			printf("An error has occurred.\nYou wrote this program badly."); 
		}
	}
	return 0;
}


int main() {
	run("../zeno.txt");
	printf("%d\n",sum()); 
}
