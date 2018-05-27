package com.jp.morgan.messaging.producer;

import com.jp.morgan.messaging.engine.IBroker;
import com.jp.morgan.messaging.model.Message;

public class MessageProducer extends AbstractMessageProducer<Message> {

    public MessageProducer(IBroker exchange,String channelName) {
		super(exchange,channelName);
	}

	@Override
    public boolean doProduce(final Message data) {
		return exchange.exchangeMessage(this.channelName, data);
    }
}
