package com.cwt.symbolinfinitemedian.websocket;

import com.cwt.symbolinfinitemedian.dto.StreamData;
import com.cwt.symbolinfinitemedian.repository.SymbolRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;

@ClientEndpoint
@Component
public class BinancePriceEndpoint {

    private static SymbolRepository repository;

    @Autowired
    public void setRepository(SymbolRepository repository) {
        BinancePriceEndpoint.repository = repository;
    }


    private static final Logger log = LoggerFactory.getLogger(BinancePriceEndpoint.class);

    private ObjectMapper mapper = new ObjectMapper();

    @OnMessage
    public void onMessage(String msg) {
        try {
            StreamData streamData = mapper.readValue(msg, StreamData.class);
            repository.save(streamData.getSymbolPrice());
            log.info("Symbol: " + streamData.getSymbolPrice().getSymbol() +  " Price: " + streamData.getSymbolPrice().getPrice());


        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason){
        log.info("Closed because: " + reason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable error){
        log.error("Error because: " + error.getMessage());
    }
}
