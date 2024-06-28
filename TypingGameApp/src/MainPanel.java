import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MainPanel extends JPanel {
	private int score = 0;

	private Container contentPane;
	private CardLayout cardLayout;
	private ScorePanel scorePanel;
	private Player player = new Player();
	private GamePanel gamePanel = new GamePanel();

	// 이미지
	private ImageIcon BackGroundIcon = new ImageIcon("./Image/BackGround.png"); // 배경화면
	private ImageIcon StartBtnICon = new ImageIcon("./Image/StartBtn.png"); // 게임시작 버튼
	private ImageIcon StartBtnICon2 = new ImageIcon("./Image/StartBtn2.png"); // 게임시작 버튼에 마우스를 올렸을 때 (버튼색이 보라색으로 변경됨)
	private ImageIcon SaveWordBtnICon = new ImageIcon("./Image/SaveWordBtn.png"); // 단어저장 버튼
	private ImageIcon SaveWordBtnICon2 = new ImageIcon("./Image/SaveWordBtn2.png"); // 단어저장 버튼에 마우스를 올렸을 때
	private ImageIcon RankingICon = new ImageIcon("./Image/RankingBtn.png"); // 랭킹 보기 버튼
	private ImageIcon RankingICon2 = new ImageIcon("./Image/RankingBtn2.png"); // 랭킹 보기 버튼에 마우스를 올렸을 때
	private ImageIcon RulesICon = new ImageIcon("./Image/RulesBtn.png"); // 게임방법 보기 버튼
	private ImageIcon RulesICon2 = new ImageIcon("./Image/RulesBtn2.png"); // 게임방법 버튼에 마우스를 올렸을 때
	private ImageIcon OctopusICon = new ImageIcon("./Image/Octopus.png"); // 게임방법 보기 버튼
	private ImageIcon NameLabel = new ImageIcon("./Image/NameLabel.png"); // '이름' Label icon

	// 이름 입력 필드
	private JLabel name = new JLabel(NameLabel); // '이름' Label
	private JTextField inputName = new JTextField(10); // player 이름 입력칸
	private JLabel inForm = new JLabel("이름을 입력 해주세요!");

	// 버튼
	private JButton startBtn = new JButton(StartBtnICon); // 게임 시작 버튼
	private JButton saveWordBtn = new JButton(SaveWordBtnICon);// 단어 저장 버튼
	private JButton rankingBtn = new JButton(RankingICon); // 랭킹 보기 버튼
	private JButton rulesBtn = new JButton(RulesICon); // 게임 방법 버튼

	
	private JLabel octopus = new JLabel(OctopusICon); // 문어 레이블
	public MainPanel(Container contentPane) {
		this.contentPane = contentPane;
		this.cardLayout = (CardLayout) contentPane.getLayout();
		scorePanel = new ScorePanel(contentPane, gamePanel);
		gamePanel = new GamePanel(contentPane, scorePanel, player);
		setLayout(null);

		// 이름 레이블
		name.setBounds(320, 295, 150, 50);
		add(name);

		// 이름 입력 칸
		inputName.setFont(new Font("카페24 써라운드", 0, 20));
		inputName.setBounds(440, 300, 200, 38);
		add(inputName);

		// 이름을 입력하지 않고 게임시작을 눌렀을 때'이름을 입력하세요!' 라고 뜸
		inForm.setFont(new Font("카페24 써라운드", 0, 17));
		inForm.setBounds(440, 335, 200, 38);

		// 게임 시작 버튼
		startBtn.setBounds(416, 390, 150, 50);
		startBtn.setBorderPainted(false); // 테두리를 그리지 않음
		startBtn.setContentAreaFilled(false); // 버튼배경을 투명하게
		startBtn.setRolloverIcon(StartBtnICon2); // 마우스를 올릴 때 (버튼 색이 보라색으로 변경)
		add(startBtn);
		
		// 게임 방법 버튼
		rulesBtn.setBounds(416, 460, 150, 50);
		rulesBtn.setBorderPainted(false);
		rulesBtn.setContentAreaFilled(false);
		rulesBtn.setRolloverIcon(RulesICon2);
		add(rulesBtn);
		
		// 단어 저장 버튼
		saveWordBtn.setBounds(416, 530, 150, 50);
		saveWordBtn.setBorderPainted(false);
		saveWordBtn.setContentAreaFilled(false);
		saveWordBtn.setRolloverIcon(SaveWordBtnICon2);
		add(saveWordBtn);

		//랭킹 보기 버튼
		rankingBtn.setBounds(75, 395, 150, 50);
		rankingBtn.setBorderPainted(false);
		rankingBtn.setContentAreaFilled(false);
		rankingBtn.setRolloverIcon(RankingICon2);
		add(rankingBtn);
		
		//문어 캐릭터
		octopus.setBounds(15, 360, 250, 300);
		add(octopus);

		// 버튼 액션리스너
		startBtn.addActionListener(new ActionListener() { // 버튼이 눌리면
			public void actionPerformed(ActionEvent e) {
				if (inputName.getText().trim().isEmpty()) {
					add(inForm);
					inForm.setVisible(true);
					repaint();
					return;
				}
				player = new Player(inputName.getText(), score);
				player.setName(inputName.getText());
				gamePanel = new GamePanel(contentPane, scorePanel, player); // 게임패널 생성

				splitPane();
				cardLayout.show(contentPane, "hPane"); // 화면을 gamePanel로 바꿔주고
				gamePanel.gameStart(player);
			}
		});

		//게임 설명 패널로 감
		rulesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPane, "rulesPanel");
			}
		});
		
		//단어 저장 패널로 감
		saveWordBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPane, "saveWordPanel");
			}
		});

		//랭킹 패널로 감
		rankingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPane, "rankPanel");
			}
		});
	}

	//화면을 gamePanel, scorePanel 반반씩 나오게
	private void splitPane() {
		JSplitPane hPane = new JSplitPane(); // 수평으로 나누는
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT); // 수평으로 쪼갬
		hPane.setDividerLocation(740); // 수평선 위치
		hPane.setEnabled(false); // 움직이지 x
		hPane.setLeftComponent(gamePanel); // 내부 패널의 왼쪽 컴포넌트 추가
		hPane.setRightComponent(scorePanel); // 내부 패널의 오른쪽 컴포넌트 추가
		contentPane.add(hPane, "hPane");
	}

	//배경화면을 그리는 함수
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(BackGroundIcon.getImage(), 0, 0, null);
		setOpaque(false);
	}
}
