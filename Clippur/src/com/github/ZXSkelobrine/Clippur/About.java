package com.github.ZXSkelobrine.Clippur;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.html.HTMLEditorKit;

public class About extends JFrame {

	private static final long serialVersionUID = -5271410492448459956L;
	private JPanel contentPane;
	public static String HTML = "<html>\n" + "<body>\n" + "Clippur (C) Ryan Delaney 2014 released under the Q Public License 1.0 (QPL-1.0) available to read from " + "<a href=\"http://opensource.org/licenses/QPL-1.0\">here</a>" + " or available within this projects source code (on " + "<a href=\"http://www.github.com/ZXSkelobrine/Clippur\">GitHub</a>" + ") within the 'LICENSE' file." + "</body>\n" + "</html>";
	static boolean isNeedCursorChange = true;
	JTextPane edit = new JTextPane() {
		private static final long serialVersionUID = -8694738408226294297L;

		public void setCursor(Cursor cursor) {
			if (isNeedCursorChange) {
				super.setCursor(cursor);
			}
		}
	};

	public static void enableFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About frame = new About();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public About() {
		setTitle("Clippur - About");
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			setIconImage(Tray.createImage("/images/about.png", "Clippur About Title Icon", 100, 100));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 391, 198);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setBounds(24, 60, 100, 100);
		lblNewLabel.setIcon(new ImageIcon(Tray.createImage("/images/about.png", "Clippur About Image", 100, 100)));
		contentPane.add(lblNewLabel);
		edit.setBackground(SystemColor.control);

		this.getContentPane().add(edit);
		MyHTMLEditorKit kit = new MyHTMLEditorKit();

		edit.setEditorKit(kit);
		edit.setBounds(130, 60, 245, 100);

		edit.setText("<html>\r\n<body>\r\nClippur \u00A9 Ryan Delaney 2014 is released under the Q Public License 1.0 (QPL-1.0) available to read from <a href=\"http://opensource.org/licenses/QPL-1.0\">here</a> or available within this projects source code (on <a href=\"http://www.github.com/ZXSkelobrine/Clippur\">GitHub</a>) within the 'LICENSE' file.</body>\r\n</html>");

		JLabel lblClippur = new JLabel("About Clippur");
		lblClippur.setHorizontalAlignment(SwingConstants.CENTER);
		lblClippur.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblClippur.setBounds(99, 11, 187, 38);
		contentPane.add(lblClippur);
		((AbstractDocument) edit.getDocument()).setDocumentFilter(new EditFilter());
		edit.addHyperlinkListener(new HTMLListener());
		edit.getCaret().setVisible(false);
		edit.getCaret().setSelectionVisible(false);
	}
}

class HTMLListener implements HyperlinkListener {
	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			try {
				System.out.println("Called");
				Desktop.getDesktop().browse(e.getURL().toURI());
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
		}
	}
}

class MyHTMLEditorKit extends HTMLEditorKit {

	private static final long serialVersionUID = -5003841688233236833L;
	MyLinkController handler = new MyLinkController();

	public void install(JEditorPane c) {
		MouseListener[] oldMouseListeners = c.getMouseListeners();
		MouseMotionListener[] oldMouseMotionListeners = c.getMouseMotionListeners();
		super.install(c);
		// the following code removes link handler added by original
		// HTMLEditorKit

		for (MouseListener l : c.getMouseListeners()) {
			c.removeMouseListener(l);
		}
		for (MouseListener l : oldMouseListeners) {
			c.addMouseListener(l);
		}

		for (MouseMotionListener l : c.getMouseMotionListeners()) {
			c.removeMouseMotionListener(l);
		}
		for (MouseMotionListener l : oldMouseMotionListeners) {
			c.addMouseMotionListener(l);
		}

		// add out link handler instead of removed one
		c.addMouseListener(handler);
		c.addMouseMotionListener(handler);
	}

	class MyLinkController extends LinkController {

		private static final long serialVersionUID = -1335557806271654028L;

		public void mouseClicked(MouseEvent e) {
			JEditorPane editor = (JEditorPane) e.getSource();

			if (editor.isEditable() && SwingUtilities.isLeftMouseButton(e)) {
				if (e.getClickCount() == 1) {
					editor.setEditable(false);
					super.mouseClicked(e);
					editor.setEditable(true);
				}
			}

		}

		public void mouseMoved(MouseEvent e) {
			JEditorPane editor = (JEditorPane) e.getSource();

			if (editor.isEditable()) {
				About.isNeedCursorChange = false;
				editor.setEditable(false);
				About.isNeedCursorChange = true;
				super.mouseMoved(e);
				About.isNeedCursorChange = false;
				editor.setEditable(true);
				About.isNeedCursorChange = true;
			}
		}

	}
}

class EditFilter extends DocumentFilter {
	@Override
	public void insertString(FilterBypass arg0, int arg1, String arg2, AttributeSet arg3) throws BadLocationException {
	}

	@Override
	public void replace(FilterBypass arg0, int arg1, int arg2, String arg3, AttributeSet arg4) throws BadLocationException {
	}

	@Override
	public void remove(FilterBypass arg0, int arg1, int arg2) throws BadLocationException {
	}

}