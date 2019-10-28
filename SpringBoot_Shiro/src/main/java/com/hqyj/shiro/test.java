package com.hqyj.shiro;

import java.util.ArrayList;

public class test {
	public static int maxSum = 0;
	public static ArrayList<String> record = new ArrayList<>();
	public static String recordsum;

	public static void main(String[] args) {
		String str1 = "123456abc12a";
		String str2 = "3ad";
		
		int count = 0;
		for (int i = 0; i < str2.length(); i++) {
			count = 0;
			for (int j = 0; j < str1.length(); j++) {
				if(str2.charAt(i) == str1.charAt(j)){
					count ++;
				}
			}
			System.out.println(str2.charAt(i)+" : "+count);
		}
//		m(30, 0, 0);
//		System.out.println("sum : " + maxSum);
//		System.out.println("recordsum : " + recordsum);
	}

	
	public static void m(int n, int sum, int cache) {
		
		/**
		 * 记录最大值sum,以及操作记录
		 */
		if (sum > maxSum) {
			maxSum = sum;
			recordsum = record.toString();
		}
		
		/**
		 * 按下A键，键数-1，和+1，剪切板不变
		 */
		if (n >= 1) {
			record.add("a :");
			m(n - 1, sum + 1, cache);
			record.remove(record.size() - 1);
		}
		
		/**
		 * 按下V键，键数-1，和+剪切板，剪切板不变
		 */
		if (n >= 1) {
			record.add("V : ");
			m(n - 1, sum + cache, cache);
			record.remove(record.size() - 1);
		}
		/**
		 * 按下A-C-V键，键数-3，和*2，剪切板=和
		 */
		if (n >= 3) {
			record.add("A-C-V :");
			m(n - 3, sum * 2, sum);
			record.remove(record.size() - 1);
		}
		return;
	}
}
