package com.kameleoon.repository;

import com.kameleoon.entity.quote.Quote;
import com.kameleoon.entity.vote.VoteQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface VoteQuoteRepository extends JpaRepository<VoteQuote, Long> {

    @Transactional
    @Modifying
    void deleteByQuote(Quote quote);

    Optional<VoteQuote> findByQuoteIdAndUserId(Long quoteId, Long userId);
}
