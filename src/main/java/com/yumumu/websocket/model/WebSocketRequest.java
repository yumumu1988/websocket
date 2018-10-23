package com.yumumu.websocket.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class WebSocketRequest implements Serializable {
    private static final long serialVersionUID = 5252056565396900458L;

    private JSONObject jsonObject;

    private JSONArray jsonArray;

    public WebSocketRequest() {
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
}
