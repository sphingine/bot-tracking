package com.marlabs.util;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.marlabs.constants.Constants;

@Component
public class QueryUtils {

	public String getQuery() {

		int[] randomNumbers = new int[4];
		for (int i = 0; i <3; i++) {
			Random r = new Random();
			int low = 1;
			int high = 100;
			randomNumbers[i] = r.nextInt(high-low) + low;

		}
		String query = Constants.STATEMENT + 
				randomNumbers[0] + Constants.COMMA + randomNumbers[1] + Constants.COMMA + randomNumbers[2];
		return query;
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