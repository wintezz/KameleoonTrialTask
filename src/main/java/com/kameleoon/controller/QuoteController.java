package com.kameleoon.controller;

import com.kameleoon.dto.quote.QuoteFullDTO;
import com.kameleoon.dto.quote.QuoteInDTO;
import com.kameleoon.dto.quote.QuoteOutDTO;
import com.kameleoon.entity.vote.SortType;
import lombok.RequiredArgsConstructor;
import com.kameleoon.service.QuoteService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.kameleoon.exception.ExceptionDescriptions.UNKNOWN_TYPE_OF_SORT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/quotes")
public class QuoteController {
    private final QuoteService quoteService;

    @PostMapping
    public QuoteOutDTO create(@Valid @RequestBody QuoteInDTO quoteInDto) {
        return quoteService.create(quoteInDto);
    }

    @GetMapping("/{id}")
    public QuoteOutDTO get(@NotNull @Positive @PathVariable Long id) {
        return quoteService.get(id);
    }

    @GetMapping("/top10")
    public List<QuoteFullDTO> getTop10(@RequestParam(defaultValue = "TOP") String order) {
        SortType sortType = SortType.from(order)
                .orElseThrow(() -> new IllegalArgumentException(UNKNOWN_TYPE_OF_SORT.getTitle()));
        return quoteService.getTop(sortType);
    }

    @GetMapping(value = "random")
    public QuoteOutDTO getRandomQuote() throws NoSuchAlgorithmException {
        return quoteService.getRandomQuote();
    }

    @PutMapping("/{id}")
    public QuoteOutDTO update(@NotNull @Positive @PathVariable Long id,
                              @Valid @RequestBody QuoteInDTO quoteInDto) {
        return quoteService.update(id, quoteInDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@NotNull @Positive @PathVariable Long id) {
        quoteService.delete(id);
    }
}
