package com.jp.morgan.messaging.producer;

import com.jp.morgan.messaging.engine.IBroker;

public abstract class AbstractMessageProducer<T> implements IMessageProducer<T> {
    protected IBroker exchange;
    protected String channelName;
    public AbstractMessageProducer(IBroker exchange,String channelName)
    {
    	this.exchange = exchange;
    	this.channelName = channelName;
    }
}