package src;

import src.Interfaces.onGameListener;
import src.Interfaces.onLevelSelectListener;

import javax.swing.*;
import java.awt.*;

import static src.Constants.*;

public class Main extends JFrame implements onLevelSelectListener, onGameListener {

    private int currLevel;
    private final JPanel rootPanel;
    private final CardLayout cardLayout;
    private final LevelSelectedPanel levelSelectedPanel;

    Main() {
        rootPanel = new JPanel();
        cardLayout = new CardLayout();
        rootPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        rootPanel.setLayout(cardLayout);

        levelSelectedPanel = getLevelSelectedPanel();
        setLayout(new BorderLayout());
        getContentPane().add(rootPanel, BorderLayout.CENTER);
        pack();

        setVisible(true);
        setResizable(false);
        setTitle("Sokoban");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Main();
    }

    private LevelSelectedPanel getLevelSelectedPanel() {
        LevelSelectedPanel levelPanel = new LevelSelectedPanel();
        levelPanel.setLevelSelectListener(this);
        rootPanel.add(LEVEL_PANEL, levelPanel);
        cardLayout.show(rootPanel, LEVEL_PANEL);
        return levelPanel;
    }

    private void addGamePanel() {
        GamePanel gamePanel = new GamePanel(currLevel);
        gamePanel.addGameListener(this);
        rootPanel.add(GAME_PANEL, gamePanel);
        cardLayout.show(rootPanel, GAME_PANEL);
    }

    private void removePanel(String panel) {
        for (Component component : rootPanel.getComponents())
            if (panel.equals(GAME_PANEL) && component instanceof GamePanel)
                rootPanel.remove(component);
            else if (panel.equals(LEVEL_PANEL) && component instanceof LevelSelectedPanel)
                rootPanel.remove(component);
    }

    @Override
    public void onLevelCompleted(int currentLvl) {
        LevelSelectedPanel.levelCompleted = currentLvl + 1;
        levelSelectedPanel.updateInterface();
    }

    @Override
    public void onReset() {
        removePanel(GAME_PANEL);
        addGamePanel();
    }

    @Override
    public void onBackPressed() {
        removePanel(GAME_PANEL);
        cardLayout.show(rootPanel, LEVEL_PANEL);
    }

    @Override
    public void onNextLvl(int level) {
        removePanel(GAME_PANEL);
        cardLayout.show(rootPanel, LEVEL_PANEL);
    }

    @Override
    public void onLevelSelected(int levelCode) {
        this.currLevel = levelCode;
        addGamePanel();
    }
}
