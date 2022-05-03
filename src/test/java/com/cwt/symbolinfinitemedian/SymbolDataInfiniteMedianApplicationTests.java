package com.cwt.symbolinfinitemedian;

import com.cwt.symbolinfinitemedian.rest.BinanceRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class SymbolDataInfiniteMedianApplicationTests {

	@Autowired
	private BinanceRestController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
