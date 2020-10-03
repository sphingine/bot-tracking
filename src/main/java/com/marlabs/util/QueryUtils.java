package com.marlabs.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.marlabs.constants.Constants;

@Component
public class QueryUtils {

	public String getQuery() throws NoSuchAlgorithmException {
		Random rand = SecureRandom.getInstanceStrong();

		int[] randomNumbers = new int[4];
		for (int i = 0; i <3; i++) {

			int low = 1;
			int high = 100;
			randomNumbers[i] = rand.nextInt(high-low) + low;

		}
		return  Constants.STATEMENT + 
				randomNumbers[0] + Constants.COMMA + randomNumbers[1] + Constants.COMMA + randomNumbers[2];
	}

	public int getSum (String query) {
		query = query.replace(Constants.STATEMENT, "");
		int result = 0;

		String[] randomNumbers = query.split(Constants.COMMA);
		for (String i : randomNumbers) {
			result += Integer.parseInt(i);
		}
		return result;
	}
}