package io.jsight.spring;

import javax.servlet.ServletInputStream;
import javax.servlet.ReadListener;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServletServerHttpRequest;

public class CachedServletInputStream extends ServletInputStream {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private InputStream cachedInputStream;

    public CachedServletInputStream(byte[] cachedBody) {
        this.cachedInputStream = new ByteArrayInputStream(cachedBody);
    }

    @Override
    public boolean isFinished() {
        try {
            return cachedInputStream.available() == 0;
        } catch (IOException exp) {
            logger.error(exp.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean isReady() {
        return true;
    }
    
    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int read() throws IOException {
        return cachedInputStream.read();
    }
}