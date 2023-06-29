package etu1932.framework;

public class ModelView {
    String Url;
    HashMap<String, Object> data = new HashMap<>();

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    

    public ModelView(String url){
        setUrl(url);
    }
}
