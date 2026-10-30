package com.nexpoint.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TcgApiResponse {

    private Data data;

    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        private String id;
        private String name;
        private TcgPrices tcgplayer;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public TcgPrices getTcgplayer() { return tcgplayer; }
        public void setTcgplayer(TcgPrices tcgplayer) { this.tcgplayer = tcgplayer; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TcgPrices {
        private Prices prices;

        public Prices getPrices() { return prices; }
        public void setPrices(Prices prices) { this.prices = prices; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Prices {
        private PriceDetail holofoil;
        private PriceDetail normal;

        public PriceDetail getHolofoil() { return holofoil; }
        public void setHolofoil(PriceDetail holofoil) { this.holofoil = holofoil; }

        public PriceDetail getNormal() { return normal; }
        public void setNormal(PriceDetail normal) { this.normal = normal; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PriceDetail {
        private Double market;
        private Double low;
        private Double mid;
        private Double high;

        public Double getMarket() { return market; }
        public void setMarket(Double market) { this.market = market; }

        public Double getLow() { return low; }
        public void setLow(Double low) { this.low = low; }

        public Double getMid() { return mid; }
        public void setMid(Double mid) { this.mid = mid; }

        public Double getHigh() { return high; }
        public void setHigh(Double high) { this.high = high; }
    }
}
