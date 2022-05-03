package com.cwt.symbolinfinitemedian;

import com.cwt.symbolinfinitemedian.dto.ExchangeInfo;
import com.cwt.symbolinfinitemedian.rest.BinanceRestController;
import com.cwt.symbolinfinitemedian.util.Utils;
import com.cwt.symbolinfinitemedian.websocket.BinancePriceEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class SymbolInfiniteMedianApplication implements CommandLineRunner {

	@Autowired
	BinanceRestController binanceRestController;


	public static void main(String[] args) {
		SpringApplication.run(SymbolInfiniteMedianApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		ExchangeInfo allSymbols = binanceRestController.getAllSymbols();
		List<String> combineStreams = Utils.generateCombineStreams(allSymbols);
		Collection<List<String>> partitionedList = Utils.createPartitionLists(combineStreams);

		for(List<String> partition : partitionedList) {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			String uri = "wss://stream.binance.com/stream?streams=" + String.join("/", partition);
			container.connectToServer(BinancePriceEndpoint.class,  new URI(uri));
		}



	}



}

