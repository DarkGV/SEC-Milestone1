import java.awt.*;
import java.util.*;


public class Grid {

    private int rows = 50;
    private int collumns = 50;
    private int maxDist =2;

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




    public Point getUserPosition(String user){
        return userPositions.get(user);

    }

    public ArrayList<String> getNearUsers(String user) {

        for (String u : byzantineUsers)
            addRandomPosition(u, true);

        ArrayList<String> nearUsers = new ArrayList<>();
        Point p = userPositions.get(user);
        int posx = p.x;
        int posy = p.y;

        for (int r = p.x - maxDist; r < rows; r++) {
            if (r < 0)
                r = 0;
            for (int c = p.y - maxDist; c < collumns; c++) {
                if (c < 0)
                    c = 0;
                for( String u : grid[r][c])
                    nearUsers.add(u);


            }
        }
        return nearUsers;
    }

    public void addRandomPosition(String user,boolean byzantine){
        Random rand = new Random();
        int posx = rand.nextInt(collumns);
        int posy = rand.nextInt(rows);
        if(byzantine){
            userPositions.remove(user);
            Point p= userPositions.get(user);
            int prevx = p.x;
            int prevy = p.y;
            grid[prevx][prevy].remove(user);

        }
        grid[posx][posy].add(user);
        userPositions.put(user, new Point(posx, posy));


    }





}


