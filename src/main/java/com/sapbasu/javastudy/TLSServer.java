package com.sapbasu.javastudy;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

/*
 * keytool -genkeypair -alias server -keyalg EC \
 * -sigalg SHA384withECDSA -keysize 256 -keystore servercert.p12 \
 * -storetype pkcs12 -v -storepass abc123 -validity 10000 -ext san=ip:127.0.0.1
 */

public class TLSServer {
  public void serve(int port, int threadCount, String tlsVersion,
      String trustStoreName, char[] trustStorePassword, String keyStoreName,
      char[] keyStorePassword) throws Exception {
    
    Objects.requireNonNull(tlsVersion, "TLS version is mandatory");
    
    if (port <= 0) {
      throw new IllegalArgumentException(
          "Port number cannot be less than or equal to 0");
    }
    
    if (threadCount <= 0) {
      throw new IllegalArgumentException(
          "Thread count cannot be less than or equal to 0");
    }
    
    KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
    InputStream tstore = TLSServer.class
        .getResourceAsStream("/" + trustStoreName);
    trustStore.load(tstore, trustStorePassword);
    tstore.close();
    TrustManagerFactory tmf = TrustManagerFactory
        .getInstance(TrustManagerFactory.getDefaultAlgorithm());
    tmf.init(trustStore);
    
    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
    InputStream kstore = TLSServer.class
        .getResourceAsStream("/" + keyStoreName);
    keyStore.load(kstore, keyStorePassword);
    KeyManagerFactory kmf = KeyManagerFactory
        .getInstance(KeyManagerFactory.getDefaultAlgorithm());
    kmf.init(keyStore, keyStorePassword);
    SSLContext ctx = SSLContext.getInstance("TLS");
    ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(),
        SecureRandom.getInstanceStrong());
    
    SSLServerSocketFactory factory = ctx.getServerSocketFactory();
    try (ServerSocket listener = factory.createServerSocket(port)) {
      SSLServerSocket sslListener = (SSLServerSocket) listener;
      
      sslListener.setNeedClientAuth(true);
      sslListener.setEnabledProtocols(new String[] {tlsVersion});
      
      HostnameVerifier allHostsValid = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      };
      
      HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
      
      ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
      while (true) {
        threadPool.submit(() -> {
          try (Socket socket = sslListener.accept()) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello World!");
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
        
      }
    }
  }
}
