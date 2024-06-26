package com.x.program.center.core.entity.wrap;

import com.x.base.core.entity.JpaObject;
import com.x.base.core.project.bean.WrapCopier;
import com.x.base.core.project.bean.WrapCopierFactory;
import com.x.program.center.core.entity.Script;

public class WrapScript extends Script {

	private static final long serialVersionUID = -1423249662095387667L;
	public static WrapCopier<Script, WrapScript> outCopier = WrapCopierFactory.wo(Script.class, WrapScript.class, null,
			JpaObject.FieldsInvisible);

	public static WrapCopier<WrapScript, Script> inCopier = WrapCopierFactory.wi(WrapScript.class, Script.class, null,
			JpaObject.FieldsUnmodifyExcludeId);
}
