package com.github.ZXSkelobrine.Clippur;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class Options extends JFrame {

	private static final long serialVersionUID = -3227489867294241138L;
	private JPanel contentPane;
	private String[] keys = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
	private String[] ctrs = new String[] { "Ctrl", "Shift", "Alt" };
	private JComboBox<String> delCmbCtr1;
	private JComboBox<String> delCmbCtr2;
	private JComboBox<String> delCmbKey1;
	private JComboBox<String> capCmbKey1;
	private JComboBox<String> capCmbCtr2;
	private JComboBox<String> capCmbCtr1;

	/**
	 * Launch the application.
	 */
	public static void enableFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Options frame = new Options();
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
	public Options() {
		setTitle("Clippur - Options");
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			setIconImage(Tray.createImage("/images/about.png", "Clippur About Title Icon", 100, 100));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 315, 181);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCaptureScreen = new JLabel("Capture Screen:");
		lblCaptureScreen.setBounds(10, 66, 105, 14);
		contentPane.add(lblCaptureScreen);

		capCmbCtr1 = new JComboBox<String>();
		capCmbCtr1.setBounds(106, 63, 60, 20);
		capCmbCtr1.setModel(new DefaultComboBoxModel<>(ctrs));
		capCmbCtr1.setSelectedIndex(Main.captureModifiersSet[0]);
		contentPane.add(capCmbCtr1);

		capCmbCtr2 = new JComboBox<String>();
		capCmbCtr2.setBounds(176, 63, 60, 20);
		capCmbCtr2.setModel(new DefaultComboBoxModel<>(ctrs));
		capCmbCtr2.setSelectedIndex(Main.captureModifiersSet[1]);
		contentPane.add(capCmbCtr2);

		capCmbKey1 = new JComboBox<String>();
		capCmbKey1.setBounds(246, 63, 46, 20);
		capCmbKey1.setModel(new DefaultComboBoxModel<>(keys));
		capCmbKey1.setSelectedIndex(Main.captureKey - 65);
		contentPane.add(capCmbKey1);

		JLabel lblClippurOptions = new JLabel("Clippur Options");
		lblClippurOptions.setHorizontalAlignment(SwingConstants.CENTER);
		lblClippurOptions.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblClippurOptions.setBounds(61, 11, 187, 38);
		contentPane.add(lblClippurOptions);

		JLabel lblDeleteImage = new JLabel("Delete Image:");
		lblDeleteImage.setBounds(10, 91, 105, 14);
		contentPane.add(lblDeleteImage);

		delCmbCtr1 = new JComboBox<String>();
		delCmbCtr1.setBounds(106, 88, 60, 20);
		delCmbCtr1.setModel(new DefaultComboBoxModel<>(ctrs));
		delCmbCtr1.setSelectedIndex(Main.deleteModifiersSet[0]);
		contentPane.add(delCmbCtr1);

		delCmbCtr2 = new JComboBox<String>();
		delCmbCtr2.setBounds(176, 88, 60, 20);
		delCmbCtr2.setModel(new DefaultComboBoxModel<>(ctrs));
		delCmbCtr2.setSelectedIndex(Main.deleteModifiersSet[1]);
		contentPane.add(delCmbCtr2);

		delCmbKey1 = new JComboBox<String>();
		delCmbKey1.setBounds(246, 88, 46, 20);
		delCmbKey1.setModel(new DefaultComboBoxModel<>(keys));
		delCmbKey1.setSelectedIndex(Main.deleteKey - 65);
		contentPane.add(delCmbKey1);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int delMod = 0;
				int[] delMods = new int[] { 0, 0 };
				int capMod = 0;
				int[] capMods = new int[] { 0, 0 };
				if (capCmbCtr1.getSelectedItem().equals("Ctrl")) {
					capMod += 2;
					capMods[0] = 0;
				}
				if (capCmbCtr1.getSelectedItem().equals("Shift")) {
					capMod += 1;
					capMods[0] = 1;
				}
				if (capCmbCtr1.getSelectedItem().equals("Alt")) {
					capMod += 8;
					capMods[0] = 2;
				}
				if (capCmbCtr2.getSelectedItem().equals("Ctrl")) {
					capMod += 2;
					capMods[1] = 0;
				}
				if (capCmbCtr2.getSelectedItem().equals("Shift")) {
					capMod += 1;
					capMods[1] = 1;
				}
				if (capCmbCtr2.getSelectedItem().equals("Alt")) {
					capMod += 8;
					capMods[1] = 2;
				}
				
				if (delCmbCtr1.getSelectedItem().equals("Ctrl")) {
					delMod += 2;
					delMods[0] = 0;
				}
				if (delCmbCtr1.getSelectedItem().equals("Shift")) {
					delMod += 1;
					delMods[0] = 1;
				}
				if (delCmbCtr1.getSelectedItem().equals("Alt")) {
					delMod += 8;
					delMods[0] = 2;
				}
				if (delCmbCtr2.getSelectedItem().equals("Ctrl")) {
					delMod += 2;
					delMods[1] = 0;
				}
				if (delCmbCtr2.getSelectedItem().equals("Shift")) {
					delMod += 1;
					delMods[1] = 1;
				}
				if (delCmbCtr2.getSelectedItem().equals("Alt")) {
					delMod += 8;
					delMods[1] = 2;
				}
				Main.captureModifiers = capMod;
				Main.deleteModifiers = delMod;
				Main.captureKey = capCmbKey1.getSelectedIndex() + 65;
				Main.deleteKey = delCmbKey1.getSelectedIndex() + 65;
				Main.captureModifiersSet = capMods;
				Main.deleteModifiersSet = delMods;
				dispose();
			}
		});
		btnSave.setBounds(110, 119, 89, 23);
		contentPane.add(btnSave);
	}
}
