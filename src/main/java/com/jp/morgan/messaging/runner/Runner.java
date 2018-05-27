package com.jp.morgan.messaging.runner;

import com.jp.morgan.messaging.consumer.IMessageConsumer;
import com.jp.morgan.messaging.consumer.SaleMessageConsumer;
import com.jp.morgan.messaging.engine.Broker;
import com.jp.morgan.messaging.model.Message;
import com.jp.morgan.messaging.producer.IMessageProducer;
import com.jp.morgan.messaging.producer.MessageProducer;

public class Runner {


    public static void main(String[] args) {
        Broker broker = Broker.getInstance();
        IMessageConsumer consumer = new SaleMessageConsumer();
        IMessageProducer producer = broker.createProducer("SALES_QUEUE");
        broker.registerConsumer("SALES_QUEUE",consumer);

        producer.doProduce(new Message("papaya at 10p"));
        producer.doProduce(new Message("20 sales of papayas at 20p each"));
        producer.doProduce(new Message("Add 30p papayas"));

        producer.doProduce(new Message("papaya at 10p"));
        producer.doProduce(new Message("20 sales of papayas at 20p each"));
        producer.doProduce(new Message("Add 30p papayas"));

        producer.doProduce(new Message("apple at 10p"));
        producer.doProduce(new Message("20 sales of apples at 20p each"));
        producer.doProduce(new Message("Add 30p apples"));

        producer.doProduce(new Message("banana at 10p"));
        producer.doProduce(new Message("20 sales of bananas at 20p each"));
        producer.doProduce(new Message("Add 30p bananas"));

        producer.doProduce(new Message("grape at 10p"));
        producer.doProduce(new Message("20 sales of grapes at 20p each"));
        producer.doProduce(new Message("Add 30p grapes"));

        producer.doProduce(new Message("papaya at 10p"));
        producer.doProduce(new Message("20 sales of papayas at 20p each"));
        producer.doProduce(new Message("Add 30p papayas"));

        producer.doProduce(new Message("papaya at 10p"));
        producer.doProduce(new Message("20 sales of papayas at 20p each"));
        producer.doProduce(new Message("Add 30p papayas"));

        producer.doProduce(new Message("papaya at 10p"));
        producer.doProduce(new Message("20 sales of papayas at 20p each"));
        producer.doProduce(new Message("Add 30p papayas"));

        producer.doProduce(new Message("papaya at 10p"));
        producer.doProduce(new Message("20 sales of papayas at 20p each"));
        producer.doProduce(new Message("Add 30p papayas"));

        producer.doProduce(new Message("papaya at 10p"));
        producer.doProduce(new Message("20 sales of papayas at 20p each"));
        producer.doProduce(new Message("Add 30p papayas"));

        producer.doProduce(new Message("papaya at 10p"));
        producer.doProduce(new Message("20 sales of papayas at 20p each"));
        producer.doProduce(new Message("Add 30p papayas"));

        producer.doProduce(new Message("papaya at 10p"));
        producer.doProduce(new Message("20 sales of papayas at 20p each"));
        producer.doProduce(new Message("Add 30p papayas"));

        producer.doProduce(new Message("papaya at 10p"));
        producer.doProduce(new Message("20 sales of papayas at 20p each"));
        producer.doProduce(new Message("Add 30p papayas"));

        producer.doProduce(new Message("papaya at 10p"));
        producer.doProduce(new Message("20 sales of papayas at 20p each"));
        producer.doProduce(new Message("Add 30p papayas"));

        producer.doProduce(new Message("papaya at 10p"));
        producer.doProduce(new Message("20 sales of papayas at 20p each"));
        producer.doProduce(new Message("Add 30p papayas"));

        producer.doProduce(new Message("papaya at 10p"));
        producer.doProduce(new Message("20 sales of papayas at 20p each"));
        producer.doProduce(new Message("Add 30p papayas"));

        producer.doProduce(new Message("papaya at 10p"));
        producer.doProduce(new Message("20 sales of papayas at 20p each"));
        producer.doProduce(new Message("Add 30p papayas"));

        producer.doProduce(new Message("papaya at 10p"));
        producer.doProduce(new Message("20 sales of papayas at 20p each"));
        producer.doProduce(new Message("Add 30p papayas"));
    }
}
