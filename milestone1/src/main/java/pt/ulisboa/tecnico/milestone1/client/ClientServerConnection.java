package pt.ulisboa.tecnico.milestone1.client;

import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.HashMap;

public class ClientServerConnection {
    static final String URI = "http://localhost:8080"; // Base location

    static void submitLocationReport(ClientProve prove) {
        RestTemplate clientProveJSONTemplate = new RestTemplate();
        clientProveJSONTemplate.postForObject(URI + "/submitLocationReport",
                                                prove, ClientProve.class);
    }

    static void obtainLocationReport(String userNameToSearch, String epoch, String requestOwnerName, boolean isHa) {
       RestTemplate clientProveRequestLocationTemplate = new RestTemplate();
       Map<String, String> requestParameters = new HashMap<>();
       requestParameters.put("userId", userNameToSearch);
       requestParameters.put("epoch", epoch);
       requestParameters.put("callerUserId", requestOwnerName);
       requestParameters.put("isHa", Boolean.toString(isHa));
       clientProveRequestLocationTemplate.getForEntity(URI + "/obtainLocationReport/{userId}/{epoch}/{callerUserId}/{isHa}", null, requestParameters);
    }

    static void obtainUsersAtLocation(String pos, String epoch, boolean isHa) {
       RestTemplate clientProveRequestLocationTemplate = new RestTemplate();
       Map<String, String> requestParameters = new HashMap<>();
       requestParameters.put("pos", pos);
       requestParameters.put("epoch", epoch);
       requestParameters.put("isHa", Boolean.toString(isHa));
       clientProveRequestLocationTemplate.getForEntity(URI + "/obtainUsersAtLocation/{pos}/{epoch}/{isHa}", null, requestParameters);
    }
}
