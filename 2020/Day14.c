#include <stdio.h>
#include <stdlib.h>

#define LINE_LEN 100

typedef struct {
    long address;
    long value;
} LOCATION;

LOCATION memory[1000];
int memory_size = 0;
char line[LINE_LEN];
long DEFAULT_LOC_VALUE = -1; // pretty please don't change this, OK?

long *get_val_ref(long address) {
    for(int i=0; i<memory_size; i++) {
        if(memory[i].address == address) {
            return &memory[i].value;
        }
    }
    return &DEFAULT_LOC_VALUE;
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
    long power = 1;
    long address;
    long number;
    FILE *fp, *fopen();
    fp = fopen(path,"r");
    while (feof(fp) == 0) {
        fgets(line,LINE_LEN,fp);
        // this next bit is a little hacky, but hey, it works!
        if(line[1] == 'a') {
            // mask stuff
            sscanf(line,"mask = %s",&mask);
            int i = 0;
            while(mask[i] != '\0' && mask[i] != EOF) {
                switch(mask[i]) {
                    case 'X':
                        mask0 += power;
                        break;
                    case '1':
                        mask1 += power;
                        mask0 += power;
                        break;
                    case '0':
                        break;
                    default:
                        printf("Error in text processing:\n<<%c>>",mask[i]);
                        abort();
                }
                i++;
                power <<= 1;
            }
            printf("given: %s\nmask0: %d\nmask1: %d\n",mask,mask0,mask1);  // debug
        } else if(line[1] == 'e') {
            // mem stuff
            sscanf(line,"mem[%d] = %d",&address,&number);
            number = number | mask1;
            number = number & mask0;
            add_loc(address,number);
        } else {
            printf("An error has occurred.\nYou wrote this program badly."); 
        }
    }
    fclose(fp);
    return 0;
}

void longtobinstr(long num, char *binstr, int width) {
    // converts a given number into a string, in binary form
    // num: the given number
    // binstr: a character array. this is where the string will be put
    // width: the expected width of num in binary, at most the size of binstr 
    // minus one. if num is too big for its binary form to fit in
    // width characters, then only the first {width}th characters will be 
    // included. if it is smaller, there will be leading zeroes.
    *(binstr + width) = '\0';
    char *bufref = binstr + width - 1;
    while(bufref - binstr >= 0) {
        if(num & 1) {
            *bufref = '1';
        } else {
            *bufref = '0';
        }
        --bufref;
        num >>= 1;
    }
}


int main() {
    run("../zeno.txt");
    printf("%d\n",sum());
}
