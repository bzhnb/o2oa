package com.x.program.center.jaxrs.appstyle;

import com.x.base.core.project.exception.LanguagePromptException;

class ExceptionIllegalFile extends LanguagePromptException {

	private static final long serialVersionUID = -5285650034988505084L;

	public ExceptionIllegalFile(String name) {
		super("无效的文件:{}，只支持 png 图片", name);
	}

}
