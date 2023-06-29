package etu1932.framework;

import java.util.HashMap;

public class ModelView {
    String Url;
    HashMap<String, Object> data = new HashMap<>();

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public void addItem(String key, Object value){
        data.put(key, value);
    }

    public ModelView(String url){
        setUrl(url);
    }
}
