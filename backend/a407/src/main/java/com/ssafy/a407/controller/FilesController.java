package com.ssafy.a407.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.ssafy.a407.model.FileInfo;
import com.ssafy.a407.message.ResponseMessage;
import com.ssafy.a407.service.FilesStorageService;

@Controller
@RequestMapping("/file")
public class FilesController {

  @Autowired
  FilesStorageService storageService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";

    String name = file.getOriginalFilename();
    int dot = name.lastIndexOf(".");
    String ext = name.substring(dot);
    String now = LocalDateTime.now().toString();
    String newName = now.replace("-", "").replace(".", "").replace(":", "")+ ext;
//    System.out.println(newName);
//    System.out.println(System.getProperty("user.dir"));
    
    try {
      File newfile = new File(System.getProperty("user.dir") + "/uploads/" + newName);
      file.transferTo(newfile);
//      System.out.print(newfile.getPath());
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(newfile.getName()));
    } catch (Exception e) {
      System.out.print(e);
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("fail"));
    }
    
//    try {
//      storageService.save(file);
//      message = System.getProperty("user.dir") + "/uploads/" + newName;
//      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//    } catch (Exception e) {
//      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//    }
    
  }

  @GetMapping("/read")
  public ResponseEntity<List<FileInfo>> getListFiles() {
    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
      String filename = path.getFileName().toString();
      String url = MvcUriComponentsBuilder
          .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
//      System.out.println(url);
      return new FileInfo(filename, url);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }

  @GetMapping("/read/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }
//  public ResponseEntity<List<FileInfo>> getFile(String filename) {
//	  List<FileInfo> fileInfos = storageService.load(filename).map(path -> {
//		  String url = MvcUriComponentsBuilder
//				  .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
//		  System.out.println(url);
//		  return new FileInfo(filename, url);
//	  }).collect(Collectors.toList());
//	  
//	  return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
//	  
//  }
  
//  이미지 한개 url 검색
//  @GetMapping("/find")
//  public static List<Path> findByFileName(@RequestParam Path path, @RequestParam String fileName)
//	      throws IOException {
//
//	      if (!Files.isDirectory(path)) {
//	          throw new IllegalArgumentException("Path must be a directory!");
//	      }
//
//	      List<Path> result;
//	      // walk file tree, no more recursive loop
//	      try (Stream<Path> walk = Files.walk(path)) {
//	          result = walk
//	                  .filter(Files::isReadable)      // read permission
//	                  .filter(Files::isRegularFile)   // is a file
//	                  .filter(p -> p.getFileName().toString().equalsIgnoreCase(fileName))
//	                  .collect(Collectors.toList());
//	      }
//	      return result;
//
//	  }
}