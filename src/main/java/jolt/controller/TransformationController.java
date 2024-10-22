package jolt.controller;

import jolt.service.TransformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransformationController {
    @Autowired
    private TransformationService transformationService;
    @PostMapping("/transform")
    public ResponseEntity<Object> transformData(@RequestBody Object inputData,
                                                @RequestParam String targetSystem) {
        Object transformedData = transformationService.transformData(inputData, targetSystem);
        return ResponseEntity.ok(transformedData);
    }
}