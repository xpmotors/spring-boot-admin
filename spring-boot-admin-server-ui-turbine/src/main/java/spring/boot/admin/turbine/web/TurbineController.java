/*
 * Copyright 2013-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spring.boot.admin.turbine.web;

import de.codecentric.boot.admin.web.AdminController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides informations for the turbine view. Only available clusters until now.
 * 
 * @author Johannes Edmeier
 */
@AdminController
@ResponseBody
@RequestMapping("/api/turbine")
public class TurbineController {

	private final String[] clusters;
	private boolean useStaticStreamUrl;

	public TurbineController(String[] clusters) {
		this(clusters, false);
	}

	public TurbineController(String[] clusters, boolean useStaticStreamUrl) {
		this.clusters = Arrays.copyOf(clusters, clusters.length);
		this.useStaticStreamUrl = useStaticStreamUrl;
	}

	@RequestMapping(value = "/clusters", method = RequestMethod.GET)
	public Map<String, ?> getClusters() {
		Map<String, Object> map = new HashMap();
		map.put("clusters", clusters);
		map.put("useStaticStreamUrl", useStaticStreamUrl);
		return map;
	}

}
