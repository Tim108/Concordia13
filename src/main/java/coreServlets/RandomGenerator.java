package coreServlets;

import java.util.Random;

public class RandomGenerator {

	public String createActivition() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		StringBuilder activationCode = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 25; i++) {
			char c = chars[random.nextInt(chars.length)];
			activationCode.append(c);
		}
		return activationCode.toString();
	}
}
