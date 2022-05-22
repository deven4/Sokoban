package src;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Level {

    private Position player;
    private final Pair[][] map;
    private final int crateCount;

    public Level(Pair[][] map, Position player, int crateCount) {
        this.map = map;
        this.player = player;
        this.crateCount = crateCount;
    }

    public Pair[][] getMap() {
        return map;
    }

    public Position getPlayer() {
        return player;
    }

    public int getCrateCount() {
        return crateCount;
    }

    public void setPlayer(Position player) {
        this.player = player;
    }

    public void movePlayer(int direction) {
        int currI = player.getI();
        int currJ = player.getJ();
        Point playerNewPos = player.getCoordinates();
        if (!isSpaceAvailable(direction, currI, currJ)) return;

        switch (direction) {
            case KeyEvent.VK_DOWN -> {
                if (isCratePresent(currI + 1, currJ) &&
                        isSpaceAvailable(KeyEvent.VK_DOWN, currI + 1, currJ)) {
                    moveCrate(map[currI + 1][currJ], map[currI + 2][currJ]);
                }
                playerNewPos.y += 50;
                player.setI(currI + 1);
            }
            case KeyEvent.VK_UP -> {
                if (isCratePresent(currI - 1, currJ) &&
                        isSpaceAvailable(KeyEvent.VK_UP, currI - 1, currJ)) {
                    moveCrate(map[currI - 1][currJ], map[currI - 2][currJ]);
                }
                playerNewPos.y -= 50;
                player.setI(currI - 1);
            }
            case KeyEvent.VK_LEFT -> {
                if (isCratePresent(currI, currJ - 1) &&
                        isSpaceAvailable(KeyEvent.VK_LEFT, currI, currJ - 1)) {
                    moveCrate(map[currI][currJ - 1], map[currI][currJ - 2]);
                }
                playerNewPos.x -= 50;
                player.setJ(currJ - 1);
            }
            case KeyEvent.VK_RIGHT -> {
                if (isCratePresent(currI, currJ + 1) &&
                        isSpaceAvailable(KeyEvent.VK_RIGHT, currI, currJ + 1)) {
                    moveCrate(map[currI][currJ + 1], map[currI][currJ + 2]);
                }
                playerNewPos.x += 50;
                player.setJ(currJ + 1);
            }
        }
        player.setCoordinates(playerNewPos);
    }

    private void moveCrate(Pair currCrate, Pair nextCrate) {
        if (currCrate.getSymbol() == '$'
                && nextCrate.getSymbol() == '.') {
            currCrate.setSymbol('0');
            nextCrate.setSymbol('*');
        } else if (currCrate.getSymbol() == '*'
                && nextCrate.getSymbol() == '.') {
            currCrate.setSymbol('.');
            nextCrate.setSymbol('*');
        } else {
            currCrate.setSymbol('0');
            nextCrate.setSymbol('$');
        }
    }

    private boolean isCratePresent(int i, int j) {
        return map[i][j].getSymbol() == '$' ||
                map[i][j].getSymbol() == '*';
    }

    /**
     * Method that returns boolean, if there is available space to move the crate.
     **/
    private boolean isSpaceAvailable(int direction, int i, int j) {
        switch (direction) {
            case KeyEvent.VK_DOWN -> {
                Pair currBlock = map[i + 1][j];
                if (currBlock.getSymbol() != '#')
                    return isTwoCrateTogether(currBlock, map[i + 2][j]);
            }
            case KeyEvent.VK_LEFT -> {
                Pair currBlock = map[i][j - 1];
                if (currBlock.getSymbol() != '#')
                    return isTwoCrateTogether(currBlock, map[i][j - 2]);
            }
            case KeyEvent.VK_RIGHT -> {
                Pair currBlock = map[i][j + 1];
                if (currBlock.getSymbol() != '#')
                    return isTwoCrateTogether(currBlock, map[i][j + 2]);
            }
            case KeyEvent.VK_UP -> {
                Pair currBlock = map[i - 1][j];
                if (currBlock.getSymbol() != '#')
                    return isTwoCrateTogether(currBlock, map[i - 2][j]);
            }
        }
        return false;
    }

    private boolean isTwoCrateTogether(Pair currBlock, Pair nextBlock) {
        // If two blocks are together
        if (currBlock.getSymbol() == '$' && nextBlock.getSymbol() == '$'
                || currBlock.getSymbol() == '*' && nextBlock.getSymbol() == '*')
            return false;
        else if (currBlock.getSymbol() == '*'
                && nextBlock.getSymbol() == '#')
            return false;
        else if (currBlock.getSymbol() == '$' && nextBlock.getSymbol() == '*'
                || currBlock.getSymbol() == '*' && nextBlock.getSymbol() == '$')
            return false;
        else return currBlock.getSymbol() != '$' ||
                    nextBlock.getSymbol() != '#';
    }
}
