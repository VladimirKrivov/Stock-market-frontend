package stock.market.frontend.app.stockmarketfrontend.service;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import stock.market.frontend.app.stockmarketfrontend.models.Stocks;

import java.io.IOException;

public class StocksAdapter extends TypeAdapter<Stocks> {

    // Адаптер необходимый для работы Gson с приватными полями Stocks
    @Override
    public void write(JsonWriter out, Stocks stocks) throws IOException {
        // Не требуется реализация, так как не нужно сериализовать объекты в JSON
    }

    @Override
    public Stocks read(JsonReader in) throws IOException {
        Stocks stocks = new Stocks();

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            if (in.peek() == JsonToken.NULL) {
                in.skipValue();
                continue;
            }

            switch (name) {
                case "secId":
                    stocks.setSecId(in.nextString());
                    break;
                case "shortname":
                    stocks.setShortname(in.nextString());
                    break;
                case "regNumber":
                    stocks.setRegNumber(in.nextString());
                    break;
                case "name":
                    stocks.setName(in.nextString());
                    break;
                case "isin":
                    stocks.setIsin(in.nextString());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return stocks;
    }
}