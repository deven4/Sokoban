package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import static src.Constants.BUTTON_COLOR;

public class RoundButton extends JButton implements MouseListener {

    private String text;
    private final boolean isEnabled;
    private final Assets assets;
    private boolean isHovered = false;

    RoundButton(String text, boolean isEnabled) {
        this.text = text;
        this.isEnabled = isEnabled;
        assets = Assets.getInstance();

        setBorderPainted(false);
        addMouseListener(this);
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int size = 25;
        Color color = Color.WHITE;
        int width = getWidth() - 5;
        int height = getHeight() - 5;

        if (isHovered) {
            size = 40;
            drawOval(graphics2D, height, width, Color.RED);
        } else {
            color = new Color(32, 32, 32);
            drawOval(graphics2D, height, width, BUTTON_COLOR);
        }
        if (isEnabled)
            drawString(graphics2D, height, width, size, color);
        else
            drawLock(graphics2D);
    }

    private void drawLock(Graphics2D graphics2D) {
        int width = 50;
        int height = 50;
        int x = (int) (width * 0.35);
        int y = (int) (height * 0.30);
        graphics2D.drawImage(assets.padlock, x, y, width, height, this);
    }

    private void drawOval(Graphics2D graphics2D, int height, int width, Color color) {
        Color color1 = new Color(105, 105, 105);
        GradientPaint gp = new GradientPaint(0, 0, Color.BLACK, width + 5, height + 5, color1);
        graphics2D.setPaint(gp);
        graphics2D.fillOval(0, 0, width + 3, height + 3);
        graphics2D.setColor(color);
        graphics2D.fillOval(0, 0, width, height);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawOval(0, 0, width, height);
    }

    private void drawString(Graphics2D graphics2D, int height, int width,
                            int size, Color color) {
        Font font = new Font("Comic Sans MS", Font.BOLD, size);
        FontRenderContext frc = new FontRenderContext(new AffineTransform(),
                true, true);
        int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
        int textHeight = (int) (font.getStringBounds(text, frc).getHeight());

        height = (int) (height / 2 + (textHeight * 0.25));
        graphics2D.setFont(font);
        graphics2D.setColor(color);
        graphics2D.drawString(text, width / 2 - textWidth / 2, height);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isHovered = true;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isHovered = false;
        repaint();
    }
}
