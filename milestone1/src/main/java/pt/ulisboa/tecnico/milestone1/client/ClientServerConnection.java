package pt.ulisboa.tecnico.milestone1.client;

import pt.ulisboa.tecnico.cryptography.Crypto;
import pt.ulisboa.tecnico.cryptography.DiffieHellman;

import javax.crypto.SecretKey;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

public class ClientServerConnection {
    static final String URI = "http://localhost:8080"; // Base location
    private DiffieHellman diffie;
    private SecretKey secretKey;

    static void submitLocationReport(ClientProve prove) {
        RestTemplate clientProveJSONTemplate = new RestTemplate();
        clientProveJSONTemplate.postForObject(URI + "/submitLocationReport",
                                                prove, ClientProve.class);
    }
     public boolean beginHandshake() throws Exception {
        File crtFile = new File(PROJECT_PATH + PATH_TO_CERTIFICATE);
        String certificate = Files.readString(crtFile.toPath(), Charset.defaultCharset());
        diffie = new DiffieHellman();
        String userPubKey = diffie.diffieUserPublicKey();
        RestTemplate clientProveRequestLocationTemplate = new RestTemplate();
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("certificate",certificate);
        requestParameters.put("key", userPubKey);
        String hsrep =clientProveRequestLocationTemplate.getForEntity(URI + "/obtainLocationReport/{certificate}/{key}", String, requestParameters);
        JsonObject repJson = JsonParser.parseString(hsrep).getAsJsonObject();
        String serverdiffiekey = requestJson.get("key").getAsString();
        if( serverdiffiekey ==null) {
            String mac = requestJson.get("mac").getAsString();


            // receive key
            secretkey = diffie.generateSharedSecret(serverdiffiekey);

            return checkIntegrity(mac, serverdiffiekey, secretkey);
        }else
            return false;



    }



    void obtainLocationReport(String userNameToSearch, String epoch, String requestOwnerName, boolean isHa) throws Exception {
        if( beginHandshake()  ) {
            RestTemplate clientProveRequestLocationTemplate = new RestTemplate();
            Map<String, String> requestParameters = new HashMap<>();
            JsonObject request = new JsonObject();

            JsonObject params = JsonParser.parseString("{}").getAsJsonObject();
            params.addProperty("userId", userNameToSearch);
            params.addProperty("epoch", epoch);
            params.addProperty("callerUserId", requestOwnerName);
            params.addProperty("isHa", Boolean.toString(isHa));
            params.add("time", LocalDateTime.now().format(formatter));

            String message = params.toString();

            request.addProperty("mac", Crypto.getMAC(message,secretKey));
            String[] enc = Crypto.encryptWithSecretKey(message,secretKey);
            request.addProperty("message", enc);

            requestParameters.put("data", request.toString());
            clientProveRequestLocationTemplate.getForEntity(URI + "/obtainLocationReport/{data}", null, requestParameters);


        }else
            System.out.println("Connection error");
    }

    void obtainUsersAtLocation(String pos, String epoch, boolean isHa) throws Exception {
        if( beginHandshake()  ) {

            RestTemplate clientProveRequestLocationTemplate = new RestTemplate();
            Map<String, String> requestParameters = new HashMap<>();
            JsonObject request = new JsonObject();

            JsonObject params = JsonParser.parseString("{}").getAsJsonObject();
            params.addProperty("pos", pos);
            params.addProperty("epoch", epoch);
            params.addProperty("isHa", Boolean.toString(isHa));
            params.add("time", LocalDateTime.now().format(formatter));

            String message = params.toString();

            request.addProperty("mac", Crypto.getMAC(message,secretKey));
            String[] enc = Crypto.encryptWithSecretKey(message,secretKey);
            request.addProperty("message", enc);

            requestParameters.put("data", request.toString());

            clientProveRequestLocationTemplate.getForEntity(URI + "/obtainUsersAtLocation/{data}", null, requestParameters);
        }else
            System.out.println("Connection error");
    }
}
