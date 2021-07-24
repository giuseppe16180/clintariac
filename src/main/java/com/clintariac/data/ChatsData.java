package com.clintariac.data;

import java.util.HashMap;
import java.util.List;

public class ChatsData {

    public ChatsData(HashMap<String, List<MessageData>> chats) {
        this.chats = chats;
    }

    public final HashMap<String, List<MessageData>> chats;
}
