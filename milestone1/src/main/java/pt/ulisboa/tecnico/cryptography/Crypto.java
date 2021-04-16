package pt.ulisboa.tecnico.cryptography;

import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.PrincipalUtil;
import org.bouncycastle.jce.X509Principal;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.*;
import java.util.*;

public class Crypto {
    private static String PATH_TO_CERTIFICATE = "path";
    private static String HMAC_ALGORITHM = "HmacSHA256";
    private static String CIFER_ALGORITHM = "AES/CBC/PKCS5Padding";


    //VERIFY INTEGRITY
    public static String getMAC(String message, SecretKey secretKey) throws Exception {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        mac.init(secretKey);
        return Base64.getEncoder().encodeToString(mac.doFinal(message.toString().getBytes()));
    }

    public static boolean checkIntegrity(String mac_in_message, String message, SecretKey secretKey) throws Exception{

        String check = getMAC( message,  secretKey) ;
        System.out.println("Mac in message " + mac_in_message + "Mac calculated "+ check);
        return  mac_in_message.equals(check);

    }


    //VERIFY FRESHNESS

    //encrypt
    //result[0] is IV result[1] is the ciphered text
    public static String[] encryptWithSecretKey(String plainText, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException {

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
    public static String decryptWithSecretKey(String cipheredText, String iv_string, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException {

        System.out.println("Decryptinh "+ cipheredText);
        Cipher cipher = Cipher.getInstance(CIFER_ALGORITHM);

        byte [] iv = Base64.getDecoder().decode(iv_string);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        String result = new String( cipher.doFinal(Base64.getDecoder().decode(cipheredText))) ;
        System.out.println("Decrypted: "+ result);
        return result;
    }


    //verify certificate
    public static boolean verifyCertificate(String pathToCertificate, String trustedAnchor, String expectedCN) throws FileNotFoundException, CertificateException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
       // byte [] decoded = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(certificateToCheck.replaceAll("-----BEGIN CERTIFICATE-----\n", "").replaceAll("-----END CERTIFICATE-----", ""));
       // Certificate certToCheck = CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(decoded));
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate certToCheck = cf.generateCertificate(new FileInputStream(pathToCertificate));


        X509Certificate c = (X509Certificate)certToCheck;
        X509Principal principal = PrincipalUtil.getSubjectX509Principal(c);
        Vector<?> subjectCNs = principal.getValues(X509Name.CN);

        if(subjectCNs.get(0).equals(expectedCN)) {
            cf = CertificateFactory.getInstance("X.509");
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


/*
    public static String requestConnection(){
        File crtFile = new File(PROJECT_PATH + PATH_TO_CERTIFICATE);
        String certificate = Files.readString(crtFile.toPath(), Charset.defaultCharset());
        String userPubKey = diffieUserPublicKey();
        //TO DO: send CERTIFICATE + USERPUBKEY

    }*/

}