package io.jsight.spring;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;
import org.springframework.util.StreamUtils;
import org.springframework.http.server.ServletServerHttpRequest;

public class CachedHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] cachedBody;

    public CachedHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        InputStream requestInputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
    }

    public byte[] getCachedBody() {
        return cachedBody;
    }

    public Map<String, List<String>> getHeaders() {
        return new ServletServerHttpRequest(this).getHeaders();
    }

    @Override
    public ServletInputStream getInputStream() {
        return new CachedServletInputStream(this.cachedBody);
    }
    
    @Override
    public BufferedReader getReader() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }
}