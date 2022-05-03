package com.cwt.symbolinfinitemedian.repository;

import com.cwt.symbolinfinitemedian.util.MedianCalculator;
import com.cwt.symbolinfinitemedian.dto.SymbolPrice;
import com.cwt.symbolinfinitemedian.model.SymbolModel;
import com.cwt.symbolinfinitemedian.util.RBHashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SymbolRepository {
    @Autowired
    private RBHashMap rbHashMap;


    public void save(SymbolPrice symbolPrice) {
        if(rbHashMap.get(symbolPrice.getSymbol().toLowerCase()) != null) {
            RBHashMap.Entry entry = rbHashMap.get(symbolPrice.getSymbol().toLowerCase());
            entry.getValue().getPrices().add(symbolPrice.getPrice());
            entry.getValue().setRecentPrice(symbolPrice.getPrice());
            entry.getValue().setRecentMedian(MedianCalculator.calculateInfiniteMedian(entry.getValue().getPrices()));
            entry.getValue().setNumberOfData(entry.getValue().getPrices().size());
        } else {
            SymbolModel symbolModel = createSymbolModel(symbolPrice);
            rbHashMap.put(symbolModel.getName(), symbolModel);
        }
    }



    public SymbolModel findByName(String name) {
        RBHashMap.Entry entry = rbHashMap.get(name);
        if(entry != null) {
            return entry.getValue();
        }
        return null;
    }

    private SymbolModel createSymbolModel(SymbolPrice symbolPrice) {
        SymbolModel symbolModel = new SymbolModel();
        symbolModel.setName(symbolPrice.getSymbol().toLowerCase());
        symbolModel.setRecentPrice(symbolPrice.getPrice());
        symbolModel.setRecentMedian(symbolModel.getRecentPrice());
        List<Double> prices = new ArrayList<>();
        prices.add(symbolPrice.getPrice());
        symbolModel.setPrices(prices);
        symbolModel.setNumberOfData(1);
        return symbolModel;
    }
}
