package com.johnnyb.RedditClone.service;

import com.johnnyb.RedditClone.dto.SubredditDto;
import com.johnnyb.RedditClone.exceptions.SubredditNotFoundException;
import com.johnnyb.RedditClone.mapper.SubredditMapper;
import com.johnnyb.RedditClone.model.Subreddit;
import com.johnnyb.RedditClone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;


    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                            .stream()
                            .map(subredditMapper::mapSubredditToDto)
                            .collect(toList());
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with id " + id));

        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
