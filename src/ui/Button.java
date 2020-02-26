package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Button extends JButton {
    public Button(String text) {
        super(text);
        this.setForeground(Color.WHITE);
        this.setBackground(Color.BLACK);
        this.setOpaque(false);
        Border line = new LineBorder(Color.WHITE, 4);
        Border margin = new EmptyBorder(0, 0, 0, 0);
        Border compound = new CompoundBorder(line, margin);
        this.setBorder(compound);
        this.setFocusPainted(false);
        final JButton that = this;
        this.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
//                if (model.isRollover()) {
//                    tip1Null.setBorder(compound1);
//                } else {
//                    tip1Null.setBorder(compound);
//                }
                if (model.isPressed()) {
                    that.setBackground(Color.WHITE);
                    that.setForeground(Color.BLACK);
                } else {
                    that.setForeground(Color.WHITE);
                    that.setBackground(Color.BLACK);
                }
            }
        });
    }
}
