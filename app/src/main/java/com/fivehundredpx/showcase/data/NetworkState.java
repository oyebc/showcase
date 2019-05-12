package com.fivehundredpx.showcase.data;

public class NetworkState {
    public enum Status{
        RUNNING,
        SUCCESS,
        FAILED
    }


    private final Status status;
    private final String msg;

    public static final NetworkState LOADED;
    public static final NetworkState LOADING;

    static {
        LOADED=new NetworkState(Status.SUCCESS,"Success");
        LOADING=new NetworkState(Status.RUNNING,"Running");
    }

    public NetworkState(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
