package com.jp.morgan.messaging.producer;

public interface IMessageProducer<T> {
    boolean doProduce(final T data);

}
