package pt.ulisboa.tecnico.milestone1.client;

import java.util.ArrayList;

public class ClientProve {
    private class ClientLocationProve {
        String clientName;
        String clientSignature;

        public ClientLocationProve(String clientName, String clientLocation) {
            this.clientName = clientName;
            this.clientSignature = clientSignature;
        }

        public String getName() { return clientName; }
        public String getSignature() { return clientSignature; }
    }
    String userName;
    String userLocation;
    String userCurrentEpoch;
    ArrayList<ClientLocationProve> clientsProve;

    public ClientProve(String userName, String userLocation, String userCurrentEpoch) {
        this.userName = userName;
        this.userLocation = userLocation;
        this.userCurrentEpoch = userCurrentEpoch;
        clientsProve = new ArrayList<>();
    }

    public void addClientSignature(String clientName, String signature) {
        ClientLocationProve clientProve = new ClientLocationProve(clientName, signature);
        clientsProve.add(clientProve);
    }

    @Override
    public String toString() {
        String jsonData = "{ \"CurrentPos\": \"+ userLocation +\",\n\"userName\":\""+userName+"\",\n\"CurrentEpoch\":\""+userCurrentEpoch+"\",\n\"ClientProves\":[";
        for (ClientLocationProve prove : clientsProve) {
            jsonData += "{\"UserName\":\""+prove.getName()+"\",\n\"UserSignature\":\""+prove.getSignature()+"\"},\n";
        }
        jsonData += "]}";
    }
}
