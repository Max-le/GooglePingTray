
/*
 * TrayIconDemo.java
 */

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.*;


class TrayIconDemo {

    private static Logger LOGGER = Logger.getLogger(TrayIconDemo.class.getName());
    private static JLogFrame logFrame = new JLogFrame();
    private static final String ADDRESS_PINGED = "www.google.com";
    private static final int PING_INTERVAL = 1000;

    /**
     * USE THIS METHOD SO THE LOG APPEARS BOTH IN CONSOLE AND IN GUI.
     * @param msg the message to add in the log.
     */
    public static void log(String msg){
        LOGGER.info(msg);
        logFrame.appendToLog(msg);
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        Image green = createImage("images/green_dot.gif", "green icon");
        Image red = createImage("images/red_dot.gif", "red icon");
        Image bulb = createImage("images/bulb.gif", "bulb");

        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(bulb);
        final SystemTray tray = SystemTray.getSystemTray();
        trayIcon.setImageAutoSize(true);

        /* Use an appropriate Look and Feel */
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon.

        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            log("SystemTray is not supported");
            return;
        }

        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About");
        MenuItem logItem = new MenuItem("Logs");
        MenuItem restartItem = new MenuItem("Restart");
        MenuItem exitItem = new MenuItem("Exit");

        //Add components to popup menu
        popup.add(aboutItem);
        popup.add(logItem);
        popup.addSeparator();
        popup.add(restartItem);
        popup.addSeparator();
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            log("TrayIcon could not be added.");
            return;
        }
        aboutItem.addActionListener(e -> {
            log("About button clicked");
            JOptionPane.showMessageDialog(null,
                    "This small utility pings "+ADDRESS_PINGED+" every second to test yout Internet connection." +
                            "\nCreated by Max Lepin\nwww.maxle.be");
        });
        logItem.addActionListener(e -> {
            log("Log btn clicked");
            logFrame.setVisible(true);
        });

        restartItem.addActionListener(e -> {
            log("Restart button clicked.");
            try {
                restartApplication();
            } catch (IOException | URISyntaxException ioException) {
                ioException.printStackTrace();
            }
        });

        exitItem.addActionListener(e -> {
            tray.remove(trayIcon);
            System.exit(0);
        });

        while (true) {
            Thread.sleep(PING_INTERVAL);
            if (receivedPing()){

                log("Ping to "+ADDRESS_PINGED+" at "+LocalTime.now()+" : Connected");

                trayIcon.setImage(green);
            } else {
                log("Ping to "+ADDRESS_PINGED+"at "+LocalTime.now()+" : Offline");
                trayIcon.setImage(red);
            }
        }
    }




    //Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = TrayIconDemo.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

    public static boolean receivedPing() throws IOException
    {
        try {
            InetAddress server = InetAddress.getByName(ADDRESS_PINGED);
            return server.isReachable(PING_INTERVAL+500);
        } catch (UnknownHostException | SocketException e){
            return false;
        }
    }

    /**
     * https://stackoverflow.com/a/4194224/9275922
     */
    public static void restartApplication() throws IOException, URISyntaxException {
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        final File currentJar = new File(TrayIconDemo.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        /* is it a jar file? */
        if(!currentJar.getName().endsWith(".jar"))
            return;

        /* Build command: java -jar application.jar */
        final ArrayList<String> command = new ArrayList<String>();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getPath());

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();
        System.exit(0);
    }


}