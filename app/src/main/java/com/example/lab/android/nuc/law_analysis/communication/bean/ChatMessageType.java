package com.example.lab.android.nuc.law_analysis.communication.bean;

/**
 * Created by wanghao15536870732 on 2018/6/05/020.
 */

public enum ChatMessageType {
    UnsupportedMessageType(0),
    TextMessageType(-1),
    ImageMessageType(-2),
    AudioMessageType(-3);

    int type;

    private ChatMessageType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public static ChatMessageType getAVIMReservedMessageType(int type) {
        switch (type) {
            case -3:
                return AudioMessageType;
            case -2:
                return ImageMessageType;
            case -1:
                return TextMessageType;
            default:
                return UnsupportedMessageType;
        }
    }
}
