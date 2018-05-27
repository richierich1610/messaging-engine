package com.jp.morgan.messaging.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import com.jp.morgan.messaging.consumer.IMessageConsumer;
import com.jp.morgan.messaging.model.Message;
import com.jp.morgan.messaging.producer.IMessageProducer;
import com.jp.morgan.messaging.producer.MessageProducer;


public class Broker implements IBroker  {
    private static Broker INSTANCE;
    private Map<String,List<BlockingQueue<Message>>> registeredQueues = new HashMap<>();

    private Broker() {
    }

    public static Broker getInstance() {
        if (INSTANCE == null) {
            synchronized (Broker.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Broker();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public IMessageProducer createProducer(String channelName) {
       return new MessageProducer(this,channelName);
    }

    @Override
    public void registerConsumer(String channelName,IMessageConsumer consumer) {
    	List<BlockingQueue<Message>> availableQueues = registeredQueues.get(channelName);
    	if(availableQueues == null)
    		availableQueues = new ArrayList<>();
    	availableQueues.add(consumer.getMessageQueue());
    	registeredQueues.put(channelName, availableQueues);
    }
    
	@Override
	public boolean exchangeMessage(String targetQueue,Message message) {
		List<BlockingQueue<Message>> availableQueues = registeredQueues.get(targetQueue);
    	if(availableQueues != null)
    	{
    		availableQueues.stream().forEach(queue->{
    			try {
					queue.put(message);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		});
    		return true;
    	}
    	return false;
	}
}