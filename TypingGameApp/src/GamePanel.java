import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

import javax.swing.*;

public class GamePanel extends JPanel {
	private Container contentPane = null;
	private CardLayout cardLayout = null;
	private ScorePanel scorePanel = null;
	private Player player = new Player();
	private TextSource textSource = new TextSource();

	private ImageIcon BackGroundIcon = new ImageIcon("./Image/BackGround2.png"); // 배경화면
	private ImageIcon progressBarBackGroundIcon = new ImageIcon("./Image/progressBarBackGround.png");
	private JLabel progressBarBackGround = new JLabel(progressBarBackGroundIcon); // 게이지바 보라색부분
	ImageIcon[] imageIcons = new ImageIcon[15]; // 음식 이미지를 넣을 배열

	private Color pinkColor = Color.decode("#F1B7D1"); // 핑크 배경 색

	private JTextField input = new JTextField(30); // 단어 입력 창
	private int level = 1; // 레벨

	private Vector<JLabel> targetVector = new Vector<JLabel>(); // targetLabel을 담는 Vector
	private Vector<JLabel> imageVector = new Vector<JLabel>(); // imageLabel을 담는 Vector

	// 레벨에 따른 난이도 조절
	private int[] generationSpeed = { 1500, 1300, 1100 }; // 생성속도
	private int[] droppingSpeed = { 80, 70, 60 }; // 떨어지는속도

	// 스레드
	GenerateWordThread generateWordThread = new GenerateWordThread(targetVector, imageVector, player);
	DropWordThread dropWordThread = new DropWordThread(targetVector, imageVector, player);
	DetectBottomThread detectBottomThread = new DetectBottomThread(targetVector, imageVector);

	private JProgressBar progressBar; // 게이지바
	private int gauge; // 게이지가 얼마나 찼는지?
	private InfoPanel levelUpDialog; // 레벨업! 다이얼로그
	int combo = 0; // combo <=5 일때 COMBO라고 뜸

	private GameGroundPanel gameGroundPanel = new GameGroundPanel();

	public GamePanel() {

	}
	public GamePanel(Container contentPane, ScorePanel scorePanel, Player player) {
		this.contentPane = contentPane;
		this.cardLayout = (CardLayout) contentPane.getLayout();
		this.scorePanel = scorePanel;
		this.player = player;
		setLayout(new BorderLayout());
		add(gameGroundPanel, BorderLayout.CENTER);

		// 아이콘 이미지 01 ~ 14를 배열에 집어넣기 위함
		for (int i = 0; i <= 9; i++) {
			imageIcons[i] = new ImageIcon("./Icon/0" + i + ".png");
		}
		for (int i = 10; i <= 14; i++) {
			imageIcons[i] = new ImageIcon("./Icon/" + i + ".png");
		}
		scorePanel.returnLevel(level);
		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				synchronized (targetVector) {
					synchronized (imageVector) {
						JTextField t = (JTextField) (e.getSource()); // 입력된 텍스트를 가져온다
						String word = t.getText(); // 입력한 단어
						for (int i = 0; i < targetVector.size(); i++) {
							String text = targetVector.get(i).getText();
							if (text.equals(word)) { // 올바른 단어를 맞췄다
								if (imageVector.get(i).getIcon() == imageIcons[7]) { // 문어그림이다
									combo = 0; // 콤보 초기화
									scorePanel.comboHidden();
									System.out.println("문어는 한교동의 친구입니다."); // 콘솔 확인 용
									scorePanel.decrease(player); // 점수 감소
									scorePanel.repaintScore();
									gameGroundPanel.remove(targetVector.get(i)); // 패널에서 없애기
									gameGroundPanel.remove(imageVector.get(i)); // 패널에서 없애기
									targetVector.remove(i); // targetVector에서 삭제
									imageVector.remove(i); // imageVector에서 삭제
									t.setText(null); // input 비우기
								} else { // 올바르게 맞췄다 (문어 그림이 아님)
									combo += 1;
									System.out.println(word + " 맞춤"); // 콘솔 확인 용
									scorePanel.increase(player, combo); // 점수 증가
									scorePanel.repaintScore();
									scorePanel.returnIcon((ImageIcon) imageVector.get(i).getIcon()); // 스코어패널에 음식아이콘을
									gameGroundPanel.remove(targetVector.get(i)); // 패널에서 없애기
									gameGroundPanel.remove(imageVector.get(i)); // 패널에서 없애기
									targetVector.remove(i); // targetVector에서 삭제
									imageVector.remove(i);// imageVector에서 삭제
									t.setText(null); // input 비우기
									gauge = progressBar.getValue();
									progressBar.setValue(gauge + 5); // 게이지 5 증가한다
									if (progressBar.getValue() == progressBar.getMaximum()) { // 게이지가 꽉 찼다면
										levelUp(); // 레벨업
										break;
									}
								}
								break;
							}
							// 틀리게 입력했다면
							else if (i == (targetVector.size() - 1) && !targetVector.get(i).getText().equals(word)) {
								combo = 0; // 콤보 초기화
								scorePanel.comboHidden();
								System.out.println(word + "틀림"); // 콘솔 확인용
								scorePanel.decrease(player); // 점수 10 감소
								scorePanel.repaintScore();
								t.setText(null);// input 비우기
							}
							t.requestFocus();
						}
					}
				}
				System.out.println(combo + "combo!"); // 콘솔 확인 용
				// 만약 단어를 5번 이상 잘 입력했을 경우
				if (combo >= 5) {
					scorePanel.comboVisible(); // score 패널에 COMBO 라는 레이블이 뜬다
				} else {
					scorePanel.comboHidden();
				}
			}
		});
		// 현재 컴포넌트가 속한 최상위 JFrame을 찾아내는 역할을 합니다.
		levelUpDialog = new InfoPanel((JFrame) SwingUtilities.getWindowAncestor(this));
	}

	class GameGroundPanel extends JPanel {
		public GameGroundPanel() {
			setLayout(null);
			input.setHorizontalAlignment(JTextField.CENTER); // 단어 입력 창 가운데 정렬
			input.setFont(new Font("카페24 써라운드", 0, 20)); // 폰트, 크기 등
			input.setBounds(280, 580, 200, 38);
			add(input);

			progressBar = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100); // 프로그래스 바 초기화
			progressBar.setBackground(Color.WHITE); // 게이지바 배경 흰색
			progressBar.setForeground(pinkColor); // 게이지 분홍색
			progressBar.setBorderPainted(false); // 테두리 제거
			progressBar.setValue(0); // 초기 값 설정
			progressBar.setBounds(50, 21, 640, 23); // 프로그래스 바의 위치와 크기 설정
			add(progressBar); // 프로그래스 바를 GameGroundPanel에 추가

			progressBarBackGround.setBounds(-40, 10, 820, 45); // 프로그래스 바의 위치와 크기 설정
			add(progressBarBackGround); // 프로그래스 바를 GameGroundPanel에 추가
		}

		// 배경 이미지를 설정함
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(BackGroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
			setOpaque(false);
		}
	}

	public void gameStart(Player player) {
		levelUpDialog.showDialog1();
		this.player = player;
		progressBar.setValue(0); // 게임 시작 시 프로그래스 바 초기화
		progressBar.setString("0%");
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				generateWordThread.start(); // 단어생성 시작
				dropWordThread.start(); // 단어 떨어뜨리기 시작
				detectBottomThread.start(); // 땅에 닿은 단어 감지 시작
				timer.cancel(); // Timer를 종료합니다.
			}
		}, 3000);

	}

	public void gameOver() {
		generateWordThread.interrupt(); // 단어생성 중단
		dropWordThread.interrupt(); // 단어 떨어뜨리기 중단
		detectBottomThread.interrupt(); // 땅에 닿은 단어 감지 중단
		System.out.print("게임오버");
	}

	// 게이지가 찼을 때 레벨 업
	private void levelUp() {
		combo = 0;
		if (level < 3) {
			if (level == 1)
				levelUpDialog.showDialog2();
			else if (level == 2)
				levelUpDialog.showDialog3();
			clear(); // 스레드 중지하고, 패널에 남은 단어 제거
			++level;
			progressBar.setValue(0); // 프로그레스 바 초기화
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					reset();
					timer.cancel(); // Timer를 종료합니다.
				}
			}, 4000); // 5초 (5000 밀리초) 후에 실행합니다.
			scorePanel.returnLevel(level);
			scorePanel.repaint();
		}

	}

	private void clear() {
		// targetVector 및 imageVector 비우기
		for (JLabel label : targetVector) {
			gameGroundPanel.remove(label);
		}
		for (JLabel label : imageVector) {
			gameGroundPanel.remove(label);
		}
		targetVector.clear();
		imageVector.clear();

		// 게임 스레드 중지
		generateWordThread.interrupt();
		dropWordThread.interrupt();
		detectBottomThread.interrupt();

		gameGroundPanel.repaint();
	}

	private void reset() {
		generateWordThread = new GenerateWordThread(targetVector, imageVector, player);
		dropWordThread = new DropWordThread(targetVector, imageVector, player);
		detectBottomThread = new DetectBottomThread(targetVector, imageVector);

		generateWordThread.start();
		dropWordThread.start();
		detectBottomThread.start();
		gameGroundPanel.repaint();
	}

	// 단어 생성하는 스레드 ----------------------------------------------------
	public class GenerateWordThread extends Thread {
		private Vector<JLabel> targetVector = null; // 생성된 단어들을 저장한다.
		private Vector<JLabel> imageVector = null; // 생성된 단어들을 저장한다.
		private Player player = null;

		public GenerateWordThread(Vector<JLabel> targetVector, Vector<JLabel> imaneVector, Player player) {
			this.targetVector = targetVector;
			this.imageVector = imaneVector;
			this.player = player;
		}

		synchronized void generateWord(Player player) {
			JLabel targetLabel = new JLabel("");
			String newWord = textSource.get(); // 랜덤 생성 단어 하나를 가져온다
			targetLabel.setText(newWord);

			int r;
			JLabel imageLabel = new JLabel("");
			if (level == 1)
				r = (int) (Math.random() * 8);
			else if (level == 2)
				r = (int) (Math.random() * 8 + 7);
			else
				r = (int) (Math.random() * imageIcons.length);
			imageLabel.setIcon(imageIcons[r]); // 랜덤 아이콘
			imageLabel.setSize(150, 40);

			targetLabel.setSize(150, 40);
			targetLabel.setHorizontalAlignment(JLabel.LEFT); // JLabel 가운데정렬
			targetLabel.setFont(new Font("카페24 써라운드", 0, 20));

			// x좌표를 랜덤으로 설정
			int startX = (int) (Math.random() * gameGroundPanel.getWidth());
			while (true) {
				if ((startX + targetLabel.getWidth()) > gameGroundPanel.getWidth())
					startX = (int) (Math.random() * gameGroundPanel.getWidth());
				else
					break;
			}
			targetLabel.setLocation(startX + 25, 0);
			imageLabel.setLocation(startX - 100, 0);

			targetLabel.setOpaque(false); // 배경 투명하게
			imageLabel.setOpaque(false); // 배경 투명하게

			targetVector.addElement(targetLabel); // targetVector에 생성한 newWord 추가
			imageVector.addElement(imageLabel);
			gameGroundPanel.add(targetLabel);
			gameGroundPanel.add(imageLabel);
		}

		public void run() {
			while (true) {
				int generationTime = generationSpeed[level - 1];
				generateWord(player);
				gameGroundPanel.repaint();
				try {
					sleep(generationTime);
				} catch (InterruptedException e) {
					return;
				}
			} // end of while
		} // end of run()
	}

	// 단어를 내리는 스레드 ----------------------------------------------------
	public class DropWordThread extends Thread {
		private Vector<JLabel> targetVector = null;
		private Vector<JLabel> imageVector = null;
		private Player player = null;

		public DropWordThread(Vector<JLabel> targetVector, Vector<JLabel> imageVector, Player player) {
			this.imageVector = imageVector;
			this.targetVector = targetVector;
			this.player = player;
		}

		// y좌표 증가해 단어 밑으로 내림
		synchronized void dropWord(Player player) {
			for (int i = 0; i < targetVector.size(); i++) {
				int x = targetVector.get(i).getX();
				int y = targetVector.get(i).getY();
				int iconX = imageVector.get(i).getX();
				targetVector.get(i).setLocation(x, y + 6);
				imageVector.get(i).setLocation(iconX, y + 6);
				gameGroundPanel.repaint();
			} // end of for

		}

		// targetVector에 들어있는 모든 JLabel들의 y좌표 증가
		@Override
		public void run() {
			while (true) {
				int dropTime = droppingSpeed[level - 1];
				dropWord(player);
				gameGroundPanel.repaint();
				try {
					sleep(dropTime);
				} catch (InterruptedException e) {
					return;
				}
			} // end of while
		} // end of run()
	}

	// 단어가 땅에 떨어졌을 때 스레드 ----------------------------------------------------
	public class DetectBottomThread extends Thread {
		private Vector<JLabel> imageLabel = null;
		private Vector<JLabel> targetVector = null;
		private ImageIcon SadFaceIcon = new ImageIcon("./Image/HangyodongSad.png");
		private boolean isGameOver;

		public DetectBottomThread(Vector<JLabel> targetVector, Vector<JLabel> imageVector) {
			this.targetVector = targetVector;
			this.imageLabel = imageVector;
		}

		@Override
		public void run() {
			while (true) {
				try {
					sleep(1);
					for (int i = 0; i < targetVector.size(); i++) {
						// 바닥에 닿은 단어 구하기 위함
						int y = ((JLabel) targetVector.get(i)).getY();
						if (y > gameGroundPanel.getHeight() - 20) {
							System.out.println(targetVector.get(i).getText() + " 떨어짐");
							
							if (imageVector.get(i).getIcon() != imageIcons[7]) {
								// true값이 반환되면 게임을 종료한다.
								isGameOver = scorePanel.decreaseLife(player);
								combo = 0;
								scorePanel.comboHidden();
							}
							if (isGameOver) { // 모든스레드 종료
								gameOver();
							}

							// 게임이 종료되지 않을 경우 패널에서 라벨 제거 게임 계속됨
							gameGroundPanel.remove(targetVector.get(i)); // 패널에서 라벨 떼기
							targetVector.remove(i); // targetVector에서 삭제
							gameGroundPanel.remove(imageLabel.get(i)); // 패널에서 라벨 떼기
							imageLabel.remove(i); // targetVector에서 삭제

							scorePanel.changeFace(SadFaceIcon);
						}
					}
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
}
