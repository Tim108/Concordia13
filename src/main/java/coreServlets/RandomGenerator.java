package coreServlets;

import java.util.Random;

public class RandomGenerator {

	public StringBuilder createActivition() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder activationCode = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			char c = chars[random.nextInt(chars.length)];
			activationCode.append(c);
		}
		return activationCode;
	}
}
