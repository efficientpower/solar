package org.wjh.solar.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wjh.solar.po.AjaxResult;

@Controller
public class MobileSolarController extends BaseController {
	
	private static Log logger = LogFactory.getLog(MobileSolarController.class);
	
	@RequestMapping("/mob/get.do")
	@ResponseBody
	public AjaxResult<Object> get(@RequestParam("id") String id){
		AjaxResult<Object> res = new AjaxResult<Object>();
		try{
			
		}catch(Exception e){
			logger.error("get failed ", e);
			res.setFlag(AjaxResult.MobileCode.FAIL.intValue());
			res.setMessage(AjaxResult.MobileCode.FAIL.getMessage());
		}
		return res;
	}
}
