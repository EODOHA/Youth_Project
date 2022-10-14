package com.youth.service;


import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.youth.dto.RefBoardFileResponseDto;
import com.youth.entity.RefBoardFile;
import com.youth.repository.RefBoardFileRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RefBoardFileService {

	private final RefBoardFileRepository refBoardFileRepository;
	
	public RefBoardFileResponseDto findById(Long refId) throws Exception {
		return new RefBoardFileResponseDto(refBoardFileRepository.findById(refId).get());
	}
	
	public List<Long> findByRefBoardId(Long refBoardId) throws Exception {
		return refBoardFileRepository.findByRefBoardId(refBoardId);
	}
	
	public boolean uploadFile(MultipartHttpServletRequest multiRequest, Long refBoardId) throws Exception {
		
		if(refBoardId == null) throw new NullPointerException("Empty REF_BOARD_ID.");
		
		Map<String, MultipartFile> files = multiRequest.getFileMap();
		
		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		
		MultipartFile mFile;
		
		String saveFilePath = "", randomFileName = "";
		
		Calendar cal = Calendar.getInstance();
		
		List <Long> refResultList = new ArrayList<Long>();
		
		while(itr.hasNext()) {
			Entry <String, MultipartFile> entry = itr.next();
			
			mFile = entry.getValue();
			
			int fileSize = (int)mFile.getSize();
			
			if(fileSize > 0) {
				String filePath = "C:\\dev_tools\\eclipse\\workspace\\uploadFiles";
				
				filePath = filePath + File.separator + String.valueOf(cal.get(Calendar.YEAR)) + File.separator
							+ String.valueOf(cal.get(Calendar.MONTH) + 1);
				
				randomFileName = "FILE_" + RandomStringUtils.random(8, 0, 0, false, true, null, new SecureRandom());
				
				String realFileName = mFile.getOriginalFilename();
				String fileExt = realFileName.substring(realFileName.lastIndexOf(".") + 1);
				String saveFileName = randomFileName + "." + fileExt;
				saveFilePath = filePath + File.separator + saveFileName;
				
				File filePyhFolder = new File(filePath);
				
				if(!filePyhFolder.exists()) {
					if(!filePyhFolder.mkdirs()) {
						throw new Exception("File.mkdir() : Fail.");
					}
				}
				
				File saveFile = new File(saveFilePath);
				
				if(saveFile.isFile()) {
					boolean _exist = true;
					
					int index = 0;
					
					while (_exist) {
						index++;
						
						saveFileName = randomFileName + "(" + index + ")." + fileExt;
						
						String dictFile = filePath + File.separator + saveFileName;
						
						_exist = new File(dictFile).isFile();
						
						if(!_exist) {
							saveFilePath = dictFile;
						}
					}
					
					mFile.transferTo(new File(saveFilePath));
				} else {
					mFile.transferTo(saveFile);
				}
				
				RefBoardFile refBoardFile = RefBoardFile.builder()
											.refBoardId(refBoardId)
											.origFileName(realFileName)
											.saveFileName(saveFileName)
											.fileSize(fileSize)
											.fileExt(fileExt)
											.filePath(filePath)
											.deleteYn("N")
											.build();
				
				refResultList.add(refBoardFileRepository.save(refBoardFile).getRefId());
			}
		}
		
		return (files.size() == refResultList.size()) ? true : false;
	}
	
	public int updateDeleteYn(Long[] deleteRefIdList) throws Exception {
		return refBoardFileRepository.updateDeleteYn(deleteRefIdList);
	}
	
	public int deleteRefBoardFileYn(Long[] refBoardIdList) throws Exception {
		return refBoardFileRepository.deleteRefBoardFileYn(refBoardIdList);
	}
}
