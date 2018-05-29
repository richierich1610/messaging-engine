package com.jp.morgan.messaging.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import com.jp.morgan.messaging.consumer.IMessageConsumer;
import com.jp.morgan.messaging.model.Message;
import com.jp.morgan.messaging.producer.IMessageProducer;
import com.jp.morgan.messaging.producer.MessageProducer;
import com.jp.morgan.messaging.util.Config;


public class Broker implements IBroker  {
    private static Broker INSTANCE;
    protected int RETRY_COUNT = Config.APP_CONFIG.get("broker_retry_count").getAsInt();
    protected long RETRY_INTERVAL = Config.APP_CONFIG.get("broker_retry_interval_in_ms").getAsLong();
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
    				int counter = 0;
    				while(!queue.offer(message) && counter<=RETRY_COUNT)
    				{
    					counter++;
    					try {
							Thread.sleep(RETRY_INTERVAL);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
    				}
    		});
    		return true;
    	}
    	return false;
	}
}