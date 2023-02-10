package com.kameleoon.service;

import com.kameleoon.dto.quote.QuoteFullDTO;
import com.kameleoon.dto.quote.QuoteInDTO;
import com.kameleoon.dto.quote.QuoteOutDTO;
import com.kameleoon.entity.quote.Quote;
import com.kameleoon.entity.user.User;
import com.kameleoon.entity.vote.SortType;
import com.kameleoon.exception.ConflictException;
import com.kameleoon.exception.NotFoundException;
import com.kameleoon.mapper.QuoteMapper;
import com.kameleoon.repository.QuoteRepository;
import com.kameleoon.repository.UserRepository;
import com.kameleoon.repository.VoteQuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import static com.kameleoon.exception.ExceptionDescriptions.*;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;

    private final VoteQuoteRepository voteQuoteRepository;

    private final UserRepository userRepository;

    public QuoteOutDTO create(QuoteInDTO quoteInDto) {
        User user = getUserFromRepository(quoteInDto.getUserId());
        Quote quote = QuoteMapper.dtoToQuote(quoteInDto);
        quote.setUser(user);
        try {
            return QuoteMapper.quoteToDto(quoteRepository.save(quote));
        } catch (DataAccessException dataAccessException) {
            throw new ConflictException(QUOTE_ALREADY_EXISTS.getTitle());
        }
    }

    public QuoteOutDTO update(Long id, QuoteInDTO quoteInDto) {
        Quote quote = getQuoteFromRepositoryByUserId(id, quoteInDto.getUserId());
        return QuoteMapper.quoteToDto(quoteRepository.saveAndFlush(quote));
    }

    public QuoteOutDTO get(Long id) {
        return QuoteMapper.quoteToDto(quoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle())));
    }

    @Transactional(readOnly = true)
    public List<QuoteFullDTO> getTop(SortType sortType) {
        List<Quote> quotes = sortType == SortType.TOP? quoteRepository.findTop10ByOrderByScoreAsc()
                : quoteRepository.findTop10ByOrderByScoreDesc();
        return QuoteMapper.eventToListOutDto(quotes);
    }

    @Transactional
    public void delete(Long id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle()));
        voteQuoteRepository.deleteByQuote(quote);
        quoteRepository.delete(quote);
    }

    private User getUserFromRepository(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getTitle()));
    }

    private Quote getQuoteFromRepositoryByUserId(Long quoteId, Long userId) {
        return quoteRepository.findByIdAndUserId(quoteId, userId)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle()));
    }

    @Transactional(readOnly = true)
    public QuoteOutDTO getRandomQuote() throws NoSuchAlgorithmException {
        List<Quote> quotes = quoteRepository.findAll();
        int random = SecureRandom.getInstanceStrong().nextInt(quotes.size());
        return QuoteMapper.quoteToDto(quotes.get(random));
    }
}
