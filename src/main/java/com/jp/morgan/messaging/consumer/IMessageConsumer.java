package com.jp.morgan.messaging.consumer;

import java.util.concurrent.BlockingQueue;

import com.jp.morgan.messaging.model.Message;

public interface IMessageConsumer extends Runnable {

    boolean doProcess(Message message);

    boolean isLoggingEnabled();

    void logReport(final int messageCount);

    boolean shouldPause(final int count);

    long getPauseLimitinMS();
    
    BlockingQueue getMessageQueue();
}