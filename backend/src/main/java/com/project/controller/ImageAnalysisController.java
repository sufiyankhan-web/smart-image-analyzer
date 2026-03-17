package com.project.controller;

import com.project.dto.ImageAnalysisResponseDto;
import com.project.dto.ImageComparisonResponseDto;
import com.project.dto.UploadResponseDto;
import com.project.dto.BatchAnalysisResponseDto;
import com.project.service.ImageAnalysisService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@Validated
public class ImageAnalysisController {

    private final ImageAnalysisService service;

    public ImageAnalysisController(ImageAnalysisService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadResponseDto> upload(@RequestParam("file") MultipartFile file) {
        UploadResponseDto response = service.uploadAndAnalyze(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/compare")
    public ResponseEntity<ImageComparisonResponseDto> compare(
            @RequestParam("firstFile") MultipartFile firstFile,
            @RequestParam("secondFile") MultipartFile secondFile) {
        return ResponseEntity.ok(service.compare(firstFile, secondFile));
    }

    @PostMapping("/batch")
    public ResponseEntity<BatchAnalysisResponseDto> analyzeBatch(@RequestParam("files") List<MultipartFile> files) {
        return ResponseEntity.ok(service.analyzeBatch(files));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageAnalysisResponseDto> getById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ImageAnalysisResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @Positive Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
