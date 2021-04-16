package pt.ulisboa.tecnico;


import pt.ulisboa.tecnico.cryptography.Crypto;
import pt.ulisboa.tecnico.cryptography.DiffieHellman;

import javax.crypto.SecretKey;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.out.println("hello world");


        // Diffie Helman test
        DiffieHellman user = new DiffieHellman();
        DiffieHellman server = new DiffieHellman();

        String userkey = user.diffieUserPublicKey();
        String serverkey = server.diffieServerPublicKey(userkey);

        SecretKey s = server.generateSharedSecret(userkey);
        SecretKey u = user.generateSharedSecret(serverkey);
        System.out.println("Secret user: " + u.equals(s));

        //Crypto test
        //encryption & decription

        String  message = "hello world";
        String [] enc = Crypto.encryptWithSecretKey(message, s);
        System.out.println("Encrypted: " + enc[0]+ " 1 "+ enc[1]);
        String dec = Crypto.decryptWithSecretKey(enc[1], enc[0], u);
        System.out.println("Decrypted: " + dec);


        //integrity
        String original_mac =Crypto.getMAC(message,s);
        //has to work
        System.out.println("Is Integer " + Crypto.checkIntegrity(original_mac,message,u));

        System.out.println("Not Integer " + Crypto.checkIntegrity(original_mac,"fake message",u));






    }
}
