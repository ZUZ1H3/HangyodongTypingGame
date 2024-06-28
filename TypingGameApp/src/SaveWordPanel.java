import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class SaveWordPanel extends JPanel {
	private Container contentPane;
	private CardLayout cardLayout;
	private ImageIcon BackGroundIcon = new ImageIcon("./Image/BackGround.png"); // 배경화면
	private ImageIcon BackBtnIcon = new ImageIcon("./Image/BackBtn.png"); // 단어저장 버튼
	private ImageIcon BackBtnIcon2 = new ImageIcon("./Image/BackBtn2.png"); // 단어저장 버튼

	private JButton backBtn = new JButton(BackBtnIcon);

	private ImageIcon SaveWordFieldIcon = new ImageIcon("./Image/SaveWordField.png"); // '레벨' Label icon
	private JLabel SaveWordField = new JLabel(SaveWordFieldIcon);
	private JTextField inputWord = new JTextField(10);
	private JLabel save = new JLabel();

	public SaveWordPanel(Container contentPane) {
		this.contentPane = contentPane;
		this.cardLayout = (CardLayout) contentPane.getLayout();
		this.setLayout(null);

		backBtn.setBounds(35, 570, 150, 50);
		backBtn.setBorderPainted(false);
		backBtn.setContentAreaFilled(false);
		backBtn.setRolloverIcon(BackBtnIcon2); // 마우스를 올릴 때 (버튼 색이 보라색으로 변경)
		add(backBtn);
		
		SaveWordField.setBounds(280, 270, 400, 110); // 저장할 단어 입력창(이미지)
		add(SaveWordField);
		
		inputWord.setHorizontalAlignment(JTextField.CENTER); // 가운데 정렬
		inputWord.setFont(new Font("카페24 써라운드", 0, 30));
		inputWord.setBounds(357, 320, 270, 45);
		inputWord.setBorder(null); // 테두리를 그리지 않음
		add(inputWord);
		
		save.setHorizontalAlignment(SwingConstants.CENTER);
		save.setFont(new Font("카페24 써라운드", 0, 20));
		save.setBounds(290, 350, 400, 110);
		add(save);
		
		inputWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = inputWord.getText();
				try (BufferedReader reader = new BufferedReader(new FileReader("word.txt"))) {
					String line;
					while ((line = reader.readLine()) != null) {
						// 중복된 단어가 이미 파일에 존재하는 경우 저장하지 않고 종료
						if (line.trim().equalsIgnoreCase(word.trim())) {
							save.setText("'" + word + "' (은)는 이미 저장된 단어입니다.");
							

							inputWord.setText("");
							repaint();
							return; // 중복된 단어가 있으므로 종료
						}
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				// 중복된 단어가 없는 경우에만 저장
				try (FileWriter out = new FileWriter("word.txt", true)) {
					out.write("\n" + word);
					out.flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				save.setText("' " + word + " ' (이)가 저장되었습니다.");
				inputWord.setText("");
				repaint();
			}
		});

		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPane, "mainPanel"); // 화면을 gamePanel로 바꿈
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(BackGroundIcon.getImage(), 0, 0, null);
		setOpaque(false);
	}
}
