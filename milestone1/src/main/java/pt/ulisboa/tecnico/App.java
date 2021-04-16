package pt.ulisboa.tecnico;


import pt.ulisboa.tecnico.cryptography.Crypto;
import pt.ulisboa.tecnico.cryptography.DiffieHellman;

import javax.crypto.SecretKey;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.out.println("hello world");

/*
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
*/
        //Test grid
        ArrayList<String> users = new ArrayList<>();
        users.add("user1");
        users.add("user2");
        users.add("user3");
        users.add("user4");
        users.add("user5");
        ArrayList<String> by = new ArrayList<>();
        by.add("user6");
        by.add("user7");
        by.add("user8");


        Grid g = new Grid(4,4,users,by);
        g.getGrid();
        System.out.println("current position" + g.getUserPosition("user1"));
        System.out.println("Nesr users " +g.getNearUsers("user1"));
        g.getGrid();


        g.newUser("user10",20,0);
        System.out.println("current position" + g.getUserPosition("user10"));
        g.updateGrid();
        System.out.println("" +
                "" +
                "" +
                "" +
                "");
        g.getGrid();







    }
}
