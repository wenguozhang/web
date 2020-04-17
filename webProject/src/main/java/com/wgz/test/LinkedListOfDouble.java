package com.wgz.test;

/**
 * 双向链表
 * 1. 添加时,找到自己节点即可,不用顾虑找到当前节点的上一个
 * @author adsmin
 *
 */
public class LinkedListOfDouble<E> {
	
	private class Node<E>{
		
    	// 存储元素
        public E e;
        
        // 下一个指针
        public Node<E> prev, next;

        /**
         * 
         * @param e
         * @param prev	前继节点
         * @param next	后继节点
         */
        public Node( Node<E> prev, E e, Node<E> next){
        	this.prev = prev;
            this.e = e;
            this.next = next;
        }

        public Node(E e){
            this( null, e, null);
        }

        public Node(){
            this(null, null, null);
        }
        
        public Node(E e, Node<E> next){
            this(null, e, next);
        }
        
        @Override
        public String toString(){
            return e.toString();
        }
    }
	
	// 第一个元素存在的节点,正向存储
	private Node<E> frist;
	
	// 最后一个元素存在的节点,反向存储
	private Node<E> last;
	
	private int size;
	
    public LinkedListOfDouble() {
    	// 不能直接初始化空节点,如果初始化,需要考虑在头添加是直接在这个节点上添加,类似虚拟头结点
    	//data = new Node();
    	frist = null;
    	size = 0;
    }
    
    /**
     * 获取 size
     * @return
     */
    public int getSize(){
        return size;
    }
    
    /**
     * 判断是否为空
     * @return
     */
    public boolean isEmpty(){
        return size == 0;
    }
    
    /**
     * 在链表头添加新的元素e
     * @param e
     */
    public void addFirst(E e){
    	
    	Node<E> f = frist;
    	Node<E> cur = new Node<E>(null, e, f);

    	// 先给 头结点赋值
    	frist = cur;
    	
    	// 维护前继节点指针
    	if(f == null) {
    		last = frist;
    	}else {
    		f.prev = cur;
    	}
    	size ++;
    }
    
    /**
     * 在链表尾部添加新元素e
     * @param e
     */
    public void addLast(E e){
    	
    	Node<E> l = last;
    	Node<E> cur = new Node(last, e, null);
    	last = cur;
    	
    	if(l == null) {
    		frist = last;
    	}else {
    		l.next = cur;
    	}
    }
    
    /**
     * 在  链表 index 位置添加 E
     * 找到 index 位置的元素很重要
     * @param index
     * @param e
     */
    public void add(int index, E e) {
    	
        if(index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Illegal index.");
        
        /**
    	 * 插入元素时, 可对index 进行判断,在链表的前半段, 可以在前段插入, 在后半段的可以在后面插入 
    	 */
    	//if(index <= size / 2) {
        
        if(index == size) {
        	addLast(e);
        	return;
        }
        
    	// 高级写法
    	if(index < (size >> 1)) {
    		
    		// 正向找到 自己(index) 位置的 node
    		// 如果 for 循环中 i<index-1, 找的是自己的上一个节点
    		Node<E> f = frist;
    		for (int i = 0; i < index; i++) {
    			// 这里指向了下一个
				f = f.next;
			}
    		
    		//当前节点的前一个
    		Node<E> prev = f.prev;
    		
    		// 创造新节点
    		Node<E> tmpNode = new Node<E>(prev, e, f);
    		// 置自己节点的前一个为新节点,即新节点的下一个为以前的 index 位置的node
    		f.prev = tmpNode;
    		
    		// f.prev 可能有为空的情况,如果为空,则说明是第一个节点
    		if(prev == null) {
    			frist = tmpNode;
    			
    		}else {
    			// 置自己节点前一个节点的下一个节点为 新节点,即将新节点加入链表中
    			prev.next = tmpNode;
    		}
    		size ++;
    		
    	}else{

    		// 逆向找到 index 位置的Node
    		Node<E> l = last;
    		// 这里起始循环只能 size-1,避免多往后遍历一个 Node
    		for(int i = size -1; i > index; i--) {
    			l = l.prev;
    		}
    		
    		Node<E> prev = l.prev;
    		
    		Node<E> tmpNode = new Node(prev, e, l);
    		
    		l.prev= tmpNode;
    		
    		if(prev == null) {
    			frist = tmpNode;
    		}else {
    			prev.next = tmpNode;
    		}
    		size ++;
    	}
    }
    
    /**
     * 删除头节点元素
     * @return
     */
    public E removeFrist() {
    	
    	// 找到前一个节点
    	Node<E> f = frist;
    	
    	if(f == null) {
    		throw new IllegalArgumentException("removeFrist failed. List is empty!");
    	}
    	
    	E cur = f.e;
    	
    	// 找到当前的下一个节点, 让下一个节点替换 当前节点
    	Node<E> next = f.next;
    	
    	frist = next;
    	// 如果下一个为 null, 则为最后一个
    	if(next == null) {
    		last = null;
    	}else {
    		next.prev = null;
    	}
    	
    	// 将当前节点置 null ,辅助 gc
    	f.e = null;
    	f.next = null;
    	// 前节点的 prev 本来就是null
    	//f.prev = null;
    	size --;
    	
    	return cur;
    }
    
    /**
     * 删除尾部节点的元素
     * @return
     */
    public E removeLast() {
    	
    	// 找到前一个节点
    	Node<E> l = last;
    	
    	if(l == null) {
    		throw new IllegalArgumentException("removeLast failed. List is empty!");
    	}
    	
    	E cur = l.e;
    	
    	// 找到当前的一个节点, 让下一个节点替换 当前节点
    	Node<E> next = l.prev;
    	
    	last = next;
    	
    	// 如果上一个为 null, 则为最后一个
    	if(next == null) {
    		frist = null;
    	}else {
    		next.next = null;
    	}
    	
    	// 将当前节点置 null ,辅助 gc
    	l.e = null;
    	l.next = null;
    	// 前节点的 prev 本来就是null
    	//f.prev = null;
    	size --;
    	return cur;
    }
    
    /**
     * 删除 index 位置的元素
     * @param index
     * @return
     */
    public E remove(int index) {
    	
    	if(index > 0 || index > size) {
    		throw new IllegalArgumentException("remove failed. index is error!");
    	}
    	
    	// 删除头
    	if(index == 0) {
    		return removeFrist();
    	}
    	// 删除尾巴
    	if(index == size-1) {
    		return removeLast();
    	}
    	E cur = null;
    	// 前半段
    	if(index < (size >> 1)) {
    		
    		// 正向找到 自己(index) 位置的 node
    		
    		Node<E> f = frist;
    		for (int i = 0; i < index; i++) {
    			// 这里指向了下一个
				f = f.next;
			}
    		cur = f.e;
    		
    		// 上一个节点
    		Node<E> prev = f.prev;
    		
    		//当前节点的下一个
    		Node<E> next = f.next;
    		
    		/**
    		 * 这一段还需要考虑一下
    		 */
    		if(prev == null) {
    			
    			frist = null;
    		}else {
    			prev.next = next;
    			f.prev = null;
    		}
    		
    		if(next == null) {
    			last = prev;
    		}else {
    			next.prev = prev;
    			f.next = null;
    		}
    		
    		f.e = null;
    		size --;
    		
    	// 后半段
    	}else {
    		
    		// 逆向找到 index 位置的Node
    		Node<E> l = last;
    		// 这里起始循环只能 size-1,避免多往后遍历一个 Node
    		for(int i = size -1; i > index; i--) {
    			l = l.prev;
    		}
    		
    		cur = l.e;
    		
    		// 上一个节点
    		Node<E> prev = l.prev;
    		
    		//当前节点的下一个
    		Node<E> next = l.next;
    		
    		if(prev == null) {
    			
    			frist = next;
    		}else {
    			prev.next = next;
    			l.prev = null;
    		}
    		
    		if(next == null) {
    			last = prev;
    		}else {
    			next.prev = prev;
    			l.next = null;
    		}
    		
    		l.e = null;
    		size --;
    	}
    	return cur;
    }
    
    /**
     * 删除 element 节点的元素
     * @param e
     */
    public void removeElement(E e) {
    	
    	if(e == null) {
    		return ;
    	}
    	
    	if(frist == null || last == null) {
    		throw new IllegalArgumentException("removeLast failed. List is empty!");
    	}
    	
    	Node<E> node = frist;
    	while( node!=null ) {
    		if(node.e.equals(e)) {
    			break;
    		}
    		node = frist.next;
    	}
    	
    	// 上一个节点
		Node<E> prev = node.prev;
		
		//当前节点的下一个
		Node<E> next = node.next;
		
		if(prev == null) {
			
			frist = next;
		}else {
			prev.next = next;
			node.prev = null;
		}
		
		if(next == null) {
			last = prev;
		}else {
			next.prev = prev;
			node.next = null;
		}
		
		node.e = null;
		size --;
    }
    
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        // 前节点
        Node<E> f = frist;
        for(Node<E> cur = f ; cur != null ; cur = cur.next)
            res.append(cur + "->");
        res.append("NULL");
        
        res.append("\n");
        // 后节点
        Node<E> l = last;
        for(Node<E> cur = l ; cur != null ; cur = cur.prev)
            res.append(cur + "->");
        res.append("NULL");
        res.append("\n end...");

        return res.toString();
    }
	
}
