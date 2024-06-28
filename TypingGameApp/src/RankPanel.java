import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import javax.swing.*;

public class RankPanel extends JPanel {
    private Container contentPane;
    private CardLayout cardLayout;
    private ImageIcon BackGroundIcon = new ImageIcon("./Image/BackGroundRanking.png");
    private ImageIcon BackBtnIcon = new ImageIcon("./Image/BackBtn.png");
    private ImageIcon BackBtnIcon2 = new ImageIcon("./Image/BackBtn2.png");

    private JButton backBtn = new JButton(BackBtnIcon);

    private JLabel[] rankLabel = new JLabel[5];
    private JLabel[] scoreText = new JLabel[5];

    public RankPanel(Container contentPane) {
        this.contentPane = contentPane;
        this.cardLayout = (CardLayout) contentPane.getLayout();
        this.setLayout(null);

        String fileName = "./ranking.txt";
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));

            int i = 0;
            String line;
            while (i < 5 && (line = in.readLine()) != null) {
                String[] splitLine = line.trim().split(",");
                rankLabel[i] = new JLabel(splitLine[0]);
                scoreText[i] = new JLabel(splitLine[1] + "점");

                rankLabel[i].setFont(new Font("카페24 써라운드", 1, 38));
                scoreText[i].setFont(new Font("카페24 써라운드", 1, 38));
                rankLabel[i].setBounds(335, 200 + i * 74, 800, 40);
                scoreText[i].setBounds(580, 200 + i * 74, 700, 40);

                add(rankLabel[i]);
                add(scoreText[i]);

                i++;
            }

        } catch (IOException e1) {
            System.out.println("해당 랭킹파일 없음");
        }

        backBtn.setBounds(35, 570, 150, 50);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setRolloverIcon(BackBtnIcon2);
        add(backBtn);

    	backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPane, "mainPanel");

			}
		});
	}


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BackGroundIcon.getImage(), 0, 0, null);
        setOpaque(false);
    }
}
