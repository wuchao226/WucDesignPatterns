package com.wuc.designpattern.flyweight;

/**
 * @author: wuc
 * @date: 2024/6/26
 * @desc:
 */
public class TrainTicket implements Ticket {
    public String from;
    public String to;
    // 外部状态：床铺
    public String bunk;
    // 外部状态：价格
    public int price;

    public TrainTicket(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void showTicketInfo(String bunk) {
        // 省略代码
    }
}
