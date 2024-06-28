import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.*;

public class Player extends JPanel {
	private String name;
	private int score;

	public Player() {

	}
	
	public Player(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void storeInfo() { //user.txt에 이름과 점수 저장하기 
		try {
			String fileName = "user.txt";
			FileWriter fw = new FileWriter(fileName, true); //파일을 연다. 기존 파일에 이어쓰기한다.
			String data = name + "," + score + "\n"; //이름과 점수
			fw.write(data); //파일에 데이터를 저장한다
			fw.close(); 
			sortInfo(fileName);
		}
		catch (IOException e) {
			System.out.println(e);
		}

	}

	public void sortInfo(String fileName) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
			FileWriter out = new FileWriter("ranking.txt", false); // 덮어쓰기 모드
			String line;
			String[] data = new String[2];

			// 해쉬맵에 데이터 저장
			Map<String, Integer> temp = new HashMap<>();
			while ((line = in.readLine()) != null) {
				data = line.trim().split(","); //이름, 스코어를 ,쉼표로 나눠서 데이터 저장
				// data[0]은 name, data[1]은 score
				temp.put(data[0], Integer.parseInt(data[1]));
			}

			// 리스트 생성 //이름과 점수 
			List<Entry<String, Integer>> rank = new ArrayList<Entry<String, Integer>>(temp.entrySet());

			// Comparator로 정렬
			Collections.sort(rank, new Comparator<Entry<String, Integer>>() {
				// compare로 값을 비교
				public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
					// 내림 차순으로 정렬
					return obj2.getValue().compareTo(obj1.getValue());
				}
			});

			// 정렬된 데이터 저장
			for (Entry<String, Integer> entry : rank) {
				String rankData;
				rankData = entry.getKey() + "," + Integer.toString(entry.getValue()) + "\n";
				out.write(rankData);
				out.flush();
			}
			out.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
