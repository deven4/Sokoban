package src;

import src.Interfaces.onButtonClicked;
import src.Interfaces.onGameListener;
import src.Interfaces.onKeyBindListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static src.Constants.*;


public class GamePanel extends JPanel implements onKeyBindListener, onButtonClicked {

    private int padX, padY;
    private final Level level;
    private final Assets assets;
    private final int currentLvl;
    private onGameListener mListener;
    private boolean isMsgDisplayed = false;
    private final CustomButton backBtn, resetBtn;
    private final int unitSize = Constants.unitSize;

    GamePanel(int levelCode) {
        setFocusable(true);
        requestFocusInWindow();
        createKeyBindings();
        this.currentLvl = levelCode;
        assets = Assets.getInstance();
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        level = loadFile(GamePanel.class.getResourceAsStream("/Levels/"
                + levelCode + ".txt"));

        // Adding Buttons
        backBtn = new CustomButton(BACK_BTN, 26, unitSize - 20,
                unitSize - 20, this);
        resetBtn = new CustomButton(RESET_BTN, 26, SCREEN_WIDTH / 2 - unitSize,
                SCREEN_HEIGHT - unitSize - 10, this);
        backBtn.addButtonListener(this);
        resetBtn.addButtonListener(this);
    }

    public void addGameListener(onGameListener mListener) {
        this.mListener = mListener;
    }

    private void createKeyBindings() {
        int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
        this.getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), MoveAction.UP);
        this.getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), MoveAction.DOWN);
        this.getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), MoveAction.LEFT);
        this.getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), MoveAction.RIGHT);

        // Action Map
        this.getActionMap().put(MoveAction.UP, new MoveAction(KeyEvent.VK_UP, this));
        this.getActionMap().put(MoveAction.DOWN, new MoveAction(KeyEvent.VK_DOWN, this));
        this.getActionMap().put(MoveAction.LEFT, new MoveAction(KeyEvent.VK_LEFT, this));
        this.getActionMap().put(MoveAction.RIGHT, new MoveAction(KeyEvent.VK_RIGHT, this));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics graphics) {
        for (int i = 0; i <= SCREEN_HEIGHT / unitSize; i++) {
            for (int j = 0; j <= SCREEN_WIDTH / unitSize; j++) {
                graphics.drawImage(assets.grass, j * unitSize, i * unitSize,
                        unitSize, unitSize, this);
            }
        }
        // Buttons
        backBtn.repaint(graphics);
        resetBtn.repaint(graphics);

        // drawing map
        if (level != null) drawMap(graphics);
    }

    private void drawMap(Graphics graphics) {
        Pair[][] map = level.getMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int x = j == 0 ? padX : (j * unitSize) + padX;
                int y = i == 0 ? padY : (i * unitSize) + padY;
                Point currCoordinates = map[i][j].getPosition().getCoordinates();

                switch (map[i][j].getSymbol()) {
                    case '#' -> graphics.drawImage(assets.brickWall, x, y, unitSize,
                            unitSize, this);
                    case '$' -> {
                        graphics.drawImage(assets.ground, x, y, unitSize,
                                unitSize, this);
                        graphics.drawImage(assets.crate, currCoordinates.x,
                                currCoordinates.y, unitSize, unitSize, this);
                    }
                    case '0', '@' -> graphics.drawImage(assets.ground, x, y, unitSize,
                            unitSize, this);
                    case '.' -> {
                        graphics.drawImage(assets.ground, x, y, unitSize,
                                unitSize, this);
                        graphics.drawImage(assets.truePlace, x, y, unitSize,
                                unitSize, this);
                    }
                    case '*' -> {
                        graphics.drawImage(assets.ground, x, y, unitSize,
                                unitSize, this);
                        graphics.drawImage(assets.trueCrate, currCoordinates.x,
                                currCoordinates.y, unitSize, unitSize, this);
                    }
                }
            }
        }
        Point playerCoord = level.getPlayer().getCoordinates();
        graphics.drawImage(assets.character, playerCoord.x, playerCoord.y, unitSize,
                unitSize, this);
    }

    private Level loadFile(InputStream inputStream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String currLine = reader.readLine();
            String[] split = currLine.split(" ");
            int rows = Integer.parseInt(split[0]);
            int cols = Integer.parseInt(split[1]);
            padX = (SCREEN_WIDTH - (cols * unitSize)) / 2;
            padY = (SCREEN_HEIGHT - (rows * unitSize)) / 2;

            Position playerPos = null;
            Pair[][] levelMap = new Pair[rows][cols];
            currLine = reader.readLine();
            int i = 0;
            int crateCount = 0;
            while (currLine != null) {
                split = currLine.split(" ");
                for (int j = 0; j < split.length; j++) {
                    int x = (j * unitSize) + padX;
                    int y = (i * unitSize) + padY;
                    char currChar = split[j].charAt(0);
                    levelMap[i][j] = new Pair(currChar, new Position(i, j,
                            new Point(x, y)));
                    if (currChar == '@') playerPos = new Position(i, j, new Point(x, y));
                    if (currChar == '.' || currChar == '*') crateCount += 1;
                }
                i += 1;
                currLine = reader.readLine();
            }
            if (playerPos == null) throw new Exception("There is no player in the map.");
            return new Level(levelMap, playerPos, crateCount);
        } catch (Exception e) {
            System.out.println("File Exception: " + e.getMessage());
        }
        return null;
    }

    /**
     * Helper method to print the whole map, every time the player moves.
     **/
    private void printMap() {
        System.out.println("MAP: ");
        for (Pair[] pairs : level.getMap()) {
            for (Pair pair : pairs) {
                System.out.print(pair.getSymbol() + " | ");
            }
            System.out.println();
        }
    }

    private boolean isLevelCompleted() {
        int count = 0;
        for (Pair[] pairs : level.getMap()) {
            for (Pair pair : pairs) {
                if (pair.getSymbol() == '*')
                    count += 1;
            }
        }
        return level.getCrateCount() == count;
    }

    private void displayMessage() {
        Timer timer = new Timer(150, e -> {
            if (mListener == null) {
                JOptionPane.showMessageDialog(this, "Game Listener is null!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            mListener.onLevelCompleted(currentLvl);
            backBtn.enabled(false);
            resetBtn.enabled(false);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(new LevelCompletedPanel(mListener, currentLvl));
            validate();
        });
        timer.start();
        timer.setRepeats(false);
    }

    @Override
    public void onKeyPressed(int direction) {
        level.movePlayer(direction);
//        printMap();
        if (isLevelCompleted() && !isMsgDisplayed) {
            isMsgDisplayed = true;
            displayMessage();
        }
        repaint();
    }

    @Override
    public void onBtnPressed(CustomButton customButton) {
        if (mListener == null) return;
        if (customButton.getText().equals(BACK_BTN))
            mListener.onBackPressed();
        else if (customButton.getText().equals(RESET_BTN))
            mListener.onReset();
        else
            mListener.onLevelCompleted(currentLvl);
    }
}
