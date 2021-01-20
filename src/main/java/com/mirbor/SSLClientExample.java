package com.mirbor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Paths;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import sun.security.provider.X509Factory;

class WebSocketChatClient extends WebSocketClient {

  public WebSocketChatClient(URI serverUri) {
    super(serverUri);
  }

  @Override
  public void onOpen(ServerHandshake handshakedata) {
    System.out.println("Connected");

  }

  @Override
  public void onMessage(String message) {
    System.out.println("got: " + message);

  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
    System.out.println("Disconnected");

  }

  @Override
  public void onError(Exception ex) {
    ex.printStackTrace();

  }

}

public class SSLClientExample {

  public static void main(String[] args) throws Exception {
    WebSocketChatClient chatclient = new WebSocketChatClient(new URI("wss://localhost:8887"));


    // load up the key store
    String STORETYPE = "JKS";
    String KEYSTORE = Paths.get("src", "test", "java", "org", "java_websocket", "keystore.jks")
        .toString();
    String STOREPASSWORD = "storepassword";
    String KEYPASSWORD = "keypassword";

    KeyStore ks = KeyStore.getInstance(STORETYPE);
    File kf = new File(KEYSTORE);
    ks.load(new FileInputStream(kf), STOREPASSWORD.toCharArray());

    KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
    kmf.init(ks, KEYPASSWORD.toCharArray());
    TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
    tmf.init(ks);

    SSLContext sslContext = null;
    sslContext = SSLContext.getInstance("TLS");
    sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

    SSLSocketFactory factory = sslContext
        .getSocketFactory();
    chatclient.setSocketFactory(factory);

    chatclient.connectBlocking();

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      String line = reader.readLine();
      if (line.equals("close")) {
        chatclient.closeBlocking();
      } else if (line.equals("open")) {
        chatclient.reconnect();
      } else {
        chatclient.send(line);
      }
    }

  }
}