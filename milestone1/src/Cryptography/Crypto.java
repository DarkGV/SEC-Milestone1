public class Crypto{
    private static String PATH_TO_CERTIFICATE = "path";
    private static String HMAC_ALGORITHM = "HmacSHA256";
    private static String CIFER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private SecretKey secretKey; //sera que nao faz mais sentido tornar as funçoes abstratas e passar isto como argumento?


    //VERIFY INTEGRITY
    public static String getMAC(String key_path, String message) throws Exception {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        mac.init(secretKey);
        return Base64.getEncoder().encodeToString(mac.doFinal(message.toString().getBytes()));
    }

    public static boolean checkIntegrity(String mac_in_message, String message) throws Exception{

        String check = getMAC(message) ;
        System.out.println("Mac in message " + mac_in_message + "Mac calculated "+ check);
        return  mac_in_message.equals(check);

    }


    //VERIFY FRESHNESS

    //encrypt
    public String[] encryptWithSecretKey(String plainText) {

        Cipher cipher = Cipher.getInstance(CIFER_ALGORITHM);
        System.out.println("Ciphering "+ plainText);

        byte[] iv = new byte[cipher.getBlockSize()];
        new SecureRandom().nextBytes(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

        String ivString = Base64.getEncoder().encodeToString(iv);
        String cipherText =  Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
        //String cipherText =  Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8))));

        System.out.println( ivString  + cipherText);
        return new String[] { ivString,cipherText };

    }


    //decrypt
    public String decryptWithSecretKey(String cipheredText, byte[] iv) {

        System.out.println("Decryptinh "+ cipheredTextText);
        Cipher cipher = Cipher.getInstance(CIFER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, this.secretKey, new IvParameterSpec(iv));
        String result = new String( cipher.doFinal(Base64.getDecoder().decode(cipheredText))) ;
        System.out.println("Decrypted: "+ result);
        return result;
    }


    //verify certificate
    public boolean verifyCertificate(String certificateToCheck, String trustedAnchor, String expectedCN) throws FileNotFoundException, CertificateException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        byte [] decoded = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(certificateToCheck.replaceAll("-----BEGIN CERTIFICATE-----\n", "").replaceAll("-----END CERTIFICATE-----", ""));
        Certificate certToCheck = CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(decoded));

        //Verify CN
        X509Certificate c = (X509Certificate)certToCheck;
        X509Principal principal = PrincipalUtil.getSubjectX509Principal(c);
        Vector<?> subjectCNs = principal.getValues(X509Name.CN);

        if(subjectCNs.get(0).equals(expectedCN)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            List list = new ArrayList();
            list.add(certToCheck);

            CertPath cp = cf.generateCertPath(list);
            FileInputStream in = new FileInputStream(trustedAnchor);
            Certificate trust = cf.generateCertificate(in);
            TrustAnchor anchor = new TrustAnchor((X509Certificate) trust, null);
            PKIXParameters params = new PKIXParameters(Collections.singleton(anchor));
            params.setRevocationEnabled(false);
            CertPathValidator cpv = CertPathValidator.getInstance("PKIX");
            PKIXCertPathValidatorResult result = null;

            try {
                result = (PKIXCertPathValidatorResult) cpv.validate(cp, params);
                return true;
            } catch (CertPathValidatorException e) {
                System.out.println("Invalid Certificate");
                return false;
            }
        } else
            return false;
    }



    public String requestConnection(){
        File crtFile = new File(PROJECT_PATH + PATH_TO_CERTIFICATE);
        String certificate = Files.readString(crtFile.toPath(), Charset.defaultCharset());
        String userPubKey = diffieUserPublicKey();
        //TO DO: send CERTIFICATE + USERPUBKEY

    }

}