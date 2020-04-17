package com.wgz.test;

public class LinkedListOfDoubleTest {

	public static void main(String[] args) {
		
		//System.out.println(5/2);
		//System.out.println("================================================");
		
		LinkedListOfDouble<Integer> doubleLinkedList = new LinkedListOfDouble<Integer>();
		
		// 添加前节点
		doubleLinkedList.addFirst(0);
		doubleLinkedList.addFirst(1);	
		doubleLinkedList.addFirst(2);
		doubleLinkedList.addFirst(3);
		doubleLinkedList.addFirst(4);
		System.out.println(doubleLinkedList);
		
		// 按照指定索引添加, 测试前半段插入
		//doubleLinkedList.add(1, 5);
		//System.out.println(doubleLinkedList);
		
		//doubleLinkedList.add(0, 6);
		//System.out.println(doubleLinkedList);
		
		//测试后半段插入
		//doubleLinkedList.add(2, 6);
		//System.out.println(doubleLinkedList);
		
		//doubleLinkedList.add(3, 7);
		//System.out.println(doubleLinkedList);
		
		//doubleLinkedList.add(5, 8);
		//System.out.println(doubleLinkedList);
		
		// 删除 frist
		/*doubleLinkedList.removeFrist();
		System.out.println(doubleLinkedList);
		doubleLinkedList.removeFrist();
		System.out.println(doubleLinkedList);
		doubleLinkedList.removeFrist();
		System.out.println(doubleLinkedList);
		doubleLinkedList.removeFrist();
		System.out.println(doubleLinkedList);
		doubleLinkedList.removeFrist();
		System.out.println(doubleLinkedList);
		doubleLinkedList.removeFrist();
		System.out.println(doubleLinkedList);*/
		
		/*doubleLinkedList.removeLast();
		System.out.println(doubleLinkedList);
		doubleLinkedList.removeLast();
		System.out.println(doubleLinkedList);
		doubleLinkedList.removeLast();
		System.out.println(doubleLinkedList);
		doubleLinkedList.removeLast();
		System.out.println(doubleLinkedList);
		doubleLinkedList.removeLast();
		System.out.println(doubleLinkedList);
		doubleLinkedList.removeLast();
		System.out.println(doubleLinkedList);*/
		
		// 删除
		//doubleLinkedList.remove(0);
		//System.out.println(doubleLinkedList);
		
		// 删除前半段
//		doubleLinkedList.remove(1);
//		System.out.println(doubleLinkedList);
		
		// 删除后半段
//		doubleLinkedList.remove(2);
//		System.out.println(doubleLinkedList);
//		
//		doubleLinkedList.remove(3);
//		System.out.println(doubleLinkedList);
//		
//		doubleLinkedList.remove(4);
//		System.out.println(doubleLinkedList);
		

		doubleLinkedList.removeElement(4);
		System.out.println(doubleLinkedList);
		
		
	}
}
