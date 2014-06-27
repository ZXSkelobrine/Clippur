package com.github.ZXSkelobrine.Clippur;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Main implements NativeKeyListener {

	static Robot robot;
	static Rectangle rec;
	static List<String> deleteLinks = new ArrayList<>();
	static boolean enableListeners = true;
	static int captureKey = 67;
	static int captureModifiers = 3;
	static int[] captureModifiersSet = new int[] { 0, 1 };
	static int deleteKey = 88;
	static int deleteModifiers = 3;
	static int[] deleteModifiersSet = new int[] { 0, 1 };

	public static void main(String[] args) throws IOException {
		Tray.registerTrayIcon();
		try {
			robot = new Robot();
			rec = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		} catch (AWTException e) {
			e.printStackTrace();
		}
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.getInstance().addNativeKeyListener(new Main());
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (enableListeners) {
			if (e.getKeyCode() == deleteKey && e.getModifiers() == deleteModifiers) {
				try {
					openWebpage(new URL(deleteLinks.get(deleteLinks.size() - 1)).toURI());
				} catch (MalformedURLException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
			if (e.getKeyCode() == captureKey && e.getModifiers() == captureModifiers) {
				try {
					BufferedImage bi = robot.createScreenCapture(rec);
					String data = Imgur.upload(bi);
					System.out.println(data);
					String[] split = data.split("\"");
					System.out.println("Viewing Link: imgur.com/" + split[5] + ".png");
					System.out.println("Deletion Link: http://imgur.com/delete/" + split[37]);
					deleteLinks.add("http://imgur.com/delete/" + split[37]);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("imgur.com/" + split[5] + ".png"), null);
					File log = new File("C:/Clippur/Logs/" + new SimpleDateFormat("YYYY-MM-dd").format(Calendar.getInstance().getTime()) + ".txt");
					if (!log.getParentFile().exists()) log.getParentFile().mkdirs();
					if (!log.exists()) log.createNewFile();
					FileOutputStream fos = new FileOutputStream(log, true);
					fos.write((new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + ": \n\tViewing Link: http://imgur.com/" + split[5] + ".png" + "\n\tDeletion Link: http://imgur.com/delete/" + split[37] + "\n\n").getBytes());
					fos.flush();
					fos.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} else {
			System.out.println(e.getKeyChar() + ": " + e.getKeyCode());
		}
	}

	public static void openWebpage(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
