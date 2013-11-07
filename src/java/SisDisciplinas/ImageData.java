package SisDisciplinas;

import java.io.Serializable;

/**
 *
 * @author uefscompras
 */

public class ImageData implements Serializable{
    
    private int length;
    private String name;
    private byte[] data;
    private String mime;
    

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}