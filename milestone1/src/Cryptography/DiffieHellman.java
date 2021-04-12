public class DiffieHellman{
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

    public void firstPhaseUser(String serverpubkey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        /*
         * User uses server's public key for the first (and only) phase
         * of her version of the DH
         * protocol.
         * Before she can do so, she has to instantiate a DH public key
         * from sever's encoded key material.
         */
        byte [] userPubKeyEnc= Base64.getDecoder().decode(serverpubkey);

        KeyFactory userKeyFac = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(serverPubKeyEnc);
        PublicKey serverPubKey = userKeyFac.generatePublic(x509KeySpec);
        userKeyAgree.doPhase(serverPubKey, true);
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
        serverKpairGen.initialize(dhParamFromuserPubKey);
        KeyPair serverKpair = serverKpairGen.generateKeyPair();

        // server creates and initializes his DH KeyAgreement object
        serverKeyAgree = KeyAgreement.getInstance("DH");
        serverKeyAgree.init(serverKpair.getPrivate());

        // Server encodes his public key, and sends it over to user.
        byte[] serverPubKeyEnc = serverKpair.getPublic().getEncoded();
        return Base64.getEncoder().encodeToString(serverPubKeyEnc);
    }


    public void firstPhaseServer(String userpubkey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        /*
         * Server uses user's public key for the first (and only) phase
         * of his version of the DH protocol.
         */
        byte [] userPubKeyEnc= Base64.getDecoder().decode(userpubkey);

        KeyFactory serverKeyFac = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(userPubKeyEnc);
        PublicKey userPubKey = serverKeyFac.generatePublic(x509KeySpec);

        serverKeyAgree.doPhase(userPubKey, true);
    }
    /*
     * At this stage, both hospital and lab have completed the DH key
     * agreement protocol.
     * Both generate the (same) shared secret.
     */
    public void generateSharedSecret(String serverpubkey) throws Exception {
        /*
         * Lab uses hospital's public key for the first (and only) phase
         * of his version of the DH protocol.
         */
        firstPhaseHospital(serverpubkey);
        byte[] userSharedSecret = userKeyAgree.generateSecret();

        // Creating a SecretKey object using the shared secret and use it for encryption.
        SecretKeySpec userAesKey = new SecretKeySpec(userSharedSecret, 0, 16, "AES");
        secretKey = userAesKey;
    }

}