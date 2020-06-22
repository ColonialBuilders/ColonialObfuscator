package colonialobfuscator.utils;

import java.util.Random;

public class NameGen {
	
	static boolean StringInt = true;
	public static String String(int i) {
		StringBuilder string = new StringBuilder();
		int i2 = 0;
		while(i >= i2) {
			if(StringInt) {
				string.append(new Random().nextInt(9000) + 20000);
			} else {
				string.append(new String(new char[] {(char) (new Random().nextInt(9000) + 20000)}));
			}
			i2++;
		}
		return string.toString();
	}
	
	public static String colonial() {
		return "ColonialObfuscator_";
	}

}
