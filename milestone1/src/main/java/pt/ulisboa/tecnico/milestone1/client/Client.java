package pt.ulisboa.tecnico.milestone1.client;

import java.util.ArrayList;

public class Client {
    String userName;
    Grid clientWorld;

    public Client(String userName, Grid clientWorld) {
        this.userName = userName;
        this.clientWorld = clientWorld;
    }

    public String getName() { return userName; }

    public void proveLocation() {
        ArrayList<String> signatures = new ArrayList<>();
        String currentPosition = clientWorld.getLocation(userName);
        String currentEpoch = clientWorld.getEpoch();
        ClientProve clientProveJSON = new ClientProve(userName, currentPosition, currentEpoch);

        for(Client nearClient : clientWorld.getNearUsers()) {
            clientProveJSON.addClientSignature(nearClient.getName(), nearClient.proveUserLocation());
        }

        ClientServerConnection.submitLocationReport(clientProveJSON);
    }

    public String proveUserLocation() {
        // Crypto signature
    }
}
