package pt.ulisboa.tecnico.milestone1.client;

import pt.ulisboa.tecnico.Grid;

import java.util.ArrayList;

public class Client {
    String userName;
    Grid clientWorld;
    private String path_to_cert;

    public Client(String userName, Grid clientWorld) {
        this.userName = userName;
        this.clientWorld = clientWorld;
    }

    public String getName() { return userName; }

    public void proveLocation() {
        ArrayList<String> signatures = new ArrayList<>();
        String currentPosition = clientWorld.getUserPosition(this).toString();
        String currentEpoch = clientWorld.getEpoch();
        ClientProve clientProveJSON = new ClientProve(userName, currentPosition, currentEpoch);

        for(Client nearClient : clientWorld.getNearUsers(this)) {
            clientProveJSON.addClientSignature(nearClient.getName(), nearClient.proveUserLocation());
        }

        ClientServerConnection.submitLocationReport(clientProveJSON);
    }

    public String proveUserLocation() {
        // Crypto signature
    }
}
