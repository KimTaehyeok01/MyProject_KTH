package com.study.Ex13FileUpload;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(){
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile[] uploadfile, Model model) throws IOException {
        List<FileDto> list = new ArrayList<>(); // 빈 객체 리스트 생성
        model.addAttribute("files", list);

        for(MultipartFile file : uploadfile){
            if(!file.isEmpty()){
                // fileDto 생성 - Builder패턴
                FileDto dto = FileDto.builder()
                        .uuid(UUID.randomUUID().toString()) // UUID - 중복안되게 파일 이름을 만듦
                        .fileName(file.getOriginalFilename()) // 사용자가 선택한 원래 파일 이름
                        .contentType(file.getContentType()) // image.png
                        .build();
                list.add(dto); // ArrayList에 추가

                // 물리적으로 file을 생성하기
                File newFileName = new File(dto.getUuid() + "_" + dto.getFileName());
                file.transferTo(newFileName);
                // DB에 파일 경로 + 이름을 기록한다.
            }
        }
        return "result";
    }
}
