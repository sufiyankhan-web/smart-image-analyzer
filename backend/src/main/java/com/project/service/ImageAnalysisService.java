package com.project.service;

import com.project.dto.ImageAnalysisResponseDto;
import com.project.dto.ImageComparisonResponseDto;
import com.project.dto.UploadResponseDto;
import com.project.dto.BatchAnalysisResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageAnalysisService {

    UploadResponseDto uploadAndAnalyze(MultipartFile file);

    ImageComparisonResponseDto compare(MultipartFile firstFile, MultipartFile secondFile);

    BatchAnalysisResponseDto analyzeBatch(List<MultipartFile> files);

    ImageAnalysisResponseDto getById(Long id);

    List<ImageAnalysisResponseDto> getAll();

    void deleteById(Long id);
}
