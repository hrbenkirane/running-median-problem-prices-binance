package com.cwt.symbolinfinitemedian.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SymbolModel {
    private String name;
    private int numberOfData;
    private double recentMedian;
    private double recentPrice;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Double> prices;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolModel that = (SymbolModel) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
