package com.x.processplatform.core.express.assemble.surface.jaxrs.task;

import com.x.base.core.project.annotation.FieldDescribe;
import com.x.base.core.project.gson.GsonPropertyObject;

import io.swagger.v3.oas.annotations.media.Schema;

public class ActionCountWithFilterWo extends GsonPropertyObject {

	private static final long serialVersionUID = -7920286614089277384L;

	@FieldDescribe("待办数量")
	@Schema(description = "返回待办数量")
	private Long count = 0L;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
}