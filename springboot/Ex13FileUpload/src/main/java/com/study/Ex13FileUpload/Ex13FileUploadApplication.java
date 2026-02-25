package com.study.Ex13FileUpload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ex13FileUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ex13FileUploadApplication.class, args);
	}

}

// 서버파일(이미지, zip) 업로드 방법
// 1. localhost : 윈도우즈 OS 프로젝트 폴더에 업로드
// 2. 원격지 AWS : 스토리지 S3 서비스
