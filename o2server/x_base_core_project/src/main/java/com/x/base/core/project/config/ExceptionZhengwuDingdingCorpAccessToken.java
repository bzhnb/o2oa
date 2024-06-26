package com.x.base.core.project.config;

import com.x.base.core.project.exception.PromptException;

class ExceptionZhengwuDingdingCorpAccessToken extends PromptException {

	private static final long serialVersionUID = -3439770681867963457L;

	ExceptionZhengwuDingdingCorpAccessToken(Integer errcode, String errmsg) {
		super("获取政务钉钉 JsapiTicket失败, errcode:{}, errmsg:{}.", errcode, errmsg);
	}
}