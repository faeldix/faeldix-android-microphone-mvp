package rfreitas.com.br.record.record;

import android.util.Log;

import java.io.File;

/**
 * Created by rafael-iteris on 1/6/17.
 */

public class Record {

    private String path;
    private String filename;

    private int max;

    public Record(String path, String filename, int max) {
        this.path = path;
        this.filename = filename;
        this.max = max;

        if(path == null || filename == null || path.isEmpty() || filename.isEmpty())
            throw new IllegalArgumentException("O path e filename sao obrigatorios.");
    }

    public int getMax() {
        return max;
    }

    public String getPath() {
        return path;
    }

    public String getFilename() {
        return filename;
    }

    public String getFullPath(){
        if(path.charAt(path.length() - 1) != File.separatorChar)
            path = path.concat(File.separator);

        if(filename.contains(File.separator))
            filename = filename.replace(File.separator, "");

        return path.concat(filename);
    }


}
