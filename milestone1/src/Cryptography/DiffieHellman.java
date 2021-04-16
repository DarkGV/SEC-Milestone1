package Cryptography;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class DiffieHellman {
    private KeyAgreement userKeyAgree;
    private KeyAgreement serverKeyAgree;
    private KeyAgreement ownKeyAgree = serverKeyAgree;
    private KeyAgreement otherKeyAgree = userKeyAgree;

    public DiffieHellman(boolean isUser){
        if(isUser) {
            ownKeyAgree = userKeyAgree;
            otherKeyAgree = serverKeyAgree;
        }
    }


    //KEY GENERATION
    public String diffieUserPublicKey() throws NoSuchAlgorithmException, InvalidKeyException {
        // User creates own DH key pair with 2048-bit key size
        KeyPairGenerator userKpairGen = KeyPairGenerator.getInstance("DH");
        userKpairGen.initialize(2048);
        KeyPair userKpair = userKpairGen.generateKeyPair();

        // User creates and initializes  DH KeyAgreement object
        userKeyAgree = KeyAgreement.getInstance("DH");
        userKeyAgree.init(userKpair.getPrivate());

        // User encodes public key, and sends it over to server.
        byte[] userPubKeyEnc = userKpair.getPublic().getEncoded();

        return Base64.getEncoder().encodeToString(userPubKeyEnc);
    }


    public String diffieServerPublicKey(String userpubkey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException {
        /*
         * Let's turn over to server. server has received user's public key
         * in encoded format.
         * He instantiates a DH public key from the encoded key material.
         *
         */
        byte [] userPubKeyEnc= Base64.getDecoder().decode( userpubkey);
        KeyFactory serverKeyFac = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(userPubKeyEnc);
        PublicKey userPubKey = serverKeyFac.generatePublic(x509KeySpec);

        /*
         * Server gets the DH parameters associated with user's public key.
         * He must use the same parameters when he generates his own key
         * pair.
         */
        DHParameterSpec dhParamFromUserPubKey = ((DHPublicKey) userPubKey).getParams();

        // Server creates his own DH key pair
        KeyPairGenerator serverKpairGen = KeyPairGenerator.getInstance("DH");
        serverKpairGen.initialize(dhParamFromUserPubKey);
        KeyPair serverKpair = serverKpairGen.generateKeyPair();

        // server creates and initializes his DH KeyAgreement object
        serverKeyAgree = KeyAgreement.getInstance("DH");
        serverKeyAgree.init(serverKpair.getPrivate());

        // Server encodes his public key, and sends it over to user.
        byte[] serverPubKeyEnc = serverKpair.getPublic().getEncoded();
        return Base64.getEncoder().encodeToString(serverPubKeyEnc);
    }


    public  void firstPhase(String otherpubkey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        /*
         * This entity uses the other party's public key for the first (and only) phase
         * of his version of the DH protocol.
         */
        byte [] otherPubKeyEnc= Base64.getDecoder().decode(otherpubkey);

        KeyFactory ownKeyFac = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(otherPubKeyEnc);
        PublicKey otherPubKey = ownKeyFac.generatePublic(x509KeySpec);

        ownKeyAgree.doPhase(otherPubKey, true);
    }



    /*
     * At this stage, both entities have completed the DH key
     * agreement protocol.
     * Both generate the (same) shared secret.
     */

    public  SecretKey generateSharedSecret(String otherPubKey) throws Exception {
        /*
         * The other entity uses own public key for the first (and only) phase
         * of his version of the DH protocol.
         */
        firstPhase(otherPubKey);
        byte[] ownSharedSecret = ownKeyAgree.generateSecret();

        // Creating a SecretKey object using the shared secret and use it for encryption.
        return new SecretKeySpec(ownSharedSecret, 0, 16, "AES");

    }

}