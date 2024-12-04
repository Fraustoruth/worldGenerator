package byow.Core;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object p) {
        if (p != null) {
            if (p instanceof Position) {
                Position pos = (Position) p;
                return pos.x == this.x && this.y == pos.y;
            }
        }
        return false;
    }

}
