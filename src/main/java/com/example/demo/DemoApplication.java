package com.example.demo;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {
  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  private Map<Long, String> numberWords = Map.ofEntries(
      Map.entry(0L, "zero"),
      Map.entry(1L, "one"),
      Map.entry(2L, "two"),
      Map.entry(3L, "three"),
      Map.entry(4L, "four"),
      Map.entry(5L, "five"),
      Map.entry(6L, "six"),
      Map.entry(7L, "seven"),
      Map.entry(8L, "eight"),
      Map.entry(9L, "nine"),
      Map.entry(10L, "ten"),
      Map.entry(11L, "eleven"),
      Map.entry(12L, "twelve"),
      Map.entry(13L, "thirteen"),
      Map.entry(14L, "fourteen"),
      Map.entry(15L, "fifteen"),
      Map.entry(16L, "sixteen"),
      Map.entry(17L, "seventeen"),
      Map.entry(18L, "eighteen"),
      Map.entry(19L, "nineteen"),
      Map.entry(20L, "twenty"),
      Map.entry(30L, "thirty"),
      Map.entry(40L, "forty"),
      Map.entry(50L, "fifty"),
      Map.entry(60L, "sixty"),
      Map.entry(70L, "seventy"),
      Map.entry(80L, "eighty"),
      Map.entry(90L, "ninety"),
      Map.entry(100L, "hundred"),
      Map.entry(1000L, "thousand"),
      Map.entry(1000000L, "million"),
      Map.entry(1000000000L, "billion"),
      Map.entry(1000000000000L, "trillion"),
      Map.entry(1000000000000000L, "quadrillion"),
      Map.entry(1000000000000000000L, "quintillion"));

  @GetMapping("/numberToWords/{id}")
  public String getNumberToWords(@PathVariable String id) {
    try {
      Long number = Long.parseLong(id);
      return capitalize(numberToWords(number));
    } catch (NumberFormatException e) {
      return "Invalid number";
    }
  }

  private String capitalize(String input) {
    if (input == null || input.isEmpty()) {
      return input;
    }
    return input.substring(0, 1).toUpperCase() + input.substring(1);
  }

  public String numberToWords(Long id) {
    if (id == 0) {
      return numberWords.get(0L);
    }

    if (id < 0) {
      return "Negative " + numberToWords(-id);
    }

    if (id <= 20) {
      return numberWords.get(id);
    }

    if (id < 100) {
      Long tens = (id / 10) * 10;
      Long rest = id % 10;
      if (rest == 0) {
        return numberWords.get(tens);
      } else {
        return numberWords.get(tens) + " " + numberWords.get(rest);
      }
    }

    if (id < 1000) {
      Long hundreds = id / 100;
      Long rest = id % 100;
      if (rest == 0) {
        return numberWords.get(hundreds) + " " + numberWords.get(100L);
      } else {
        return numberWords.get(hundreds) + " " + numberWords.get(100L) + " and " + numberToWords(rest);
      }
    }

    if (id < 1000000) {
      return largeNumberToWords(id, 1000L);
    }

    if(id < 1000000000) {
      return largeNumberToWords(id, 1000000L);
    }

    if(id < 1000000000000L) {
      return largeNumberToWords(id, 1000000000L);
    }

    if(id < 1000000000000000L) {
      return largeNumberToWords(id, 1000000000000L);
    }

    if(id < 1000000000000000000L) {
      return largeNumberToWords(id, 1000000000000000L);
    }

    if(id <=  Long.MAX_VALUE) {
      return largeNumberToWords(id, 1000000000000000000L);
    }

    return "Unknown";

  }

  private String largeNumberToWords(Long id, Long base) {
    Long main = id / base;
    Long rest = id % base;
    if (rest == 0) {
      return numberToWords(main) + " " + numberWords.get(base);
    } else {
      return numberToWords(main) + " " + numberWords.get(base) + (rest < 100 ? " and " : " ") + numberToWords(rest);
    }
  }
}
