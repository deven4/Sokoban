package src;

import src.Interfaces.onButtonClicked;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import static src.Constants.BUTTON_COLOR;

public class CustomButton implements MouseListener, MouseMotionListener {

    private final int size;
    private final String text;
    private boolean isEnabled = true;
    private final JPanel rootPanel;
    private boolean isHovered = false;
    private onButtonClicked mListener;

    private Font font;
    private int xCoord, yCoord;
    private int textWidth, textHeight;
    private Color textColor, rectColor;
    private int rectWidth = 0, rectHeight = 0;

    CustomButton(String text, int size,
                 int xCoord, int yCoord, JPanel rootPanel) {
        this.text = text;
        this.size = size;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.rootPanel = rootPanel;

        rootPanel.addMouseListener(this);
        rootPanel.addMouseMotionListener(this);

        instantiate();
    }

    private void instantiate() {
        font = new Font("Comic Sans MS", Font.BOLD, size);
        FontRenderContext frc = new FontRenderContext(new AffineTransform(),
                true, true);
        textWidth = (int) (font.getStringBounds(text, frc).getWidth());
        textHeight = (int) (font.getStringBounds(text, frc).getHeight());

        int aaja = (Constants.SCREEN_WIDTH * (textWidth / 100));
        float textPadding = textWidth < 100 ? (float) textWidth / 100
                : (float) textWidth / aaja;

        // Rectangle Shape
        rectHeight = (int) (textHeight + textHeight * 0.40);
        rectWidth = (int) (textWidth + textWidth * textPadding);
    }

    public String getText() {
        return text;
    }

    public void addButtonListener(onButtonClicked mListener) {
        this.mListener = mListener;
    }

    public void enabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setLocation(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    /**
     * Draws the Custom button onto the panel.
     **/
    public void repaint(Graphics graphics) {
        if (isHovered & isEnabled) {
            textColor = Color.RED;
            rectColor = Color.WHITE;
        } else {
            textColor = Color.BLACK;
            rectColor = BUTTON_COLOR;
        }
        draw(graphics);
    }

    public void draw(Graphics graphics) {
        int textX = xCoord + (rectWidth - textWidth) / 2;
        int textY = yCoord + rectHeight / 2 + textHeight * 25 / 100;
        // Shadow layer
        graphics.setColor(Color.BLACK);
        graphics.fillRoundRect(xCoord, yCoord, rectWidth + 3,
                rectHeight + 3, 5, 5);
        // Rectangle BG
        graphics.setColor(rectColor);
        graphics.fillRoundRect(xCoord, yCoord, rectWidth,
                rectHeight, 5, 5);
        // Border layer
        graphics.setColor(Color.BLACK);
        graphics.drawRoundRect(xCoord, yCoord, rectWidth, rectHeight, 5, 5);
        // Text layer
        graphics.setFont(font);
        graphics.setColor(textColor);
        graphics.drawString(text, textX, textY);
    }

    private boolean isPointInsideButton(int x, int y) {
        Point currCoordinates = new Point(x, y);
        Rectangle rectangle = new Rectangle(xCoord, yCoord, rectWidth, rectHeight);
        return rectangle.contains(currCoordinates);
    }

    /**
     * Returns an array of custom button height & width.
     **/
    public int[] getDimensions() {
        return new int[]{rectHeight, rectWidth};
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isPointInsideButton(e.getX(), e.getY()) && isEnabled)
            if (mListener != null) mListener.onBtnPressed(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        isHovered = isPointInsideButton(e.getX(), e.getY());
        rootPanel.repaint();
    }
}
