package com.soulasuna.test;


import com.soulasuna.channel.ChannelTask;
import com.soulasuna.utils.ClearHandle;
import com.soulasuna.utils.DefaultValueHandle;
import com.soulasuna.utils.NonNullHandle;

import java.util.*;
import java.util.concurrent.*;

public class DomeTest {

    public static void main(String[] args) {

        Map<String,String> map = new HashMap<>(16);

        map.put("id","1");
        map.put("name",null);
        map.put("password","");

        System.out.println("清洗数据开始==入参=="+map);

        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);

        map.forEach((key,value) -> {
            ChannelTask channelTask = new ChannelTask();
            channelTask.setInputKey(key);
            channelTask.setInputValue(value);

            List<ClearHandle<String>> channelTaskList = new ArrayList<>();
            channelTaskList.add(new NonNullHandle());
            channelTaskList.add(new DefaultValueHandle());
            channelTask.setHandleList(channelTaskList);
            // 递交任务
            completionService.submit(channelTask);


        });
        int size = map.keySet().size();

        List<String> resultList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            String result;
            try {
                result = completionService.take().get();
                resultList.add(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        Map<String, String> resultMap = parseResult(resultList);

        System.out.println("清洗数据完成==出参=="+resultMap);





    }

    private static Map<String,String> parseResult(List<String> resultList){
        Map<String,String> map = new HashMap<>(resultList.size());
        resultList.forEach(result -> {
            String[] split = result.split("@@");
            map.put(split[0],split[1]);
        });

        return map;
    }



}
