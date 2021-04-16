package pt.ulisboa.tecnico.client;


import ...;

public class ClientServerConnection {
    static final String URI = "http://localhost:8080"; // Base location
    static void submitLocationReport(ClientProve Prove) {
        RestTemplate clientProveJSONTemplate = new RestTemplate().postForObject(URI + "/submitLocationReport",
                                                                                prove, ClientProve.Class);
    }
}
