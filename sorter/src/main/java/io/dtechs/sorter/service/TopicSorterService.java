package io.dtechs.sorter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.dtechs.sorter.parsers.FilenameParser;

@Service
@RequiredArgsConstructor
public class TopicSorterService {

    private final FilenameParser filenameParser;

    public String getTopicByFilename(String filename) {
        return filenameParser.getTopicByFilename(filename);
    }
}
