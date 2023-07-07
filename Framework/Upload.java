package etu1932.framework;

public class Upload {
    String filename;
    String path;
    byte[] data;
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
    public String getFilename() {
        return filename;
    }
    public String getPath() {
        return path;
    }
    public byte[] getData() {
        return data;
    }
}
