package pt.ulisboa.tecnico;

import java.awt.*;
import java.util.*;
import java.util.List;


public class Grid {

    private int rows = 50;
    private int collumns = 50;
    private int maxDist =1;

    private Map<String, Point> userPositions= new HashMap<>();
    private ArrayList<String> grid[][] = new ArrayList[rows][collumns];
    private ArrayList<String> byzantineUsers = new ArrayList<>();


    public Grid (int rows, int collumns,  ArrayList<String> users, ArrayList<String> byzantineUsers) {
        this.rows = rows;
        this.collumns = collumns;
        this.byzantineUsers = byzantineUsers;

        for (String u : users) {
            addRandomPosition(u,false);


        }

    }
    public Grid (int rows, int collumns,  Map<String, Point> userPositions, ArrayList<String> byzantineUsers){
        this.rows=rows;
        this.collumns=collumns;
        this.userPositions=userPositions;
        this.byzantineUsers = byzantineUsers;

        for( String u : userPositions.keySet() ){
            Point p= userPositions.get(u);
            int posx = p.x;
            int posy = p.y;

            grid[posx][posy].add(u);


        }
    }
    public void updateGrid(){
        List<String> l = new ArrayList<String>(userPositions.keySet());
         for( String s : l)   {

             System.out.println(s);
             addRandomPosition(s, true);
         }


    }




    public Point getUserPosition(String user){
        return userPositions.get(user);

    }

    public ArrayList<String> getNearUsers(String user) {

        moveByzantine();

        ArrayList<String> nearUsers = new ArrayList<>();
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
                    for( String u : grid[r][c])
                        nearUsers.add(u);


            }
        }
        return nearUsers;
    }

    public void addRandomPosition(String user,boolean alreadyInGrid){
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
            grid[posx][posy]= new ArrayList<String>();
        grid[posx][posy].add(user);
        userPositions.put(user, new Point(posx, posy));
        System.out.println("added");


    }
    public void moveByzantine(){

        for (String u : byzantineUsers)
            if(userPositions.get(u)!= null)
                addRandomPosition(u, true);
            else
                addRandomPosition(u, false);

    }
    public void newUser(String user){
        addRandomPosition(user, false);
    }
    public void newUser(String user, int x, int y){
        if(x <rows & y< collumns) {
            if (grid[x][y] == null)
                grid[x][y] = new ArrayList<String>();
            
            grid[x][y].add(user);
            userPositions.put(user, new Point(x, y));
        } else
            System.out.println("Invalid position");

    }
    public void getGrid(){
        for(String u : userPositions.keySet())
            System.out.println("User: " + u + "position: "+ userPositions.get(u));


    }



}


