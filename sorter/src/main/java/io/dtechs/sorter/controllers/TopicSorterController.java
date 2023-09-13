package io.dtechs.sorter.controllers;

import io.dtechs.sorter.service.TopicSorterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path =  "/get/topic")
@RequiredArgsConstructor
public class TopicSorterController {

    private final TopicSorterService topicSorterService;

    @GetMapping("/by-filename")
    public String getTopicByFilename(@RequestParam String filename) {
        return topicSorterService.getTopicByFilename(filename);
    }
}
