package eg.edu.alexu.csd.datastructure.mailServer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import eg.edu.alexu.csd.datastructure.mailServer.App;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DateEdit extends JFrame {

	private JPanel contentPane;
	private App app;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DateEdit frame = new DateEdit();
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
	public DateEdit(App app, JLabel dateLabel, JFrame mainFrame) {
		this.app = app;
		setTitle("Birth date edit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] days = new String[31];
		for (int i = 1; i <= 31; i++) {
			days[i - 1] = Integer.toString(i);
		}
		
		JComboBox dayCB = new JComboBox(days);
		dayCB.setBounds(55, 81, 40, 21);
		contentPane.add(dayCB);
		
		JLabel dayLabel = new JLabel("day");
		dayLabel.setForeground(new Color(0, 139, 139));
		dayLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dayLabel.setBounds(92, 81, 45, 21);
		contentPane.add(dayLabel);
		
		String[] months = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		
		JComboBox monthCB = new JComboBox(months);
		monthCB.setBounds(164, 81, 40, 21);
		contentPane.add(monthCB);
		
		JLabel monthLabel = new JLabel("month");
		monthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		monthLabel.setForeground(new Color(0, 139, 139));
		monthLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		monthLabel.setBounds(207, 81, 55, 21);
		contentPane.add(monthLabel);
		
		String[] years = new String[80];
		for (int i = 0; i < 80; i++) {
			years[i] = Integer.toString(i + 1941);
		}
		
		JComboBox yearCB = new JComboBox(years);
		yearCB.setBounds(292, 81, 62, 21);
		contentPane.add(yearCB);
		
		JLabel yearLabel = new JLabel("year");
		yearLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yearLabel.setForeground(new Color(0, 139, 139));
		yearLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		yearLabel.setBounds(348, 81, 55, 21);
		contentPane.add(yearLabel);
		
		JButton btnNewButton = new JButton("Confirm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String date = monthCB.getSelectedItem() + "-" + dayCB.getSelectedItem() + "-"
						+ yearCB.getSelectedItem();
				if (app.signedInUser.setBirthDate(date)) {
					app.signedInUser.writeToFile();
					dateLabel.setText(app.signedInUser.getBirthDate());
					dispose();
					mainFrame.setEnabled(true);
					mainFrame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Invalid date");
				}
			}
		});
		btnNewButton.setBackground(new Color(245, 245, 245));
		btnNewButton.setForeground(new Color(0, 206, 209));
		btnNewButton.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		btnNewButton.setBounds(66, 171, 138, 35);
		contentPane.add(btnNewButton);
		
		JButton btnCansel = new JButton("Cancel");
		btnCansel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				mainFrame.setEnabled(true);
				mainFrame.setVisible(true);
			}
		});
		btnCansel.setForeground(new Color(0, 206, 209));
		btnCansel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		btnCansel.setBackground(new Color(245, 245, 245));
		btnCansel.setBounds(250, 171, 111, 35);
		contentPane.add(btnCansel);
		
		setVisible(true);
	}
}