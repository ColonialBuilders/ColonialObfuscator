package colonialobfuscator.utils;

import java.util.Random;

public class NameGen {
	
	public static String String(int i) {
		StringBuilder string = new StringBuilder();
		int i2 = 0;
		while(i >= i2) {
			string.append(new Random().nextInt(9000) + 20000);
			i2++;
		}
		return string.toString();
	}

}