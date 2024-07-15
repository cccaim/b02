package org.zerock.b02.controller;


import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.b02.dto.UploadFileDTO;

@RestController
@Log4j2
public class UpDownController {

  @Value("${org.zerock.upload.path}")
  private String uploadPath;

  @ApiOperation(summary = "Upload Post", description = "POST 방식으로 파일등록")
  @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public String upload(UploadFileDTO uploadFileDTO) {
    log.info(uploadFileDTO);

    if (uploadFileDTO.getFiles() != null){
      uploadFileDTO.getFiles().forEach(multipartFile -> {
        log.info(multipartFile.getOriginalFilename());
      });
    }
    return null;
  }
}
