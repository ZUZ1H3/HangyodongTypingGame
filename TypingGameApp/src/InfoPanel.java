import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InfoPanel extends JDialog {
    private Timer timer;
    private JLabel levelUpLabel = new JLabel();
    private ImageIcon[] imagelist = {
            new ImageIcon("./image/Dialog1.png"),
            new ImageIcon("./image/Dialog2.png"),
            new ImageIcon("./image/Dialog3.png"),
            new ImageIcon("./image/Dialog4.png"),
            new ImageIcon("./image/Dialog5.png"),
            new ImageIcon("./image/Dialog6.png")
    };
    private ImageIcon image = imagelist[0];
    private ImageIcon image2 = imagelist[1];

    private int time = 0;

    public InfoPanel(JFrame parent) {
        setSize(350, 250);
        setLocationRelativeTo(parent); // InfoPanel 창이 parent 창에 상대적으로 위치하게 함
        setLayout(new BorderLayout());

        // 레벨업 라벨 설정
        levelUpLabel.setIcon(image);
        levelUpLabel.setHorizontalAlignment(JLabel.CENTER);
        add(levelUpLabel, BorderLayout.CENTER);

        // 타이머 설정 (1초마다 레이블 이미지 변경 및 경과 시간 측정)
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1초 경과
            	time++;
                // 이미지 번갈아가며 표시
                if (time % 2 == 0) {
                    levelUpLabel.setIcon(image);
                } else {
                    levelUpLabel.setIcon(image2);
                }
                // time>=6 일 때 다이얼로그 닫기
                if (time >= 6) {
                    dispose();
                    timer.stop(); // 타이머 중지
                    time = 0;
                }
            }
        });
    }

    //1레벨일때
    public void showDialog1() {
        image = imagelist[0];
        image2 = imagelist[1];
        timer.start(); // 타이머 시작
        setVisible(true); // 다이얼로그 표시
    }

    //2레벨일떄
    public void showDialog2() {
        image = imagelist[2];
        image2 = imagelist[3];
        timer.start(); // 타이머 시작
        // 이미지 변경을 기다림
        try {
            Thread.sleep(1000); // 1초 동안 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setVisible(true); // 다이얼로그 표시
    }
    
    //3레벨일때
    public void showDialog3() {
        image = imagelist[4];
        image2 = imagelist[5];
        timer.start(); // 타이머 시작
        // 이미지 변경을 기다림
        try {
            Thread.sleep(1000); // 1초 동안 대기 (타이머 주기와 동일하게 설정)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setVisible(true); // 다이얼로그 표시
    }
}
