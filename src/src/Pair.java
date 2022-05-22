package src;

/**
 * Pair of Symbols and their positions in the level map.
 **/
public class Pair {

    private char symbol;
    private Position position;

    public Pair(char symbol, Position position) {
        this.symbol = symbol;
        this.position = position;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
