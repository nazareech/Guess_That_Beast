package com.example.guess_that_beast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MemeLoader {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Meme> loadMemes() throws IOException {
        try (InputStream inputStream = MemeLoader.class.getResourceAsStream("/com/example/guess_that_beast/memes.json")) {
            if (inputStream == null) {
                throw new FileNotFoundException("JSON file not found");
            }
            List<Meme> memes = mapper.readValue(inputStream,
                    mapper.getTypeFactory().constructCollectionType(List.class, Meme.class));

            if (memes.isEmpty()) {
                throw new IllegalStateException("No memes found in JSON file");
            }

            return memes;
        }
    }
}
