package com.cwt.symbolinfinitemedian.rest;


import com.cwt.symbolinfinitemedian.dto.ExchangeInfo;
import com.cwt.symbolinfinitemedian.dto.SymbolData;
import com.cwt.symbolinfinitemedian.model.SymbolModel;
import com.cwt.symbolinfinitemedian.repository.SymbolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {BinanceRestController.class})
class BinanceRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SymbolRepository symbolRepositoryMock;

    @Mock
    private BinanceRestController binanceRestControllerMock;

    SymbolModel symbolModel;

    ExchangeInfo exchangeInfo;

    @BeforeEach
    void setUp() {

        // symbolModel
        List<Double> prices = List.of(3.0, 4.0, 2.0, 1.0, 5.0, 8.0, 2.5);
        symbolModel = new SymbolModel();
        symbolModel.setNumberOfData(7);
        symbolModel.setPrices(prices);
        symbolModel.setName("testSymbol");
        symbolModel.setRecentPrice(2.5);
        symbolModel.setRecentMedian(3.0);

        // ExchangeInfo
        exchangeInfo = new ExchangeInfo();
        exchangeInfo.setSymbols(List.of(new SymbolData("test1"), new SymbolData("test2")));

    }
    @Test
    void testGetSymbol() throws Exception {
        // fake the getSymbol method call using mocked symbolModelRepositroy object
        given(symbolRepositoryMock.findByName(anyString())).willReturn(symbolModel);

        mvc.perform(MockMvcRequestBuilders.get("/symbolTest"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString(symbolModel.getName())));
    }
    @Test
    void testGetSymbols() {
        when(binanceRestControllerMock.getAllSymbols()).thenReturn(exchangeInfo);
        ExchangeInfo exchangeInfoRest = binanceRestControllerMock.getAllSymbols();

        assertThat(exchangeInfoRest.getSymbols())
                .hasSize(2)
                .allSatisfy( symbol -> {
                    assertThat( symbol.getSymbol()).contains("test");
                });
    }

}