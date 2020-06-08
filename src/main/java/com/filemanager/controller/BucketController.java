package com.filemanager.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.filemanager.services.AmazonClient;

import net.sf.json.JSONObject;

@RestController
@RequestMapping("/")
@PropertySource("classpath:application.properties")
public class BucketController {

    private AmazonClient amazonClient;

    @Autowired
    BucketController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @RequestMapping(value = "/servicecheck", method = RequestMethod.GET)
	public String servicecheck() {
		return "This is the First Message From bookshop..!";
	}
    
    @RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model) {
		return "index";
	}
    
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam("uploadimage") MultipartFile uploadimage) {
		String respone = this.amazonClient.uploadFile(uploadimage);
		return ResponseEntity.ok(respone);
	}
    
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam("imgurl") String imgurl) {
		String respone = this.amazonClient.deleteFileFromS3Bucket(imgurl);
		return ResponseEntity.ok(respone);
	}
    
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "imgurl") String imgurl) {
        return this.amazonClient.deleteFileFromS3Bucket(imgurl);
    }
}
