import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RulesPanel extends JPanel {
	private Container contentPane;
	private CardLayout cardLayout;
	private ImageIcon BackGroundIcon = new ImageIcon("./Image/BackGroundRules.png");
	private ImageIcon BackBtnIcon = new ImageIcon("./Image/BackBtn.png"); //
	private ImageIcon BackBtnIcon2 = new ImageIcon("./Image/BackBtn2.png");

	private JButton backBtn = new JButton(BackBtnIcon);

	public RulesPanel(Container contentPane) {
		this.contentPane = contentPane;
		this.cardLayout = (CardLayout) contentPane.getLayout();
		this.setLayout(null);

		backBtn.setBounds(420, 515, 150, 50);
		backBtn.setBorderPainted(false);
		backBtn.setContentAreaFilled(false);
		backBtn.setRolloverIcon(BackBtnIcon2); // 마우스를 올릴 때 (버튼 색이 보라색으로 변경)
		add(backBtn);

		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPane, "mainPanel");
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(BackGroundIcon.getImage(), 0, 0, null);
		setOpaque(false);
	}
}
