import org.junit.BeforeClass;
import org.junit.Test;

import com.jp.morgan.messaging.consumer.IMessageConsumer;
import com.jp.morgan.messaging.consumer.SaleMessageConsumer;
import com.jp.morgan.messaging.engine.Broker;
import com.jp.morgan.messaging.model.Message;
import com.jp.morgan.messaging.producer.IMessageProducer;
import junit.framework.Assert;

public class MessagingApplicationTest {
	
	private static IMessageConsumer consumer;
	private static Broker broker;
	private static IMessageProducer producer;
   
	
	@BeforeClass
	public static void setup()
	{
		consumer = new SaleMessageConsumer();
		broker = Broker.getInstance();
		producer = broker.createProducer("SALES_QUEUE");
		broker.registerConsumer("SALES_QUEUE",consumer);
	}
	
	@Test
	public void testMessageType1()
	{
		 boolean result = producer.doProduce(new Message("papaya at 10p"));
		 Assert.assertTrue(result);
	}
	
	@Test
	public void testMessageType2()
	{
		 boolean result = producer.doProduce(new Message("20 sales of papayas at 20p each"));
		 Assert.assertTrue(result);
	}
	
	@Test
	public void testMessageType3()
	{
		 boolean result = producer.doProduce(new Message("Add 30p papayas"));
		 Assert.assertTrue(result);
	}

}
