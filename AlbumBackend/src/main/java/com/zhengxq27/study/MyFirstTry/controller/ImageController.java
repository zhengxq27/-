package com.zhengxq27.study.MyFirstTry.controller;

import com.zhengxq27.study.MyFirstTry.model.ReturnMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Enumeration;

@Controller
public class ImageController {

    @RequestMapping(value="/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> checkImage(@RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            return new ResponseEntity(new ReturnMsg("uploadimg success"), HttpStatus.OK);
        }
        else {
            return new ResponseEntity(new ReturnMsg("uploadimg fail"), HttpStatus.BAD_REQUEST);
        }
        //return new ResponseEntity(new ReturnMsg("server error, delete fail"), HttpStatus.OK);
    }
}
