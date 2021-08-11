package com.cos.retrofitex03.util;

public interface InitSettings {

    void init();
    void initLr();
    void initSetting();
    default void initAdapter(){};
    default void initData(){};
}
