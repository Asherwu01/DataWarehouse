package com.asher.flume.interceptor;

import com.asher.flume.utils.MyJsonUtil;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ETLInterceptor implements Interceptor {
    @Override
    public void initialize() {

    }

    // 过滤非json格式数据
    @Override
    public Event intercept(Event event) {
        byte[] body = event.getBody();
        String log = new String(body, StandardCharsets.UTF_8);

        //JSON.isValid(log)方法不准确，自己定义一个
        if (MyJsonUtil.isValidJson(log)) {
            return event;
        }
        // String log = body.toString();
        return null;
    }

    @Override
    public List<Event> intercept(List<Event> list) {

        list.removeIf(event -> intercept(event) == null);

        return list;
    }

    public static class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new ETLInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }

    @Override
    public void close() {

    }
}
