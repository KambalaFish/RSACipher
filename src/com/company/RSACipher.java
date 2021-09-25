package com.company;

import java.util.Scanner;

public class RSACipher {
    private final int d, n, fi;
    public RSACipher(int p, int q, int d){
        this.d = d;
        n = p*q;
        fi = (p-1)*(q-1);
    }

    private EuclidRow generalizedEuclidAlgorithm(int a, int b){
//        swap
        if (b>a){
            a = a + b;
            b = a - b;
            a = a - b;
        }
        EuclidRow u = new EuclidRow(a, 1, 0);
        EuclidRow v = new EuclidRow(b, 0, 1);
        EuclidRow t = new EuclidRow(0,0,0);

        while (v.gcd!=0){
            int q = u.gcd / v.gcd;
            t.gcd = u.gcd % v.gcd;
            t.a = u.a - q * v.a;
            t.b = u.b - q * v.b;
            u.set(v);
            v.set(t);
        }
        return u;
    }

    private int getC(){
        int c = generalizedEuclidAlgorithm(fi, d).b;
        if (c<0)
            return c + fi;
        return c;
    }

    private int calculatePowerByMod(int base, int power, int mod) {
        int result = 1;
        while (power > 0) {
            if ((power & 1) == 1)
                result = (result * base) % mod;
            base = (base * base) % mod;
            power = power >> 1;
        }
        return result;
    }

    public void encrypt(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please input message: ");
        int message = sc.nextInt();
        if (message>=n){
            System.out.println("Message must be less than P number");
            return;
        }
        int c = getC();
        int e = calculatePowerByMod(message, d, n);
        System.out.println("Alice wants to send Bob message: "+message);
        System.out.println("Alice takes Bob's open keys d and N and encrypts her message: e = message^d mod N = "+message+"^"+d+" mod "+n+" = "+e);
        System.out.println("Alice sends Bob e");
        System.out.println("Bob receives e from Alice and decrypts e using his secret key c = "+c+" to get Alice's message");
        int decryptedMessage = calculatePowerByMod(e, c, n);
        System.out.println("Bob calculates decryptedMessage = e^c mod N = "+e+"^"+c+" mod "+n+" = "+decryptedMessage);
    }
}
