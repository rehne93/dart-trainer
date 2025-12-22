package de.baernreuther.dart.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FinishGenerator {

    public String generateFinish(int scoreLeft)  {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, List<String>> checkOuts;
            checkOuts = objectMapper.readValue(new ClassPathResource("static/json/checkout.json").getInputStream(), new TypeReference<HashMap<String, List<String>>>() {
            });

            if (checkOuts.containsKey(String.valueOf(scoreLeft))) {
                List<String> found = checkOuts.get(String.valueOf(scoreLeft));

                return String.join(" ", found);
            }
        } catch(IOException ex) {
            log.error("Error whole loading checkout.json", ex);
            return "";
        }
        return "";
    }

}
