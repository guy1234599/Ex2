import java.util.Scanner;

public class bb {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int x = console.nextInt();
        if (x<=4||x>100000000){
            System.out.print("error");
            System.exit((4));
        }
        int p3 =x+2;
        int p4 = 2;
        boolean found = false;
        int n = x - 1;
        int j = x - n;
        int z=x;
        int maxInt = Integer.MAX_VALUE;
        int c=2;
        for (int i=0 ;i<maxInt;i++){
            if (!isPrime(p3)) {
                p3++;
                p4++;
            } else {
                if (!isPrime(p4)) {
                    p3++;
                    p4++;
                } else {
                    System.out.println(p3 + " - " + p4+ " = " + x);
                    found = true;
                    break;
                }
            }

        }
}
    public static boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number == 2) return true;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;

    }}
