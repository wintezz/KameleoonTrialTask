package com.kameleoon.service;

import com.kameleoon.dto.vote.VoteQuoteInDTO;
import com.kameleoon.entity.quote.Quote;
import com.kameleoon.entity.vote.VoteQuote;
import com.kameleoon.exception.NotFoundException;
import com.kameleoon.mapper.VoteQuoteMapper;
import com.kameleoon.repository.QuoteRepository;
import com.kameleoon.repository.VoteQuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kameleoon.exception.ExceptionDescriptions.QUOTE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteQuoteService {

    private final QuoteRepository quoteRepository;

    private final VoteQuoteRepository voteQuoteRepository;

    public void vote(VoteQuoteInDTO voteQuoteInDto) {
        Optional<VoteQuote> voteQuote = voteQuoteRepository
                .findByQuoteIdAndUserId(voteQuoteInDto.getQuoteId(), voteQuoteInDto.getUserId());
        if (voteQuote.isPresent()) {
            if (voteQuote.get().isVote() && !voteQuoteInDto.isVote()) {
                updateScoreAndSaveVote(-2, voteQuoteInDto);
            }
            if (!voteQuote.get().isVote() && voteQuoteInDto.isVote()) {
                updateScoreAndSaveVote(2, voteQuoteInDto);
            }
        } else {
            if (voteQuoteInDto.isVote()) {
                updateScoreAndSaveVote(1, voteQuoteInDto);
            } else {
                updateScoreAndSaveVote(-1, voteQuoteInDto);
            }
        }
    }

    private void updateScoreAndSaveVote(int vote, VoteQuoteInDTO voteQuoteInDto) {
        quoteRepository.updateScore(vote, LocalDateTime.now(), voteQuoteInDto.getQuoteId());
        voteQuoteRepository.saveAndFlush(VoteQuoteMapper.dtoToVoteQuote(voteQuoteInDto));
    }

    public List<Integer> getScoreHistory(Long quoteId) {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new NotFoundException(QUOTE_NOT_FOUND.getTitle()));
        return quote.getVotes().stream()
                .sorted(Comparator.comparing(VoteQuote::getUpdatedAt))
                .map(VoteQuoteMapper::voteQuoteToInteger)
                .collect(Collectors.toList());
    }
}
