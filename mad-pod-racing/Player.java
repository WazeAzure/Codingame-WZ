import java.util.*;

import java.io.*;
// import java.math.*;
import java.lang.Math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

class Pair {
    public int x;
    public int y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
}

class Player {
    public static int currentX = 0;
    public static int currentY = 0;

    static boolean isChanged(int x, int y){
        return (x != currentX) || (y != currentY);
    }

    static double getDistance(int x1, int y1, int x2, int y2){
        return Math.sqrt((x2 - x1)*(x2-x1) + (y2-y1)*(y2-y1));
    }

    static boolean isInRegion(int x, int y, int ox, int oy, int r){
        return (ox - r <= x && x <= ox + r) && (oy - r <= y && y <= oy + r);
    }

    static Pair newXY(int x1, int y1, int x2, int y2){
        int magnitude = (int) Math.sqrt(Math.pow((double) (x2 - x1), 2) + Math.pow((double)(y2 - y1), (double)2));
        int tempX = (x2 - x1) * 600 / magnitude;
        int tempY = (y2 - y1) * 600 / magnitude;
        Pair ans = new Pair(tempX, tempY);
        return ans;
    }

    public static void main(String args[]) {
        Vector<Pair> vp = new Vector<Pair>(0);

        int laps = 1;
        int pods = 0;

        Scanner in = new Scanner(System.in);
        boolean boost = false;
        int i = 0;
        int currentTick = 0;
        boolean acc = false;
        // game loop
        while (i >= 0) {
            
            int x = in.nextInt();
            int y = in.nextInt();
            int nextCheckpointX = in.nextInt(); // x position of the next check point
            int nextCheckpointY = in.nextInt(); // y position of the next check point
            int nextCheckpointDist = in.nextInt(); // distance to the next checkpoint
            int nextCheckpointAngle = in.nextInt(); // angle between your pod orientation and the direction of the next checkpoint
            int opponentX = in.nextInt();
            int opponentY = in.nextInt();
            int thrust = 0;

            if(i==0){
                // first pods ticks
                Pair p = new Pair(x, y);
                vp.add(p);

                currentX = x;
                currentY = y;
            }

            

            if(laps == 1){
                if(isChanged(nextCheckpointX, nextCheckpointY)){
                    pods += 1;
                    
                    Pair p = new Pair(currentX, currentY);
                    vp.add(p);

                    currentX = nextCheckpointX;
                    currentY = nextCheckpointY;

                    if(isInRegion(nextCheckpointX, nextCheckpointY, vp.get(0).x, vp.get(0).y, 600)){
                        pods = 1;
                        laps += 1;
                    }
                }

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");
                if(nextCheckpointAngle > 30 || nextCheckpointAngle < -30){
                    thrust = 50;
                } else {
                    thrust = 100;
                }

                // if(isChanged(nextCheckpointX, nextCheckpointY)){
                if(nextCheckpointDist < 1000){
                    currentTick = i;
                    // currentX = nextCheckpointX;
                    // currentY = nextCheckpointY;
                    acc = true;
                }

                if(i - currentTick >= 0 && i - currentTick < 3 && acc){
                    thrust = 80 - (8 * (i-currentTick));
                } else {
                    acc = false;
                }

                
                System.err.println(nextCheckpointDist);

                // You have to output the target position
                // followed by the power (0 <= thrust <= 100) or "BOOST"
                // i.e.: "x y thrust"

                // attack mechanism
                // if(getDistance(x, y, opponentX, opponentY) <= 400 && !boost){
                //     System.out.println(opponentX + " " + opponentY + " BOOST");
                //     boost = true;
                // } else 
                if(!boost){
                    System.out.println(nextCheckpointX + " " + nextCheckpointY + " BOOST");
                    boost = true;
                } else {
                    System.out.println(nextCheckpointX + " " + nextCheckpointY + " " + thrust);
                }
            } else {
                // exquisite move
                for(int awikwok=0; awikwok<vp.size(); awikwok++){
                    System.err.printf("(%d %d)\n", vp.get(awikwok).x, vp.get(awikwok).y);
                }
                System.err.printf("\n");
                System.err.printf("%d %d %d %d\n", nextCheckpointX, nextCheckpointY, vp.get((pods + 1) % vp.size()).x, vp.get((pods + 1) % vp.size()).y);
                Pair temp = newXY(nextCheckpointX, nextCheckpointY, vp.get((pods + 1) % vp.size()).x, vp.get((pods + 1) % vp.size()).y);
                int newCheckpointX = nextCheckpointX + temp.x;
                int newCheckpointY = nextCheckpointY + temp.y;

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");
                if(nextCheckpointAngle > 30 || nextCheckpointAngle < -30){
                    thrust = 50;
                } else {
                    thrust = 100;
                }

                if(isChanged(nextCheckpointX, nextCheckpointY)){
                    currentTick = i;
                    // currentX = nextCheckpointX;
                    // currentY = nextCheckpointY;
                    acc = true;
                }

                if(i - currentTick >= 0 && i - currentTick < 4 && acc){
                    thrust = 65 - (5 * (i-currentTick));
                } else {
                    acc = false;
                }

                if(!boost){
                    System.out.println(newCheckpointX + " " + newCheckpointY + " BOOST");
                    boost = true;
                } else {
                    System.out.println(newCheckpointX + " " + newCheckpointY + " " + thrust);
                }
            }

            i++;
            System.err.println("laps: " + laps + "\npods: " + pods + "\n");
        }
    }
}