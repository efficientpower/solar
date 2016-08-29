package org.wjh.solar.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wjh.solar.po.AjaxResult;
import org.wjh.solar.po.AjaxResult.MobileCode;
import org.wjh.solar.utils.RegExpUtils;

@Controller
public class UploadController {
    public static final String TMP_FILE_DIR = "/home/wangjihui/tmp";
    public static final String FILE_FIELD_NAME = "fileData";
    private static final int BUFFFER_SIZE = 2*1024*1024;
    private static Log logger = LogFactory.getLog(UploadController.class);

    @RequestMapping("/solar/upload.do")
    @ResponseBody
    public AjaxResult<Map<String, Object>> upload(
            HttpServletRequest request,
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
             String fileName = "/home/wangjihui/workspace/tmp/" + md5;
             long readBytes = 0;
             long totalSize = 0;
             FileItemFactory factory = new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, new File("/home/wangjihui/workspace/tmp"));  
             ServletFileUpload upload = new ServletFileUpload(factory);
             FileItemIterator iterator = upload.getItemIterator(request);
             while(iterator.hasNext()){
                 FileItemStream itemStream = iterator.next();
                 if(FILE_FIELD_NAME.equals(itemStream.getFieldName())){
                     readBytes = writeRdfFile(new File(fileName).length(), itemStream, fileName);
                     FileItem item = factory.createItem(itemStream.getFieldName(), itemStream.getContentType(), itemStream.isFormField(), itemStream.getName());
                     totalSize = item.getSize();
                 }
             }
        } catch (Exception e) {
            logger.error("upload failed! ", e);
            res.setFlag(MobileCode.FAIL.intValue());
            res.setMessage(MobileCode.FAIL.getMessage());
        }
        return res;
    }
    
    private long writeRdfFile(long readBytes,FileItemStream itemStream, String filePath){
        BufferedInputStream buffIns = null;
        RandomAccessFile rdf  = null;
        try {
            buffIns = new BufferedInputStream(itemStream.openStream());
            buffIns.skip(readBytes);
            rdf = new RandomAccessFile(new File(filePath), "rw");
            rdf.seek(rdf.length());
            int length ;
            byte buffer [] = new byte[BUFFFER_SIZE];
            while((length = buffIns.read(buffer)) > -1){
                rdf.write(buffer, 0 , length);
                readBytes += length;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try{
                if(buffIns != null){
                    buffIns.close();
                }
                if(rdf != null){
                    rdf.close();
                }
            }catch(Exception w){
                logger.error("close error ", w);
            }
            
        }
        return readBytes;
    }
}
