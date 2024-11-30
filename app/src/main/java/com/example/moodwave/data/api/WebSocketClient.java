package com.example.moodwave.data.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient {
    private OkHttpClient client;
    private WebSocket webSocket;
    private WebSocketListener webSocketListener;


    public interface SocketEventListener {
        void onMessageReceived(String message);
        void onError(String error);
    }

    private SocketEventListener listener;

    public WebSocketClient(SocketEventListener listener) {
        this.listener = listener;
        client = new OkHttpClient();
    }

    public void connect(String url, String token) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        webSocketListener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                System.out.println("WebSocket connected");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                if (listener != null) {
                    listener.onMessageReceived(text);
                }
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                if (listener != null) {
                    listener.onError(t.getMessage());
                }
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                webSocket.close(1000, null);
                System.out.println("WebSocket is closing: " + reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                System.out.println("WebSocket closed: " + reason);
            }
        };

        webSocket = client.newWebSocket(request, webSocketListener);
    }

    public void sendMessage(String message) {
        if (webSocket != null) {
            webSocket.send(message);
        }
    }

    public void close() {
        if (webSocket != null) {
            webSocket.close(1000, "Client closed connection");
        }
    }

}
