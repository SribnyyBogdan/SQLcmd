package integration;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Богдан on 14.02.2016.
 */
public class ConfigurableInputStream extends InputStream{
    private String line;
    @Override
    public int read() throws IOException {
        if (line.length() == 0) {
            return -1;
        }
        char symbol = line.charAt(0);
        line = line.substring(1);
        return (int)symbol;
    }

    public void addLine(String line){
        if (this.line == null){
            this.line = line;
        }else{
            this.line += "\n" + line;
        }
    }
}
