package com.jp.morgan.messaging.consumer;


import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import com.jp.morgan.messaging.model.Message;
import com.jp.morgan.messaging.util.Config;

public abstract class AbstractMessageConsumer implements IMessageConsumer {
    protected int DEFAULT_MAX_CAPACITY = Config.APP_CONFIG.get("max_capacity_message").getAsInt();
    protected long DEFAULT_PAUSE_INTERVAL_MS = Config.APP_CONFIG.get("max_pause_interval").getAsLong();
    protected int retryCount = Config.APP_CONFIG.get("broker_retry_count").getAsInt();
    protected BlockingQueue<Message> queue;
    private int messageCount = 0;
    
    public AbstractMessageConsumer()
    {
    	queue = new LinkedBlockingQueue<Message>();
    	startConsumerProcess();
    }
    
    private void startConsumerProcess()
    {
    	(new Thread(this)).start();
    }
    
    
    @Override
    public boolean shouldPause(final int count) {
        return count % DEFAULT_MAX_CAPACITY == 0;
    }

    @Override
    public long getPauseLimitinMS() {
        return DEFAULT_PAUSE_INTERVAL_MS;
    }
    
    @Override
    public BlockingQueue<Message> getMessageQueue()
    {
    	return queue;
    }
    
    @Override
    public boolean isLoggingEnabled() {
        return true;
    }

    
    @Override
	public void run() {
		try {
            while (true) {
                Message message = queue.take();
                messageCount++;
                deliver(message,messageCount,0);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            Thread.currentThread().interrupt();
        }
	}
    
    private void deliver(Message message, int messageCount, int count) throws InterruptedException, IllegalAccessException, InstantiationException {
        Objects.requireNonNull(message, "Message can not be null");
        if (this.doProcess(message)) {
            if (this.isLoggingEnabled()) {
            	this.logReport(messageCount);
            }
            if (this.shouldPause(messageCount)) {
                System.out.println("Consumer paused for interval " + this.getPauseLimitinMS() + " MS.");
                Thread.sleep(this.getPauseLimitinMS());
                System.out.println("Consumer starting again after " + this.getPauseLimitinMS() + " MS pause ");
            } else if (count > retryCount) {
                return;
            }
        } else {
            this.deliver(message,messageCount,count + 1);
        }
    }
}
