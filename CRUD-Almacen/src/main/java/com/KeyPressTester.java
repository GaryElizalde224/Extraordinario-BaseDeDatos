package com;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame; import javax.swing.SwingUtilities;

public final class KeyPressTester implements Runnable {

    public static void main(String[] args) throws InterruptedException, InvocationTargetException { SwingUtilities.invokeAndWait(new KeyPressTester()); }

    @Override public void run() { frame = new JFrame("KeyPress Tester");

        frame.setSize(new Dimension(800, 600)); frame.setVisible(true);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {System.out.println("Key Character: " + e.getKeyChar() + ";" +
                    " Key Code: " + KeyEvent.getKeyText(e.getKeyCode()));

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private JFrame frame; }


