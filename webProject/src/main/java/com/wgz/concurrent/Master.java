package com.wgz.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {

    /**
     * 需要执行的任务队列。
     */
    private ConcurrentLinkedQueue<Task> linkedQueue = new ConcurrentLinkedQueue<>();

    /**
     * 存放执行结果集合
     */
    private ConcurrentHashMap<String, String> resultMap = new ConcurrentHashMap<>();

    /**
     * 当前开启的线程集合。
     */
    private HashMap<String, Thread> map = new HashMap<>();


    public Master(int count) {
        Worker worker = new Worker();
        worker.setQueue(linkedQueue);
        worker.setResultMap(resultMap);
        for (int i = 0; i < count; i++) {
            map.put("线程" + (i + 1), new Thread(worker));
        }
    }

    /**
     * 添加任务
     *
     * @param task
     */
    public void add(Task task) {
        linkedQueue.add(task);
    }

    /**
     * 执行任务
     */
    public void execute() {
        for (Map.Entry<String, Thread> entry : map.entrySet()) {
            entry.getValue().start();
        }
    }

    /**
     * 判断所有线程是否都执行完成。
     *
     * @return
     */
    public boolean isComplete() {
        for (Map.Entry<String, Thread> entry : map.entrySet()) {
            if (entry.getValue().getState() != Thread.State.TERMINATED) {
                return false;
            }
        }
        return true;
    }

    public ConcurrentHashMap<String, String> getResultMap() {
        return resultMap;
    }
}