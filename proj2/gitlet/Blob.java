package gitlet;

import java.io.File;
import java.io.Serializable;
import static gitlet.AAA.*;

public class Blob implements Serializable {
    private String content;
    private String filename;

    //constructor of the blob
    public Blob(String content, String filename){
        this.content = content;
        this.filename = filename;
    }

    public String content(){
        return content;
    }
    public String getFilename(){
        return filename;
    }

    public static Blob getBlobFromSHA1(String SHA1Name){
        File blobFile = Utils.join(Blobs, SHA1Name);
        return Utils.readObject(blobFile, Blob.class);
    }

    public String getSHA1OfBlob(){
        return Utils.sha1(this.content + this.filename);
    }

}
