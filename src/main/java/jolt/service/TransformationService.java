package jolt.service;

import com.bazaarvoice.jolt.Chainr;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class TransformationService {

    private final ObjectMapper objectMapper;

    public TransformationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Object transformData(Object inputData, String targetSystem) {
        String specPath = "/jolt-specs/spec" + targetSystem + ".json";
        try (InputStream specStream = getClass().getResourceAsStream(specPath)) {
            if (specStream == null) {
                throw new IllegalArgumentException("Spec file not found for target system " + targetSystem);
            }
            List<Object> specJson = objectMapper.readValue(specStream, List.class);
            Chainr chainr = Chainr.fromSpec(specJson);
            return chainr.transform(inputData);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading or applying Jolt spec for target system " + targetSystem, e);
        }
    }
}
