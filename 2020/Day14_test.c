#include <stdio.h>
#include <assert.h>
#include <string.h>
#include "./Day14.c"


void icecream(char* s) {
    printf(">>%s<<\n",s);
}

int longtobinstr_test() {
    char* message = malloc(500);
    *message = '\0';
    char* tmp_msg = malloc(100);
    int has_erred = 0; // a "boolean"
    char *buffer = malloc(70);
    long number;
    int width;
    typedef struct {
        long number;
        int width;
        char* expected;
    } Example;
    Example examples[] = {
        { 0, 1, "0" },
        { 1, 1, "1" },
        { 4, 3, "100" },
        { 67, 7, "1000011" },
        { 7, 5, "00111" },
        { -1, -1, ""} // this acts like a \0; it is not an actual example
    };
    int num_examples = 0;
    while(examples[num_examples].number >= 0) num_examples++;
    for(int i =0; i<num_examples; i++) {
        longtobinstr(examples[i].number,buffer,examples[i].width);
        if(strcmp(buffer,examples[i].expected) != 0) {
            has_erred = 1;
            strcat(message,"Case >>\n");
            sprintf(
                tmp_msg,
                "  number: <%ld>\n  exp: <%s>\n  got: <%s>\n",
                examples[i].number,
                examples[i].expected,
                buffer
            );
            strcat(message,tmp_msg);
            strcat(message,"<< failed!\n");
        }
    }
    // cleanup
    free(buffer);
    if(has_erred) printf("%s\n",message);
    free(message);
    free(tmp_msg);
    return has_erred;
}

typedef struct {
    int (*func)();
    char name[50];
} Test;

int dummy() { return -1; }

int main() {
    Test tests[] = {
        { &longtobinstr_test, "longtobinstr", },
        { &dummy, "", },
    };
    int i = 0;
    int num_failed = 0;
    while(tests[i].name[0] != '\0') {
        if((tests[i].func)()) {
            printf("\x1b[1;31mTest >>%s<< failed!\x1b[0m\n",tests[i].name);
            num_failed++;
        }
        i++;
    }
    if(num_failed == 0) printf("\x1b[1;32mAll tests passed!\x1b[0m\n");
    else printf("\x1b[1;31m%d\x1b[0m of \x1b[1m%d\x1b[0m tests failed!\n",num_failed,i);
}
