package org.wjh.solar.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wjh.solar.domain.User;
import org.wjh.solar.lock.ZookeeperLock;
import org.wjh.solar.lock.ZookeeperLockContext;
import org.wjh.solar.po.AjaxResult;
import org.wjh.solar.utils.EncryptUtils;

@Controller
public class MobileSolarController extends BaseController {
    private static Log logger = LogFactory.getLog(MobileSolarController.class);
    @Autowired
    @Qualifier("solarLocker")
    private ZookeeperLockContext solarLocker;
    
    @RequestMapping("/mob/get.do")
    @ResponseBody
    public AjaxResult<Object> get(@RequestParam("userId") String userId) {
        AjaxResult<Object> res = new AjaxResult<Object>();
        ZookeeperLock lock = solarLocker.getLock("solar");
        try {
            AopUtils.isAopProxy(solarLocker);
            AopUtils.isCglibProxy(solarLocker);
            AopUtils.isJdkDynamicProxy(solarLocker);
            lock.lock();
            EncryptUtils.Md5Utils.getMd5OfString("123456");
            User user = userService.getByUserId(userId);
            res.setData(user);
        } catch (Exception e) {
            logger.error("get failed ", e);
            res.setFlag(AjaxResult.MobileCode.FAIL.intValue());
            res.setMessage(AjaxResult.MobileCode.FAIL.getMessage());
        }finally{
            lock.unlock();
        }
        return res;
    }
}
