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

	static PopupMenu popup = new PopupMenu();
	static TrayIcon ti;
	static SystemTray st;
	static Menu menu;

	public static boolean registerTrayIcon() {
		if (!SystemTray.isSupported()) return false;
		popup = new PopupMenu();
		ti = new TrayIcon(createImage("/images/tray.png", "ImgClip"));
		st = SystemTray.getSystemTray();
		MenuItem aboutItem = new MenuItem("About");
		MenuItem cb1 = new MenuItem("Start Listening");
		MenuItem cb2 = new MenuItem("Stop Listening");
		final CheckboxMenuItem cb3 = new CheckboxMenuItem("Listening Status");
		cb3.setEnabled(false);
		cb3.setState(true);
		MenuItem exitItem = new MenuItem("Exit");
		MenuItem optionsItem = new MenuItem("Options");

		// Add components to pop-up menu
		popup.add(aboutItem);
		popup.add(optionsItem);
		popup.addSeparator();
		popup.add(cb1);
		popup.add(cb2);
		popup.add(cb3);
		popup.addSeparator();
		popup.add(exitItem);
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				st.remove(ti);
				System.exit(0);
			}
		});
		cb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!cb3.getState()) {
					cb3.setState(true);
					Main.enableListeners = true;
				}
			}
		});
		cb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cb3.getState()) {
					cb3.setState(false);
					Main.enableListeners = false;
				}
			}
		});

		ti.setPopupMenu(popup);

		try {
			st.add(ti);
		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added.");
		}

		aboutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				About.enableFrame();
			}
		});

		optionsItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Options.enableFrame();
			}
		});
		return true;
	}

	public static Image createImage(String path, String description) {
		URL imageURL = Tray.class.getResource(path);

		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			return null;
		} else {
			return (new ImageIcon(imageURL, description)).getImage();
		}
	}

	public static Image createImage(String path, String description, int width, int height) {
		URL imageURL = Tray.class.getResource(path);

		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			return null;
		} else {
			ImageIcon io = new ImageIcon(imageURL, description);
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			bi.createGraphics().drawImage(io.getImage(), 0, 0, width, height, null);
			return (new ImageIcon(bi, description)).getImage();
		}
	}

	public static void setUploading() {
		ti.setImage(createImage("/images/tray_upload.png", "Clippur"));
	}

	public static void setDone() {
		//TODO work on this - lock up interface for 1sec.
		ti.setImage(createImage("/images/tray_done.png", "Clippur"));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ti.setImage(createImage("/images/tray.png", "Clippur"));
	}

}
