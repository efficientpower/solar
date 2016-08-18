package org.wjh.solar.web;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.wjh.solar.po.AjaxResult;
import org.wjh.solar.po.AjaxResult.MobileCode;
import org.wjh.solar.utils.RegExpUtils;

@Controller
public class UploadController {
    private static final int BUFFFER_SIZE = 2*1024*1024;
    private static final int LIMIT_SIZE = 2000*1024*1024;
    private static Log logger = LogFactory.getLog(UploadController.class);

    @RequestMapping("/solar/upload.do")
    @ResponseBody
    public AjaxResult<Map<String, Object>> upload(
            @RequestParam("fileData") MultipartFile uploadFile,
            @RequestParam("md5") String md5, OutputStream os) {
        AjaxResult<Map<String, Object>> res = new AjaxResult<Map<String, Object>>();
        try {
             String regExp = "^([a-fA-F0-9]{32})$";
             if(!RegExpUtils.isMatcher(regExp, md5)){
                 logger.error("md5 is not valid! md5=" + md5);
                 res.setFlag(MobileCode.PARAM_EXCEPTION.intValue());
                 res.setMessage(MobileCode.PARAM_EXCEPTION.getMessage());
                 return res;
             }
             if(uploadFile.getSize() > LIMIT_SIZE){
                 logger.error("file size is too large!");
                 res.setFlag(MobileCode.FAIL.intValue());
                 res.setMessage("文件超过" + LIMIT_SIZE);
                 return res;
             }
             String file = "/home/wangjihui/MSHHDD";
             BufferedInputStream buffIns = new BufferedInputStream(uploadFile.getInputStream(),BUFFFER_SIZE);
             RandomAccessFile rdf = new RandomAccessFile(file, "rw");
             try{
                 rdf.seek(rdf.length()); 
                 byte buf[] = new byte[BUFFFER_SIZE];
                 int length;
                 while((length= buffIns.read(buf)) > -1){
                     rdf.write(buf,0,length);
                 }
             }finally{
                 buffIns.close();
                 rdf.close();
             }
        } catch (Exception e) {
            logger.error("upload failed! ", e);
            res.setFlag(MobileCode.FAIL.intValue());
            res.setMessage(MobileCode.FAIL.getMessage());
        }
        return res;
    }
}
