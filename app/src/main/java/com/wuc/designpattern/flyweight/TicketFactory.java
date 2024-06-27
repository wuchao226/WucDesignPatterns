package com.wuc.designpattern.flyweight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: wuc
 * @date: 2024/6/26
 * @desc:
 */
public class TicketFactory {
    // 消息池，对象缓存
    static Map<String, Ticket> sTicketMap = new ConcurrentHashMap<>();

    public static Ticket getTicket(String from, String to) {
        // 内部状态不可变
        String key = from + "-" + to;
        if (sTicketMap.containsKey(key)) {
            return sTicketMap.get(key);
        } else {
            Ticket ticket = new TrainTicket(from, to);
            sTicketMap.put(key, ticket);
            return ticket;
        }
    }
}
