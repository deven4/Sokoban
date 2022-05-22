package src;

import src.Interfaces.onButtonClicked;

import javax.swing.*;
import java.awt.*;

import static src.Constants.*;
import static src.Constants.unitSize;

public class CustomDialogBox extends JPanel implements onButtonClicked {

    public static final int TEXT_Y = 150;
    public static final int PANEL_WID = 400;
    public static final int PANEL_HEI = 500;

    private final int currLvl;
    private final Assets assets;
    private CustomButton nextBtn;
    private dialogListener dialogListener;

    public CustomDialogBox(int currLvl) {
        this.currLvl = currLvl;
        setOpaque(false);
        setVisible(true);
        assets = Assets.getInstance();
        setPreferredSize(new Dimension(PANEL_WID, PANEL_HEI));

        create();
    }

    private void create() {
        setLayout(null);
        JLabel label = new JLabel("LEVEL " + getLevel());
        label.setFont(new Font("Quicksand", Font.BOLD, 35));
        label.setForeground(Color.WHITE);
        Dimension size = label.getPreferredSize();
        label.setBounds((PANEL_WID / 2) - (size.width / 2), TEXT_Y, size.width,
                size.height);

        JLabel label2 = new JLabel("COMPLETED");
        label2.setFont(new Font("Quicksand", Font.BOLD, 35));
        label2.setForeground(Color.WHITE);
        Dimension size2 = label2.getPreferredSize();
        label2.setBounds((PANEL_WID / 2) - (size2.width / 2),
                TEXT_Y + size.height + 5, size2.width, size2.height);

        add(label);
        add(label2);

        // Next Button
        int y = PANEL_HEI / 2 + unitSize;
        nextBtn = new CustomButton("Next Level", 26, PANEL_WID / 2 - unitSize - 10,
                y, this);
        int btnWidth = nextBtn.getDimensions()[1];
        nextBtn.setLocation((PANEL_WID / 2) - (btnWidth / 2), y);
        nextBtn.addButtonListener(this);
    }

    public void addBtnListeners(dialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setLocation((SCREEN_WIDTH / 2) - (getWidth() / 2),
                (SCREEN_HEIGHT / 2) - (getHeight() / 2));
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Image scaledInstance = assets.winBG.getScaledInstance(PANEL_WID,
                PANEL_HEI, Image.SCALE_SMOOTH);
        graphics2D.drawImage(scaledInstance, 0, 0, PANEL_WID, PANEL_HEI, this);

        nextBtn.repaint(graphics2D);
    }

    @Override
    public void onBtnPressed(CustomButton customButton) {
        if (dialogListener != null) dialogListener.onNext();
    }

    private String getLevel() {
        if (currLvl < 10) return "0" + currLvl;
        else return String.valueOf(currLvl);
    }

    public interface dialogListener {
        void onNext();
    }
}
