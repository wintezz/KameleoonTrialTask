package com.kameleoon.mapper;

import com.kameleoon.dto.vote.VoteQuoteInDTO;
import com.kameleoon.entity.vote.VoteQuote;

public class VoteQuoteMapper {

    public static Integer voteQuoteToInteger(VoteQuote voteQuote) {
        return voteQuote.getScoreAfterVote();
    }

    public static VoteQuote dtoToVoteQuote(VoteQuoteInDTO voteQuoteInDto) {
        return VoteQuote.builder()
                .vote(voteQuoteInDto.isVote())
                .build();
    }
}
