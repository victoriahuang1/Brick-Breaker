import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class LevelReader {
    private static BufferedReader br;
    private static String line;
    
    LevelReader(){
        try {
            br = new BufferedReader(new FileReader("levels.txt"));
        } catch (FileNotFoundException e) {
        }
    }

    private static Brick strToBrick(char ch, int x, int y){
        int hits = Integer.parseInt(((Character)ch).toString());
        if(hits >= 1 && hits <= 3){
            return new Brick(x, y, hits);
        }
        return null;
    }
    
    // lvl,12332112332112332112312312313....12132123132132132
    public static Brick[][] getBricks(int lvl){
        String bricks_str;
        Brick[][] bricks = new Brick[15][10];
        int count = 0;
        try {
            br = new BufferedReader(new InputStreamReader
                    (LevelReader.class.getResourceAsStream("levels.txt")));
            //br = new BufferedReader(new FileReader("levels.txt"));
            while((line = br.readLine()) != null){
                if(! line.equals("") && Integer.parseInt(((Character)line.charAt(0)).toString()) == lvl){
                    bricks_str = line.substring(2);
                    for(int r = 0; r < 15; r++){
                        for(int c = 0; c < 10; c++){
                            bricks[r][c] = strToBrick(bricks_str.charAt(count), c*Brick.WIDTH, r*Brick.HEIGHT);
                            count++;
                        }
                    }
                }
            }
        } catch (IOException e) {
        }
        return bricks;
    }
    
    // get drop rate for powerups
}
