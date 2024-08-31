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
        { -1, -1, ""} // this acts like a \0; it not an actual example
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
    else printf("All tests passed!\n");
    free(message);
    free(tmp_msg);
    return has_erred;
}


int main() {
    longtobinstr_test();
}
