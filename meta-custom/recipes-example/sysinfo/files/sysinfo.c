#include <stdio.h>
#include <string.h>

int main(){
    FILE *fp;
    char buffer[256];

    fp = fopen("/proc/cpuinfo", "r");

    if (fp == NULL) {
        perror("Failed to open /proc/cpuinfo");
        return 1;
    }

    printf("=== CPU Info ===\n");

    while (fgets(buffer, sizeof(buffer), fp)) {
        if (strstr(buffer, "processor")   ||
            strstr(buffer, "CPU architecture") ||
            strstr(buffer, "CPU variant") ||
            strstr(buffer, "CPU part") ||
            strstr(buffer, "CPU revision") ||
            strstr(buffer, "BogoMIPS")) {
            printf("%s", buffer);
        }
    }

    fclose(fp);

    return 0;
}
