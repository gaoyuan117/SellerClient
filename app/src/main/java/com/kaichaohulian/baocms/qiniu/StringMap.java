package com.kaichaohulian.baocms.qiniu;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by huashao on 2016/10/29.
 */


public final class StringMap {
    private Map<String, Object> map;

    public StringMap() {
        this(new HashMap());
    }

    public StringMap(Map<String, Object> map) {
        this.map = map;
    }

    public StringMap put(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public StringMap putNotEmpty(String key, String value) {
        if(!StringUtils.isNullOrEmpty(value)) {
            this.map.put(key, value);
        }

        return this;
    }

    public StringMap putNotNull(String key, Object value) {
        if(value != null) {
            this.map.put(key, value);
        }

        return this;
    }

    public StringMap putWhen(String key, Object val, boolean when) {
        if(when) {
            this.map.put(key, val);
        }

        return this;
    }

    public StringMap putAll(Map<String, Object> map) {
        this.map.putAll(map);
        return this;
    }

    public StringMap putAll(StringMap map) {
        this.map.putAll(map.map);
        return this;
    }

    public void forEach(StringMap.Consumer imp) {
        Iterator var2 = this.map.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry i = (Map.Entry)var2.next();
            imp.accept((String)i.getKey(), i.getValue());
        }

    }

    public int size() {
        return this.map.size();
    }

    public Map<String, Object> map() {
        return this.map;
    }

    public Object get(String key) {
        return this.map.get(key);
    }

    public String formString() {
        final StringBuilder b = new StringBuilder();
        this.forEach(new StringMap.Consumer() {
            private boolean notStart = false;

            public void accept(String key, Object value) {
                if(this.notStart) {
                    b.append("&");
                }

                try {
                    b.append(URLEncoder.encode(key, "UTF-8")).append('=').append(URLEncoder.encode(value.toString(), "UTF-8"));
                } catch (UnsupportedEncodingException var4) {
                    throw new AssertionError(var4);
                }

                this.notStart = true;
            }
        });
        return b.toString();
    }

    public interface Consumer {
        void accept(String var1, Object var2);
    }
}
