package com.soulasuna.channel;

import com.soulasuna.utils.ClearHandle;

import java.util.List;
import java.util.concurrent.Callable;


public class ChannelTask implements Callable<String>{
    private String inputKey;
    private String inputValue;
    private List<ClearHandle<String>> handleList;

    public String getInputKey() {
        return inputKey;
    }

    public void setInputKey(String inputKey) {
        this.inputKey = inputKey;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public List<ClearHandle<String>> getHandleList() {
        return handleList;
    }

    public void setHandleList(List<ClearHandle<String>> handleList) {
        this.handleList = handleList;
    }

    /**
     * 清洗数据执行方法
     * @return
     * @throws Exception
     */
    @Override
    public String call() throws Exception {
        handleList.forEach(stringClearHandle -> {
            inputValue = stringClearHandle.clearHandle(inputValue);
        });
        return inputKey+"@@"+inputValue;
    }
}
