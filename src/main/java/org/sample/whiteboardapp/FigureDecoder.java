package org.sample.whiteboardapp;

import jakarta.json.JsonException;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

public class FigureDecoder implements Decoder.Text<Figure> {

    @Override
    public Figure decode(String string) throws DecodeException {
        return JsonbBuilder.create().fromJson(string, Figure.class);
    }

    @Override
    public boolean willDecode(String string) {
        try {
            JsonbBuilder.create().fromJson(string, Figure.class);
            return true;
        } catch (JsonException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
