package pt.ulisboa.tecnico.client;

import ...;

private class ClientLocationProve {
    String clientName;
    String clientSignature;

    public ClientLocationProve(String clientName, String clientLocation) {
        this.clientName = clientName;
        this.clientSignature = clientSignature;
    }

    public String getName() { return clientName; }
    public String getLocation() { return clientSignature; }
}

public class ClientProve {
    String userName;
    String userLocation;
    String userCurrentEpoch;
    ArrayList<ClientLocationProve> clientsProve;

    public ClientProve(String userName, String userLocation, String userCurrentEpoch) {
        this.userName = userName;
        this.userLocation = userLocation;
        this.userCurrentEpoch = userCurrentEpoch;
        clientsProve = ArrayList<>();
    }

    public void addClientSignature(String clientName, String signature) {
        ClientLocationProve clientProve = new ClientLocationProve(clientName, signature);
        clientsProve.add(clientProve);
    }

    @Override
    public String toString() {
        jsonData = "{ \"CurrentPos\": \n";
        for
    }
}
