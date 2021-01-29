package com.example.mvc.screens.common.controllers;

public interface BackpressDispatcher {
    void registerListener(BackpressListener listener);
    void unregisterListener(BackpressListener listener);
}
