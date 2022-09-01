package com.jersey.test.base;



import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseException extends Exception{


    private static final long serialVersionUID = 1L;
    protected Response.Status status;
    protected String code;
    protected String field;
    protected String reason;
    protected String message;

    public List<BaseException> Collection = new ArrayList<>();

    public BaseException(Response.Status state, String code, String reason) {
        this(state, code, reason, null);
    }

    public BaseException(Response.Status state, String code, String reason, String field) {
        this(state, code, reason, field, null);
    }

    public BaseException(Response.Status state, String code, String reason, String field, String message) {
        super(message);
        this.status = state;
        this.code = code;
        this.reason = reason;
        this.field = field;
        this.message = message;
    }

    /**
     * Http Code
     */
    public Response.Status getStatus() {
        return this.status;
    }

    /**
     * 自定義錯誤碼
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 給末端的錯誤原因
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * 相關欄位名稱或key name
     */
    public String getField() {
        return this.field;
    }

    /**
     * 留給內部log的系統訊息
     */
    public String getMessage() {
        return this.message;
    }

    public JsonArray getJsonArray() {
        BaseException that = this;
        JsonArray array = new JsonArray();
        if (null == that.Collection || that.Collection.size() == 0) {
            array.add(that.getJsonObj());
        } else {
            for (BaseException e : that.Collection) {
                if (e != that) {
                    array.add(e.getJsonObj());
                }
            }
        }
        return array;
    }

    public JsonObject getJsonObj() {
        JsonObject json = new JsonObject();
        json.addProperty("code", this.code);
        json.addProperty("field", this.field);
        json.addProperty("message", this.message);
        json.addProperty("reason", this.reason);
        return json;
    }

    public String getJsonString() {
        JsonArray a = getJsonArray();
        String s1 = new Gson().toJson(a);

        return s1;
    }

    public void setField(String value) {
        this.field = value;
    }
    public void setReason(String value) {
        this.reason = value;
    }

    public final Response getResponse(){
        return Response.status(this.getStatus()).entity(this.getJsonString()).build();
    }
}

