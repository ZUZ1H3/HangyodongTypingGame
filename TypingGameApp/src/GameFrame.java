import java.awt.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;

public class GameFrame extends JFrame {
	CardLayout cardLayout;
	private Container contentPane;
	private MainPanel mainPanel;
	private GamePanel gamePanel;
	private SaveWordPanel saveWordPanel;
	private RankPanel rankPanel;
	private ScorePanel scorePanel;
	private Player player;
	private RulesPanel rulesPanel;

	private File file;
	private AudioInputStream audioStream;
	private Clip clip;

	public GameFrame() {
		setTitle("배고픈 한교동");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 690);
		setLocationRelativeTo(null);
		setVisible(true);
		contentPane = this.getContentPane();
		cardLayout = new CardLayout();
		contentPane.setLayout(cardLayout);

		
		file = new File("music.wav");
		try {
			audioStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
		} catch (Exception e) {
		}
		playMusic(); // 음악 재생
		
		// Panel들 생성
		scorePanel = new ScorePanel(contentPane, gamePanel);
		gamePanel = new GamePanel(contentPane, scorePanel, player);
		mainPanel = new MainPanel(contentPane);
		saveWordPanel = new SaveWordPanel(contentPane);
		rankPanel = new RankPanel(contentPane);
		rulesPanel = new RulesPanel(contentPane);

		// 카드패널을 컨텐트팬에 부착한다.
		contentPane.add(mainPanel, "mainPanel"); // 패널부착, 패널식별자
		contentPane.add(saveWordPanel, "saveWordPanel");
		contentPane.add(rankPanel, "rankPanel");
		contentPane.add(gamePanel, "gamePanel");
		contentPane.add(scorePanel, "scorePanel");
		contentPane.add(rulesPanel, "rulesPanel");
	}

	private void playMusic() {
        if (clip != null) {
        	clip.loop(Clip.LOOP_CONTINUOUSLY); // 무한 반복
            clip.start();
        } else {
            System.out.println("clip is null");
        }
    }
}
