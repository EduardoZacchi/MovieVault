package com.movievault.service;

import com.movievault.controller.request.StreamingRequest;
import com.movievault.entity.Streaming;
import com.movievault.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreamingService {

    private final StreamingRepository streamingRepository;

    public List<Streaming> getAllStreaming(){
        return streamingRepository.findAll();
    }

    public Optional<Streaming> getStreamingById(Long id){
        return streamingRepository.findById(id);
    }

    public Streaming saveStreaming(Streaming streaming){
        return streamingRepository.save(streaming);
    }

    public void deleteStreaming(Long id){
        streamingRepository.deleteById(id);
    }
}
