package com.jp.morgan.messaging.engine;

import com.jp.morgan.messaging.consumer.IMessageConsumer;
import com.jp.morgan.messaging.model.Message;
import com.jp.morgan.messaging.producer.IMessageProducer;

public interface IBroker {
	public IMessageProducer createProducer(String channelName);
    void registerConsumer(final String channelName,final IMessageConsumer consumer);
    public boolean exchangeMessage(String targetQueue,Message message);
}
