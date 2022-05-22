package src;

import src.Interfaces.onKeyBindListener;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MoveAction extends AbstractAction {

    public static final String UP = "Up";
    public static final String DOWN = "Down";
    public static final String LEFT = "Left";
    public static final String RIGHT = "Right";

    private final int direction;
    private final onKeyBindListener mListener;

    MoveAction(int direction, onKeyBindListener mListener) {
        this.direction = direction;
        this.mListener = mListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (mListener != null) mListener.onKeyPressed(direction);
    }
}
