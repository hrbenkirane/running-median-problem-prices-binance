package com.cwt.symbolinfinitemedian.util;

import com.cwt.symbolinfinitemedian.dto.ExchangeInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

    public static final int partitionSize = 250;

    public static List<String> generateCombineStreams(ExchangeInfo exchangeInfo) {
        List<String> result = new ArrayList<>();
        if(exchangeInfo.getSymbols() != null && !exchangeInfo.getSymbols().isEmpty()) {
            result = exchangeInfo.getSymbols().stream()
                     .map( s -> s.getSymbol().toLowerCase() + "@trade" )
                     .collect(Collectors.toList());
        }
        return result;

    }

    public static Collection<List<String>> createPartitionLists(List<String> combineStreams) {
        return IntStream.range(0, combineStreams.size())
                .boxed()
                .collect(Collectors.groupingBy(partition -> (partition / partitionSize),
                        Collectors.mapping(elementIndex -> combineStreams.get(elementIndex), Collectors.toList())))
                .values();
    }


}

