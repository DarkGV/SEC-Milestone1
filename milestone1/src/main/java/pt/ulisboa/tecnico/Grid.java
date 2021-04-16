package pt.ulisboa.tecnico;

import pt.ulisboa.tecnico.milestone1.client.Client;

import java.awt.*;
import java.util.*;
import java.util.List;


public class Grid {


    private int epoch =0;
    private int rows = 50;
    private int collumns = 50;
    private int maxDist =1;

    private Map<Client, Point> userPositions= new HashMap<>();
    private ArrayList<Client> grid[][] = new ArrayList[rows][collumns];
    private ArrayList<Client> byzantineUsers = new ArrayList<>();


    public Grid (int rows, int collumns,  ArrayList<Client> users, ArrayList<Client> byzantineUsers) {
        this.rows = rows;
        this.collumns = collumns;
        this.byzantineUsers = byzantineUsers;

        for (Client u : users) {
            addRandomPosition(u,false);


        }

    }
    public Grid (int rows, int collumns,  Map<Client, Point> userPositions, ArrayList<Client> byzantineUsers){
        this.rows=rows;
        this.collumns=collumns;
        this.userPositions=userPositions;
        this.byzantineUsers = byzantineUsers;

        for( Client u : userPositions.keySet() ){
            Point p= userPositions.get(u);
            int posx = p.x;
            int posy = p.y;

            grid[posx][posy].add(u);


        }
    }
    public void updateGrid(){
        List<Client> l = new ArrayList<>(userPositions.keySet());
         for( Client s : l)   {

             System.out.println(s);
             addRandomPosition(s, true);
         }
         epoch++;


    }




    public Point getUserPosition(Client user){
        return userPositions.get(user);

    }

    public ArrayList<Client> getNearUsers(Client user) {

        moveByzantine();

        ArrayList<Client> nearUsers = new ArrayList<>();
        Point p = userPositions.get(user);
        int posx = p.x;
        int posy = p.y;

        for (int r = p.x - maxDist; r < p.x + maxDist && r<rows; r++) {
            if (r < 0)
                r = 0;
            for (int c = p.y - maxDist;c < p.y + maxDist && c < collumns; c++) {
                if (c < 0)
                    c = 0;
                if(grid[r][c] != null)
                    for( Client u : grid[r][c])
                        nearUsers.add(u);


            }
        }
        return nearUsers;
    }

    public void addRandomPosition(Client user,boolean alreadyInGrid){
        Random rand = new Random();
        int posx = rand.nextInt(rows);
        int posy = rand.nextInt(collumns);
        System.out.println("Columns "+posx+ "rows "+ posy);
        if(alreadyInGrid){
            Point p= userPositions.get(user);
            grid[p.x][p.y].remove(user);
            userPositions.remove(user);
        }
        if( grid[posx][posy] == null)
            grid[posx][posy]= new ArrayList<Client>();
        grid[posx][posy].add(user);
        userPositions.put(user, new Point(posx, posy));
        System.out.println("added");


    }
    public void moveByzantine(){

        for (Client u : byzantineUsers)
            if(userPositions.get(u)!= null)
                addRandomPosition(u, true);
            else
                addRandomPosition(u, false);

    }
    public void newUser(Client user){
        addRandomPosition(user, false);
    }
    public void newUser(Client user, int x, int y){
        if(x <rows & y< collumns) {
            if (grid[x][y] == null)
                grid[x][y] = new ArrayList<Client>();
            
            grid[x][y].add(user);
            userPositions.put(user, new Point(x, y));
        } else
            System.out.println("Invalid position");

    }
    public void getGrid(){
        for(Client u : userPositions.keySet())
            System.out.println("User: " + u + "position: "+ userPositions.get(u));


    }
    public String getEpoch() {
        return String.valueOf(epoch);
    }



}


