#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <stdint.h>

#define BOOL(A) (A) == 0 ? "TRUE" : "FALSE"

long long adapters[200];
int adapterslen;
long long memo[200];

int main() {
	FILE *fopen(), *fp;
	void qsort(), printArr_i(), printArr_ll(), getData();
	int comp_ll(), comp_i();
	long long numCombinations();
	// get & sort the data
	extern long long adapters[];
	adapters[0] = 0;
	long long *adaptersStart = adapters + 1;
	getData(adaptersStart,&adapterslen,"./Day10-input.txt");
	qsort(adapters,adapterslen,sizeof(long long), comp_ll);
	for(int i=0; i<adapterslen; ++i) {
		memo[i] = -1;
	}
	
	printf("%lld\n",numCombinations(0)); 
	// printf("<<%d>>\n",INT_WIDTH);
}

long long numCombinations(int start) {
	extern long long adapters[];
	extern int adapterslen;
	
	if(memo[start] != -1)
		return memo[start];
	if(start == adapterslen-1)
		return 1;
	long long result = 0;
	int gap = 1;
	while(adapters[start+gap] - adapters[start] < 4 && 
			start+gap < adapterslen) {
		result += numCombinations(start+gap);
		++gap;
	}
	memo[start] = result;
	return result;
}
	

int comp_i(const void  *x, const void *y) {
	int x1 = *(const int*)x;
	int y1 = *(const int*)y;
	if(x1 < y1) return -1;
	if(x1 > y1) return 1;
	return 0;
}

int comp_ll(const void *x, const void *y) {
	long long x1 = *(const long long int*)x;
	long long y1 = *(const long long int*)y;
	if(x1 < y1) return -1;
	if(x1 > y1) return 1;
	return 0;
}

void printArr_i(int *arr, int arrlen) {
	printf("{ ");
	for(int i=0; i < arrlen; ++i) {
		printf("%d, ",*(arr+i));
		if(i%9 == 0 && i > 0) {
			printf("\n");
		}
	}
	printf("\b\b }\n");
}

void printArr_ll(long long *arr, int arrlen) {
	printf("{ ");
	for(int i=0; i < arrlen; ++i) {
		printf("%d, ",*(arr+i));
		if(i%9 == 0 && i > 0) {
			printf("\n");
		}
	}
	printf("\b\b }\n");
}

void getData(long long *buffer, int *buflen, char *path) {
	FILE *fp = fopen(path,"r");
	char c;
	char line[30];
	int lineIndex = 0, bufIndex = 0;
	while(1) {
		c = fgetc(fp);
	 	if(c == '\n') {
			*(line+lineIndex) = '\0';
			*(buffer+bufIndex) = atoll(line);
			lineIndex = 0;
			++bufIndex;
			continue;
		}
		if(c == EOF) {
			*(line+lineIndex) = '\0';
			*(buffer+bufIndex) = atoll(line);
			lineIndex = 0;
			++bufIndex;
			*buflen = bufIndex;
			break;
		}
		*(line+lineIndex) = c;
		++lineIndex;
	}
	fclose(fp);
}
