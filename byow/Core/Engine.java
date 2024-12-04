package byow.Core;

import byow.InputDemo.InputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import jh61b.junit.In;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;
import edu.princeton.cs.introcs.StdDraw.*;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static long SEED;
    private static Random RANDOM;
    private static HashSet<Position> POSITIONS = new HashSet<>();
//    private static HashMap<Integer, Boolean> POSITIONS = new HashMap<Integer, Boolean>();
    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.


        long number = convertStringToLong(input);
        setSeed(number);
        return startWorld();

    }

    public TETile[][] startWorld() {

//        TERenderer ter = new TERenderer();
//        ter.initialize(WIDTH, HEIGHT);
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        fillWithEmptyTiles(finalWorldFrame);
        for (int i = 0; i < getNumRooms(); i += 1) {
            addRoom(finalWorldFrame);
        }
        //ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;

    }
    private static long convertStringToLong(String input) {
        String s = input.substring(1, input.length() - 1);
        Long d = Long.parseLong(s);
        return d;
    }

    private static void setSeed(long seed) {
        SEED = seed;
        setRANDOM(SEED);
    }

    public static void setRANDOM(long seed) {
        RANDOM = new Random(seed);
    }
    private static class Room {
        Position position;
        int width;
        int height;

        Room(Position p, int w, int h) {
            this.position = p;
            this.width = w;
            this.height = h;
        }
    }


    private static int getRandomWidth() {
        return RandomUtils.uniform(RANDOM, 3, 10);
    }

    private static Position getRandomPosition(int width, int height) {
        int xCoordinate = RandomUtils.uniform(RANDOM,1, WIDTH - width - 1);
        int yCoordinate = RandomUtils.uniform(RANDOM, 1, HEIGHT - height - 1);
        Position position = new Position(xCoordinate, yCoordinate);
        return position;
    }

    private static int getNumRooms() {
        return RandomUtils.uniform(RANDOM, 30, 40);
    }

    private static int getRandomHeight() {
        return RandomUtils.uniform(RANDOM, 2, 8);
    }

    public static void fillWithEmptyTiles(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                Position p = new Position(x, y);
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static boolean checkOverlap(Position p) {
        if (!POSITIONS.contains(p)) {
            return false;
        }
        return true;
    }

    public static boolean checkRoom(Room r, TETile[][] tiles) {
        for (int x = r.position.x - 1; x < r.width + r.position.x + 1; x += 1) {
            for (int y = r.position.y - 1; y < r.height + r.position.y + 1; y += 1) {
                if (tiles[x][y] != Tileset.NOTHING) {
                    return true;
                }
//                Position p = new Position(x, y);
//                if (POSITIONS.contains(p)) {
//                    System.out.println("hi");
//                    return true;
//                }
            }
        }
        return false;
    }

    public static void addRoom(TETile[][] tiles) {
        int height = getRandomHeight();
        int width = getRandomWidth();
        Position position = getRandomPosition(width, height);
        Room r = new Room(position, width, height);
        while (checkRoom(r, tiles)) {
            // todo: same width/height?
            width = getRandomWidth();
            height = getRandomHeight();
            position = getRandomPosition(width, height);
            r = new Room(position, width, height);
        }
        for (int x = position.x; x < width + position.x; x += 1){
            for (int y = position.y; y < height + position.y; y += 1) {
                tiles[x][y] = Tileset.FLOOR;
                Position p = new Position(x, y);
                POSITIONS.add(p);
            }
        }
        addWall(r, tiles);
    }

    // Todo: check if tileset.floor is necessary
    public static void drawHorizontal(TETile[][] tiles, int lowerX, int upperX, int y, boolean right) {
        if (right) {
            tiles[lowerX][y] = Tileset.FLOOR;
            for (int x = lowerX + 1; x < upperX - 1; x += 1) {
                tiles[x][y] = Tileset.FLOOR;
                tiles[x][y + 1] = Tileset.WALL;
                tiles[x][y - 1] = Tileset.WALL;
            }
            tiles[upperX][y] = Tileset.FLOOR;
        } else {
            tiles[upperX][y] = Tileset.FLOOR;
            for (int x = upperX - 1; x > lowerX; x -= 1) {
                tiles[x][y] = Tileset.FLOOR;
                tiles[x][y+1] = Tileset.WALL;
                tiles[x][y-1] = Tileset.WALL;
            }
            tiles[lowerX][y] = Tileset.FLOOR;
        }
    }

    public static void drawVertical(TETile[][] tiles, int lowerY, int upperY, int x, boolean up) {
        if (up) {
            tiles[x][lowerY] = Tileset.FLOOR;
            for (int y = lowerY + 1; y < upperY - 1; y += 1) {
                tiles[x][y] = Tileset.FLOOR;
                tiles[x - 1][y] = Tileset.WALL;
                tiles[x + 1][y] = Tileset.WALL;
            }
            tiles[x][upperY] = Tileset.FLOOR;
        } else {
            tiles[x][upperY] = Tileset.FLOOR;
            for (int y = upperY - 1; y > lowerY; y -= 1) {
                tiles[x][y] = Tileset.FLOOR;
                tiles[x - 1][y] = Tileset.WALL;
                tiles[x + 1][y] = Tileset.WALL;
            }
            tiles[x][lowerY] = Tileset.FLOOR;
        }
    }

    public static void drawCorner(TETile[][] tiles, int x, int y, boolean up, boolean right) {
        tiles[x][y] = Tileset.FLOOR;
        if (up) {
            if (right) {
                tiles[x+1][y+1] = Tileset.WALL;
                tiles[x+1][y] = Tileset.WALL;
                tiles[x][y+1] = Tileset.WALL;

            } else {
                tiles[x-1][y+1] = Tileset.WALL;
                tiles[x-1][y] = Tileset.WALL;
                tiles[x][y+1] = Tileset.WALL;
            }
        } else {
            if (right) {
                tiles[x+1][y-1] = Tileset.WALL;
                tiles[x+1][y] = Tileset.WALL;
                tiles[x][y-1] = Tileset.WALL;
            } else {
                tiles[x-1][y-1] = Tileset.WALL;
                tiles[x-1][y] = Tileset.WALL;
                tiles[x][y-1] = Tileset.WALL;
            }
        }
    }

    public static void addWall(Room r, TETile[][] tiles) {
        Position position = r.position;
        int lowerX = position.x - 1;
        int lowerY = position.y - 1;
        int upperX = position.x + r.width;
        int upperY = position.y + r.height;
        int numDoors = RandomUtils.uniform(RANDOM, 1, 3);
        for (int i = lowerX; i < upperX; i +=1) {
            tiles[i][lowerY] = Tileset.WALL;
            tiles[i][upperY] = Tileset.WALL;
        }
        for (int h = lowerY; h <= upperY; h += 1) {
            tiles[lowerX][h] = Tileset.WALL;
            tiles[upperX][h] = Tileset.WALL;
        }
    }

//    public static void createDoor(int lowerX, int lowerY, int upperX, int upperY, TETile[][] tiles, int doors) {
//        for (int i = 0; i < doors; i += 1) {
//            int random = RandomUtils.uniform(RANDOM, 2);
//            if (random == 0) {
//                int x = RandomUtils.uniform(RANDOM, lowerX + 1, upperX - 1);
//                int r = RandomUtils.uniform(RANDOM, 2);
//                if (r == 0) {
//                    tiles[x][lowerY] = Tileset.FLOOR;
//                } else {
//                    tiles[x][upperY] = Tileset.FLOOR;
//                }
//            }
//            else {
//                int y = RandomUtils.uniform(RANDOM, lowerY + 1, upperY - 1);
//                int r = RandomUtils.uniform(RANDOM, 2);
//                if (r == 0) {
//                    tiles[lowerX][y] = Tileset.FLOOR;
//                }
//                else {
//                    tiles[upperX][y] = Tileset.FLOOR;
//                }
//            }
//        }
//    }


//    public static void main(String[] args) {
////        TERenderer ter = new TERenderer();
////        ter.initialize(WIDTH, HEIGHT);
////        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
////        fillWithEmptyTiles(finalWorldFrame);
////        for (int i = 0; i < getNumRooms(); i += 1) {
////            addRoom(finalWorldFrame);
////        }
////        ter.renderFrame(finalWorldFrame);
//        Engine engine = new Engine();
//        engine.interactWithInputString("n5197880843569031643s");
//    }


}

