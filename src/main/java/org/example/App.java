package org.example;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final int PASSWORD_HASH_ITERATION = 2;
    public static final String PASSWORD_HASH_ALGO = "SHA-256";

    public static String hashPassword(String password, byte[] saltBytes) {
        try {
            byte[] passwordBytes = password.getBytes();
            MessageDigest digest = MessageDigest.getInstance(PASSWORD_HASH_ALGO);
            digest.update(saltBytes);
            byte[] tokenHash = digest.digest(passwordBytes);
            int iterations = PASSWORD_HASH_ITERATION - 1;
            for (int i = 0; i < iterations; i++) {
                digest.reset();
                tokenHash = digest.digest(tokenHash);
            }
            String tokenHashHex = Hex.encodeHexString(tokenHash);
            return tokenHashHex;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setPassword(String password){
        String hashPassword = null;
        String saltPassword = null;
        if(password!=null){
            Random random=new Random();
            byte[] saltBytes=new byte[16];
            random.nextBytes(saltBytes);
            hashPassword=hashPassword(password, saltBytes);
            saltPassword=Hex.encodeHexString(saltBytes);
            System.out.println("Your Password Hash "+hashPassword);
            System.out.println("Your Password Salt "+saltPassword);
        }else{
            System.out.println("Your Password is >>"+hashPassword);
            System.out.println("Your Salt is >>"+saltPassword);
        }
    }
    public static void main( String[] args )
    {
        Scanner scanner=new Scanner(System.in);
        System.out.println( "Insert Yourr Password!!" );
        String password=scanner.nextLine();
        setPassword(password);
    }
}
