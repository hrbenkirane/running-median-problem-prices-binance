package com.cwt.symbolinfinitemedian.rest;


import com.cwt.symbolinfinitemedian.dto.ExchangeInfo;
import com.cwt.symbolinfinitemedian.model.SymbolModel;
import com.cwt.symbolinfinitemedian.repository.SymbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BinanceRestController {
    @Autowired
    SymbolRepository symbolRepository;

    public static final String BINANCE_ALL_SYMBOLS_ENDPOINT = "https://api.binance.com/api/v3/exchangeInfo";

    @GetMapping(value = "/symbols")
    public ExchangeInfo getAllSymbols() {
        RestTemplate restTemplate = new RestTemplate();
        ExchangeInfo symbols = restTemplate.getForObject(BINANCE_ALL_SYMBOLS_ENDPOINT, ExchangeInfo.class);
        return symbols;
    }

    @GetMapping(value = "/{symbol}")
    public SymbolModel getSymbol(@PathVariable String symbol) {
        SymbolModel symbolModel = symbolRepository.findByName(symbol);
        if(symbolModel == null) {
            throw new IllegalArgumentException("Symbol is not found!");
        }
        return symbolModel;
    }

}
