package org.sample.whiteboardapp;

import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public class FigureEncoder implements Encoder.Text<Figure> {

    @Override
    public String encode(Figure figure) throws EncodeException {
        return JsonbBuilder.create().toJson(figure);
    }
}
