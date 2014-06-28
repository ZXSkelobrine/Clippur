package com.github.ZXSkelobrine.Clippur;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

public class Tray {

	protected static PopupMenu popup = new PopupMenu();
	protected static TrayIcon ti;
	protected static SystemTray st;
	protected static Menu menu;
	protected static MenuItem aboutItem;
	protected static MenuItem startListening;
	protected static MenuItem stopListening;
	protected static MenuItem exitItem;
	protected static MenuItem optionsItem;
	protected static CheckboxMenuItem currentStatus;
	protected static TrayActionListener tal = new TrayActionListener();

	public static boolean registerTrayIcon() {
		// Check if the system tray is supported on this os.
		if (!SystemTray.isSupported()) return false;
		// If so get the systems tray.
		st = SystemTray.getSystemTray();
		// Create a new tray icon from the supplied image and description.
		ti = new TrayIcon(createImage("/images/tray.png", "ImgClip"));
		// Create a new popup menu instance.
		popup = new PopupMenu();

		// Create all the needed menu items to populate the list.
		aboutItem = new MenuItem("About");
		startListening = new MenuItem("Start Listening");
		stopListening = new MenuItem("Stop Listening");
		currentStatus = new CheckboxMenuItem("Listening Status");
		exitItem = new MenuItem("Exit");
		optionsItem = new MenuItem("Options");

		// Set the action command for the action listener.
		aboutItem.setActionCommand("about");
		startListening.setActionCommand("start");
		stopListening.setActionCommand("stop");
		exitItem.setActionCommand("exit");
		optionsItem.setActionCommand("options");

		// Set the usability and state of the status checkbox.
		currentStatus.setEnabled(false);
		currentStatus.setState(true);

		// Add components to pop-up menu
		popup.add(aboutItem);
		popup.add(optionsItem);
		popup.addSeparator();
		popup.add(startListening);
		popup.add(stopListening);
		popup.add(currentStatus);
		popup.addSeparator();
		popup.add(exitItem);

		// Set the action listener of all the items.
		aboutItem.addActionListener(tal);
		startListening.addActionListener(tal);
		stopListening.addActionListener(tal);
		exitItem.addActionListener(tal);
		optionsItem.addActionListener(tal);

		// Set the popup menu of the tray icon.
		ti.setPopupMenu(popup);

		// Try to...
		try {
			// Add the icon to the system tray.
			st.add(ti);
			// Catch any AWTExceptions
		} catch (AWTException e) {
			// Print out an error.
			System.out.println("TrayIcon could not be added.");
			return false;
		}

		// Return true as the icon was added successfully.
		return true;
	}

	/**
	 * Creates an image from the given path.
	 * 
	 * @param path
	 *            - The local path of the file.
	 * @param description
	 *            - The description of the file.
	 * @return the image.
	 */
	public static Image createImage(String path, String description) {
		// Create a url from the path.
		URL imageURL = Tray.class.getResource(path);

		// If the url is null
		if (imageURL == null) {
			// Print out an error
			System.err.println("Resource not found: " + path);
			// And return null.
			return null;
		} else {
			// Otherwise return the new image.
			return (new ImageIcon(imageURL, description)).getImage();
		}
	}

	/**
	 * Creates an image with the given size.
	 * 
	 * @param path
	 *            - The local path of the file.
	 * @param description
	 *            - The description of the file.
	 * @param width
	 *            - The width of the output
	 * @param height
	 *            - The height of the output
	 * @return the scaled image.
	 */
	public static Image createImage(String path, String description, int width, int height) {
		// Create a url from the path.
		URL imageURL = Tray.class.getResource(path);

		// If the url is null
		if (imageURL == null) {
			// Print out an error
			System.err.println("Resource not found: " + path);
			// And return null.
			return null;
		} else {
			// Create the image from the path.
			ImageIcon io = new ImageIcon(imageURL, description);
			// Create a new BufferedImage with the given width and height and of
			// TYPE_INT_ARGB.
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			// Create the graphics and draw the image onto it with the width and
			// height.
			bi.createGraphics().drawImage(io.getImage(), 0, 0, width, height, null);
			// Return the new image. This is a bit convoluted. I will change it
			// later.
			return (new ImageIcon(bi, description)).getImage();
		}
	}

	/**
	 * This sets the icon to the upload status.
	 */
	public static void setUploading() {
		ti.setImage(createImage("/images/tray_upload.png", "Clippur"));
	}

	/**
	 * This sets the image to done then back to normal after one seconds. TODO
	 * **WARNING** This locks up the interface...
	 */
	public static void setDone() {
		// Set the image to the 'done' icon
		ti.setImage(createImage("/images/tray_done.png", "Clippur"));
		// Try to
		try {
			// Sleep for one sec
			Thread.sleep(1000);
			// Catch any interruptedExceptions.
		} catch (InterruptedException e) {
			// And print the stack trace.
			e.printStackTrace();
		}
		// Then set the image back to normal.
		ti.setImage(createImage("/images/tray.png", "Clippur"));
	}

}

/**
 * This class deals with all actions from the buttons.
 * 
 * @author Ryan TODO comment this...
 */
class TrayActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
		case "about":
			About.enableFrame();
			break;
		case "start":
			if (!Tray.currentStatus.getState()) {
				Tray.currentStatus.setState(true);
				Main.enableListeners = true;
			}
			break;
		case "stop":
			if (Tray.currentStatus.getState()) {
				Tray.currentStatus.setState(false);
				Main.enableListeners = false;
			}
			break;
		case "exit":
			Tray.st.remove(Tray.ti);
			System.exit(0);
			break;
		case "options":
			Options.enableFrame();
			break;
		}
	}

}
