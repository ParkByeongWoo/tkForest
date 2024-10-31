package com.tkForest.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

// Multipart 파일을 받아서 savedFileName을 만드는 작업

public class FileService {
	// 1) 서버에 디렉토리가 없으면 디렉토리 생성
	// 2) 전달받은 업로드 파일에서 원본파일명을 꺼내서 저장용 파일명 생성(랜덤값)
	// 3) 1번과 2번을 이용해서 실제 디렉토리에 파일을 저장하는 작업
	// 4) 저장파일명을 DB에도 저장할 수 있도록 반환
	public static String saveFile(MultipartFile uploadFile, String uploadPath) {
		// 파일이 첨부되면 디렉토리가 있는지 확인
		// 디렉토리가 없으면 생성, 있으면 그대로 사용
		if(!uploadFile.isEmpty()) {
			File path = new File(uploadPath);
			if(!path.isDirectory())
				path.mkdirs();
		}
		
		// 원본파일명 추출
		String originalFileName = uploadFile.getOriginalFilename();
		
		// 랜덤값 발생
		String uuid = UUID.randomUUID().toString();
		
		// 원본파일명에서 파일명과 확장명을 분리
		String filename;
		String ext;
		String savedFileName;
		
		// .의 위치를 찾음
		int position = originalFileName.lastIndexOf('.');
		
		// 확장자 추출
		// 확장자가 없는 경우 : -1값임
		if(position == -1) 
			ext = ""; 
		else  // 확장자가 있는 경우 이력서.jpg
			ext = "." + originalFileName.substring(position + 1);  // .jpg
		
		// 파일명 추출
		filename = originalFileName.substring(0, position);

		// savedFileName 작성
		savedFileName = filename + "_" + uuid +  ext;
		
		// 서버공간에 파일 저장하기
		File serverFile = null;
		serverFile = new File(uploadPath + "/" + savedFileName);
		
		try {
			uploadFile.transferTo(serverFile);
		} catch (IOException e) {
			savedFileName = null;   // 파일을 저장하는 도중 오류가 발생했을 경우에는 DB에 파일명도 저장되면 안됨 
			e.printStackTrace();
		}
		
		return savedFileName;  		// DB에 저장할 변경된 파일명
	}
	
	// 서버공간에 저장된 파일을 삭제 (경로 + savedFileName)
	public static boolean deleteFile(String fullPath) {
		boolean result = false;		// 삭제 여부 반환
		
		File delFile = new File(fullPath);
		
		if(delFile.isFile())
			result = delFile.delete();
		
		return result;
	}
	
	public static void renameFile(String oldPath, String newPath) {
	    File oldFile = new File(oldPath);
	    File newFile = new File(newPath);
	    if (oldFile.exists()) {
	        oldFile.renameTo(newFile);
	    }
	}
}

