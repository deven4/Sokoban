package src;

import src.Interfaces.onGameListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static src.Constants.*;

public class LevelCompletedPanel extends JPanel {

    private final int width = unitSize * 9;
    private final int height = unitSize * 10;

    private onGameListener mListener;

    LevelCompletedPanel(onGameListener mListener, int currLvl) {
        this.mListener = mListener;
        int panelX = (SCREEN_WIDTH / 2) - (width / 2);
        int panelY = (SCREEN_HEIGHT / 2) - (height / 2);
        setBackground(Color.BLACK);
        super.setOpaque(false);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        CustomDialogBox box = new CustomDialogBox(currLvl);
        box.setBounds(0, 0, 300, 350);
        box.addBtnListeners(() -> mListener.onNextLvl(currLvl));
        add(box);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.40f));
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();

        getUI().paint(g, this);
    }
}

