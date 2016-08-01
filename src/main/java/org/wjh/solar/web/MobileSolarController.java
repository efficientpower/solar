package org.wjh.solar.web;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wjh.solar.domain.User;
import org.wjh.solar.po.AjaxResult;
import org.wjh.solar.utils.EncryptUtils;

@Controller
public class MobileSolarController extends BaseController {

    private static Log logger = LogFactory.getLog(MobileSolarController.class);

    @RequestMapping("/mob/get.do")
    @ResponseBody
    public AjaxResult<Object> get(@RequestParam("id") String id) {
        AjaxResult<Object> res = new AjaxResult<Object>();
        try {
            User user = new User();
            user.setGender(User.GENDER_MALE);
            user.setNickName("helloworld");
            user.setUserId("wangjihuiupc@126.com");
            user.setPassWord(EncryptUtils.Md5Utils.getMd5OfString("123456"));
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            int key = userService.insert(user);
            res.setData(key);
        } catch (Exception e) {
            logger.error("get failed ", e);
            res.setFlag(AjaxResult.MobileCode.FAIL.intValue());
            res.setMessage(AjaxResult.MobileCode.FAIL.getMessage());
        }
        return res;
    }
}
