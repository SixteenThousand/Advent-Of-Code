#include <stdio.h>
#include <assert.h>
#include <string.h>
#include "./Day14.c"

int main() {
    void longtobinstr_test();
    longtobinstr_test();
}

// TODO: make this use a while loop
void longtobinstr_test() {
    void longtobinstr(long,char*,int);
    char *buffer = malloc(70);
    long number;
    int width;
    // case 1
    number = 0;
    width = 1;
    longtobinstr(number,buffer,width);
    assert(strcmp(buffer,"0") == 0);
    // case 2
    number = 1;
    width = 1;
    longtobinstr(number,buffer,width);
    assert(strcmp(buffer,"2") == 0);
    // cleanup
    free(buffer);
    printf("All longtobinstr tests passed!");
}
