package src;

import src.Interfaces.onLevelSelectListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

public class LevelSelectedPanel extends JPanel implements ActionListener {

    public static int levelCompleted = 1;

    private final int unitSize;
    private final Assets assets;
    private final GridBagConstraints gbc;
    private final RoundButton[][] buttons;
    private final boolean[] levelCompletedArr;
    private onLevelSelectListener levelSelectListener;

    public int BTN_ROW;
    private final int BTN_COLS = 5;
    private static final int TOTAL_LEVELS = 15;

    LevelSelectedPanel() {
        unitSize = Constants.unitSize;
        assets = Assets.getInstance();
        BTN_ROW = TOTAL_LEVELS / BTN_COLS;
        levelCompletedArr = new boolean[TOTAL_LEVELS];
        buttons = new RoundButton[BTN_ROW][BTN_COLS];

        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setupLevels();
        addBtns();
    }

    public void setupLevels() {
        for (int i = 0; i < levelCompleted; i++) {
            levelCompletedArr[i] = true;
        }
    }

    public void updateInterface() {
        System.out.println(Arrays.toString(levelCompletedArr));
        setupLevels();
        this.removeAll();
        this.revalidate();
        this.repaint();
        addBtns();
    }

    public void setLevelSelectListener(onLevelSelectListener levelSelectListener) {
        this.levelSelectListener = levelSelectListener;
    }

    private void addBtns() {
        int count = 0;
        for (int i = 0; i < BTN_ROW; i++) {
            for (int j = 0; j < BTN_COLS; j++) {
                setupConstraints(j, i);
                buttons[i][j] = new RoundButton("" + (count + 1), levelCompletedArr[count]);
                buttons[i][j].setPreferredSize(new Dimension(90, 90));
                buttons[i][j].addActionListener(LevelSelectedPanel.this);
                add(buttons[i][j], gbc);
                count += 1;
            }
        }
    }

    private void setupConstraints(int gridx, int gridy) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 20, 10);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i <= Constants.SCREEN_HEIGHT / unitSize; i++) {
            for (int j = 0; j <= Constants.SCREEN_WIDTH / unitSize; j++) {
                g.drawImage(assets.wallTextureBG, j * unitSize, i * unitSize,
                        unitSize, unitSize, this);
            }
        }

        // Title text
        String text = "SELECT LEVEL";
        drawText(g, text, 28);
    }

    /**
     * Will Draw text in the center of the JPanel.
     **/
    private void drawText(Graphics graphics, String text, int textSize) {
        int height = (int) (unitSize * 1.5);
        Font font = new Font("Comic Sans MS", Font.BOLD, textSize);
        FontRenderContext frc = new FontRenderContext(new AffineTransform(),
                true, true);
        int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
        int textHeight = (int) (font.getStringBounds(text, frc).getHeight());
        int textPos = Constants.SCREEN_WIDTH / 2 - (textWidth / 2);

        // 40% addition to textHeight
        graphics.setColor(new Color(230, 57, 30));
        int rectHeight = (int) (textHeight + (textHeight * 0.4));
        graphics.fillRoundRect(textPos - 10, height - textHeight,
                textWidth + 15, rectHeight,
                10, 10);

        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        graphics.drawString(text, textPos, height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RoundButton btn = (RoundButton) e.getSource();
        try {
            if (btn.isEnabled())
                levelSelectListener.onLevelSelected(Integer.parseInt(btn.getText()));
        } catch (NullPointerException exception) {
            System.out.println("Null Pointer exception: Setup the onLevelSelectedListener!");
        }
    }
}
