package com.x.server.console;

import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.x.base.core.project.config.Config;
import com.x.base.core.project.connection.CipherConnectionAction;
import com.x.base.core.project.jaxrs.WrapString;
import com.x.base.core.project.tools.JarTools;

public class ConfigFactory {

	private ConfigFactory() {
		// nothing
	}

	private static Logger logger = LoggerFactory.getLogger(ConfigFactory.class);

	public static void sync() throws Exception {
		if (StringUtils.equalsIgnoreCase(Config.node(), Config.resource_node_centersPirmaryNode())) {
			logger.info("{} is not primary center. sync config from primary center {}.", Config.node(),
					Config.nodes().primaryCenterNode());
			syncFromPrimaryCenter();
			Config.flush();
		}
	}

	private static void syncFromPrimaryCenter() throws Exception {
		String url = Config.url_x_program_center_jaxrs("config");
		ReturnWoString wo = CipherConnectionAction.get(false, url).getData(ReturnWoString.class);
		byte[] bytes = Base64.decodeBase64(wo.getValue());
		File dist = new File(Config.base(), "config");
		JarTools.unjar(bytes, "", dist, true);
	}

	private static class ReturnWoString extends WrapString {

		private static final long serialVersionUID = 5128454714367834150L;

	}

}
