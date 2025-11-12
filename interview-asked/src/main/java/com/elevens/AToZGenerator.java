package com.elevens;

import java.util.ArrayList;
import java.util.List;

public class AToZGenerator {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> stringList = new ArrayList<>();
        for (char c='A'; c<='Z' ; c++) {
            stringBuilder.append(c);
            stringList.add(String.valueOf(c));
        }
        System.out.println(stringBuilder);
        System.out.println(stringList);

        StringBuffer stringBuffer = new StringBuffer();
        for (char c= 65; c<= 90 ; c++) {
            stringBuffer.append(c);
        }
        System.out.println(stringBuffer);
    }

}
