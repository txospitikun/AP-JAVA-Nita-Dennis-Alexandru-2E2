#include <8051.h>

int main()
{
    unsigned char x;
    x = 1;
    while(1)
    {
        x = x << 1;
        if(x == 0)
        {
            x = 1;
            
        }
        P1 = ~x;
    }

    return 0;
}