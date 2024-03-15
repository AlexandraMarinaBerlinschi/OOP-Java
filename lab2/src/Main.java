import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        for(int i=1;i<=99;i++)
        {
            if(i%3==0) System.out.println(i);
        }

        Scanner Obj = new Scanner(System.in);
        String val1 = Obj.nextLine();
        String val2 = Obj.nextLine();
        String val3 = Obj.nextLine();
        int val4 = Math.max(Integer.parseInt(val2),Integer.parseInt(val1));
        System.out.println(Math.max(Integer.parseInt(val3), val4));

        String n = Obj.nextLine();
        System.out.println(fib(Integer.parseInt(n) - 1));
    }

    public static int fib(int n)
    {
        if(n == 0)
        {
            return 0;
        }
        if(n <= 2)
        {
            return 1;
        }
        return fib(n-1) + fib(n-2);
    }
}

