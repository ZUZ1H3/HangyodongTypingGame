import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ScorePanel extends JPanel {
	private Player player = new Player();
	private Container contentPane = null;
	private GamePanel gamePanel = new GamePanel();

	private ImageIcon HappyFaceIcon = new ImageIcon("./Image/HangyodongHappy.png");
	private ImageIcon NormalFaceIcon = new ImageIcon("./Image/HangyodongNormal.png");
	private ImageIcon SadFaceIcon = new ImageIcon("./Image/HangyodongSad.png");

	private ImageIcon HeartIcon = new ImageIcon("./Image/heart.png");
	private ImageIcon ComboIcon = new ImageIcon("./Image/ComboLabel.png");
	private ImageIcon level1 = new ImageIcon("./Image/Level1.png");
	private ImageIcon level2 = new ImageIcon("./Image/Level2.png");
	private ImageIcon level3 = new ImageIcon("./Image/Level3.png");

	private JLabel ComboLabel = new JLabel(ComboIcon);
	private JLabel HangyodongLabel = new JLabel(NormalFaceIcon);

	// 점수
	private int score = 0;
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	private JLabel scoreLabel2 = new JLabel("점");
	private JLabel levelLabel = new JLabel(level1);

	// 하트(생명)
	private int life = 5;
	private JLabel[] lifeLabel = new JLabel[5];

	Color pinkColor = Color.decode("#F1B7D1");

	JLabel food = new JLabel();

	public ScorePanel(Container contentPane, GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		this.setBackground(pinkColor);
		setLayout(null);

		levelLabel.setBounds(0, 0, 230, 100);
		levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		add(levelLabel);
		
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		scoreLabel.setFont(new Font("카페24 써라운드", 1, 37));
		scoreLabel.setBounds(0, 90, 180, 50); // x, y, width, height
		add(scoreLabel);

		scoreLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		scoreLabel2.setFont(new Font("카페24 써라운드", 1, 37));
		scoreLabel2.setBounds(180, 90, 40, 50); // x, y, width, height
		add(scoreLabel2);

		food.setBounds(18, 472, 130, 100);
		add(food);

		// 한교동 그림
		HangyodongLabel.setBounds(0, 150, 235, 680);
		add(HangyodongLabel);

		ComboLabel.setBounds(20, 190, 200, 50);
		add(ComboLabel);
		ComboLabel.setVisible(false);
		addHeart();
	}

	public void comboVisible() {
		ComboLabel.setVisible(true);
	}

	public void comboHidden() {
		ComboLabel.setVisible(false);
	}

	// 하트 5개
	public void addHeart() {
		for (int i = 0; i < life; i++) {
			lifeLabel[i] = new JLabel(HeartIcon);
			lifeLabel[i].setBounds(6 + (i * 45), 260, 40, 40);
			add(lifeLabel[i]);
		}
	}

	public void returnIcon(ImageIcon icon) {
		food.setIcon(icon);
		food.setVisible(true);
		repaint();

		Timer timer = new Timer(500, e -> {
			food.setVisible(false);
			repaint();
		});
		// 타이머 시작
		timer.setRepeats(false); // 한 번만 실행되도록 설정
		timer.start();
	}

	public void returnLevel(int level) {
		if(level==2) {
		    levelLabel.setIcon(level2);
		}
		else if(level==3) {
		    levelLabel.setIcon(level3);
		}
	    repaint();
	}
	
	// 한교동의 얼굴을 변경하는 메소드
	public void changeFace(ImageIcon icon) {
		HangyodongLabel.setIcon(icon);

		Timer timer = new Timer(500, e -> {
			HangyodongLabel.setIcon(NormalFaceIcon);// 0.5초 후에 다시 NormalFaceIcon으로 변경

		});
		// 타이머 시작
		timer.setRepeats(false); // 한 번만 실행되도록 설정
		timer.start();
	}

	// 점수 증가
	synchronized public void increase(Player player, int combo) {
		if (combo >= 5)
			score += 20;
		else
			score += 10;
		player.setScore(score);
		scoreLabel.setText(Integer.toString(score));
		System.out.println("점수 " + score + "로 증가  ");
		scoreLabel.getParent().repaint();

		// 아이콘 변경
		changeFace(HappyFaceIcon);
	}

	// 점수 감소
	synchronized public void decrease(Player player) {
		score -= 10;
		player.setScore(score);
		scoreLabel.setText(Integer.toString(score));
		System.out.println("점수 " + score + "로 감소  ");
		scoreLabel.getParent().repaint();
		// 아이콘 변경
		changeFace(SadFaceIcon);
	}

	public void repaintScore() {
		scoreLabel.getParent().repaint();
	}

	public void initPlayerInfo(String name, int level, int score) {
		player = new Player(name, score);
	}

	synchronized boolean decreaseLife(Player player) {
		life--;
		boolean isTrue = false;
		switch (life) {
		case 4: // ♥ ♥ ♥ ♥ 
			lifeLabel[4].setVisible(false);
			break;
		case 3: // ♥ ♥ ♥
			lifeLabel[3].setVisible(false);
			break;
		case 2: // ♥ ♥
			lifeLabel[2].setVisible(false);
			break;
		case 1: // ♥
			lifeLabel[1].setVisible(false);
			break;
		case 0:
			lifeLabel[0].setVisible(false);
			// 게임 종료 후 정보 저장
			Player p = new Player(player.getName(), player.getScore());
			p.storeInfo();
			isTrue = true;
			// 종료할것인지 물어보는 JOptionPane
			String[] answer = { "종료", "메인으로" };
			int choice = JOptionPane.showOptionDialog(gamePanel,
					player.getName() + "은(는) " + player.getScore() + "점 입니다", "GAME OVER",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, answer, null);

			if (choice == 0) { // "예" 선택. 창 닫는다
				System.exit(JFrame.EXIT_ON_CLOSE);
			} else if (choice == 1) { // "다시시작" 선택. 현재 프레임 닫고 새 프레임 연다
				// 스레드 종료하고 다시 시작...
				GameFrame f = new GameFrame();
			}
			break;
		}
		return isTrue;
	}
}
