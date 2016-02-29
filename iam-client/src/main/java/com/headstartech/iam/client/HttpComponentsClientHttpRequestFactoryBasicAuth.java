package com.headstartech.iam.client;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;

public class HttpComponentsClientHttpRequestFactoryBasicAuth extends HttpComponentsClientHttpRequestFactory {

    final HttpHost host;
    final CredentialsProvider credsProvider;
    final AuthCache authCache;

    public HttpComponentsClientHttpRequestFactoryBasicAuth(HttpHost host, String userName, String password) {
        super();
        this.host = host;
        credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(host.getHostName(), host.getPort()), new UsernamePasswordCredentials(userName, password));
        authCache = new BasicAuthCache();
        authCache.put(host, new BasicScheme());

    }

    protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
        return createHttpContext();
    }
    private HttpContext createHttpContext() {
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credsProvider);
        context.setAuthCache(authCache);
        return context;
    }
}
