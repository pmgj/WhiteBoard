package org.sample.whiteboardapp;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class FigureDecoder implements Decoder.Text<Figure> {

    @Override
    public Figure decode(String string) throws DecodeException {
        System.out.println("decoding: " + string);
        return JsonbBuilder.create().fromJson(string, Figure.class);
    }

    @Override
    public boolean willDecode(String string) {
        try {
            Json.createReader(new StringReader(string)).readObject();
            return true;
        } catch (JsonException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public void init(EndpointConfig config) {
        System.out.println("init");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }    
}
