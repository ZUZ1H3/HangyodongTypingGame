import java.io.*;
import java.util.*;

public class TextSource {
	private Vector<String> v = new Vector<String>();

	public TextSource() { // word.txt에서 단어를 읽어와 Vector에 저장하는 부분
		String word;
		Scanner scanner;
		try {
			scanner = new Scanner(new FileReader("./word.txt"));
			while (scanner.hasNext()) { // 파일의 끝까지 반복하며 각 줄의 단어를 Vector에 추가
				word = scanner.nextLine();
				v.add(word);
			}
			scanner.close();
		} catch (FileNotFoundException e) { // 파일을 찾을 수 없는 경우 예외 처리
			e.printStackTrace();
		}
	}

	// 무작위로 단어를 선택하여 반환하는 메서드
	public String get() {
		int index = (int) (Math.random() * v.size());
		return v.get(index);
	}
}
