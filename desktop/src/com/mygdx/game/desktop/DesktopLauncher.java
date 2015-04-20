package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.vprog;
import com.mygdx.game.JMenuBuilder;

// These modules are needed to ascertain available screen "real estate".
import java.awt.Rectangle;
import java.awt.Insets;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;

// These modules are Alex experimenting with a UI. All of this UI work might be
// better off being moved into the Core, rather than Desktop, sub-project.
import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.*;

public class DesktopLauncher {
    // Swing is not thread safe, so it is "good practice" to move all Swing
    // commands out of main, into a function that is run by the event dispatcher
    // thread once it's actually safe.
    private static void startSwingGUI() {
        // Don't want any of this fixed-size stuff if we can avoid it, so we get
        // the screen area available to us for our windows (we don't want to
        // open windows bigger than these bounds if we can avoid it).
        Rectangle screenBounds = getWindowBounds();
        
        // This is currently just Alex testing his UI work. This will definitely
        // not be exactly how it's done later.
        openTestWindow(screenBounds);
        
        LwjglApplicationConfiguration config
            = new LwjglApplicationConfiguration();
        config.title = "VProg2D";
        config.x = screenBounds.x;
        config.y = screenBounds.y;
        config.width = screenBounds.width;
        config.height = screenBounds.height;
        config.resizable = true;
        new LwjglApplication(new vprog(), config);
    }
    
    private static Rectangle getWindowBounds() {
        // First grab the overall screen graphics configuration...
        GraphicsConfiguration graphicsConf
            = GraphicsEnvironment.getLocalGraphicsEnvironment()
              .getDefaultScreenDevice().getDefaultConfiguration();
        
        // ..then by grabbing the 'real' screen bounds (e.g. physical)...
        Rectangle screenBounds = getScreenBounds(graphicsConf);
        
        // ..then getting the "insets"...
        Insets screenInsets = getScreenInsets(graphicsConf);
        
        // ..and finally calculating the effective screen bounds based on the
        // physical bounds, minus the insets.
        return calculateInsetBounds(screenBounds, screenInsets);
    }
    
    private static Rectangle getScreenBounds
    (GraphicsConfiguration graphicsConf) {
        return graphicsConf.getBounds();
    }
    
    private static Insets getScreenInsets(GraphicsConfiguration graphicsConf) {
        return Toolkit.getDefaultToolkit().getScreenInsets(graphicsConf);
    }
    
    private static Rectangle calculateInsetBounds
    (Rectangle bounds, Insets margin) {
        Rectangle insetBounds = new Rectangle();
        insetBounds.x = bounds.x + margin.left;
        insetBounds.y = bounds.y + margin.top;
        insetBounds.width = bounds.width - margin.left - margin.right;
        insetBounds.height = bounds.height - margin.top - margin.bottom;
        return insetBounds;
    }
    
    private static void openTestWindow(Rectangle bounds) {
        JMenuBar menuBar = new JMenuBar();
        
        menuBar.add(JMenuBuilder.buildFileMenu());
        menuBar.add(JMenuBuilder.buildProjectMenu());
        menuBar.add(JMenuBuilder.buildHelpMenu());
        
        JFrame wrapperFrame = new JFrame("VProg2D Wrapper Frame");
        
        wrapperFrame.setBounds(bounds);
        // NOTE: This is only DISPOSE_ON_CLOSE while I'm testing. In the future,
        // it probably ought to be EXIT_ON_CLOSE (e.g. when, as I'm envisioning
        // it, the main app is embedded inside this UI's JFrame.)
        wrapperFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Container frameContainer = wrapperFrame.getContentPane();
        
        // BorderLayout is the default layout for Swing elements. Specifying the
        // "NORTH" constant forces the menu bar into the top-left, as typical.
        frameContainer.add(menuBar, BorderLayout.NORTH);
        
        wrapperFrame.setVisible(true);
    }

    public static void main (String[] arg) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                startSwingGUI();
            }
        });
    }
}
