package com.wgz.test;

import java.io.IOException;

public class ParamTest {
	
	private static void operateFun(int a,Integer aoo,int[] arr) {
		a = 1;
		aoo = new Integer(1);
		arr[0] = 1;
	}
	public static void main(String[] args) throws IOException{
		int b = 2;
		Integer boo = new Integer(2);
		int[] barr = {2};
		operateFun(b,boo,barr);
		System.out.println(b);
		System.out.println(boo);
		System.out.println(barr[0]);
	}
}
