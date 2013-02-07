/*
 * This file is part of Technic Launcher.
 *
 * Copyright (c) 2013-2013, Technic <http://www.technicpack.net/>
 * Technic Launcher is licensed under the Spout License Version 1.
 *
 * Technic Launcher is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the Spout License Version 1.
 *
 * Technic Launcher is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the Spout License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */

package org.spoutcraft.launcher.technic;

import java.io.File;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.spoutcraft.launcher.exceptions.RestfulAPIException;
import org.spoutcraft.launcher.technic.rest.Modpack;
import org.spoutcraft.launcher.technic.rest.RestAPI;
import org.spoutcraft.launcher.technic.rest.pack.FallbackModpack;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestInfo extends PackInfo {
	@JsonProperty("name")
	private String name;
	@JsonProperty("recommended")
	private String recommended;
	@JsonProperty("latest")
	private String latest;
	@JsonProperty("builds")
	private List<String> builds;
	@JsonProperty("logo_md5")
	private String logoMD5;
	@JsonProperty("background_md5")
	private String backgroundMD5;
	@JsonProperty("icon_md5")
	private String iconMD5;
	@JsonProperty("url")
	private String url;

	private RestAPI rest;

	private String displayName = "Technic";

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setRest(RestAPI rest) {
		this.rest = rest;
	}

	public RestAPI getRest() {
		return rest;
	}

	public String getWebURL() {
		return url;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}
	@Override
	public String getRecommended() {
		return recommended;
	}

	@Override
	public String getLatest() {
		return latest;
	}

	@Override
	public List<String> getBuilds() {
		return builds;
	}

	@Override
	public String getLogoURL() {
		return rest.getModpackImgURL(name);
	}
	
	@Override
	public String getBackgroundURL() {
		return rest.getModpackBackgroundURL(name);
	}

	@Override
	public String getIconURL() {
		return rest.getModpackIconURL(name);
	}

	@Override
	public String getLogoMD5() {
		return logoMD5;
	}

	@Override
	public String getBackgroundMD5() {
		return backgroundMD5;
	}

	@Override
	public String getIconMD5() {
		return iconMD5;
	}

	@Override
	public Modpack getModpack() {
		try {
			return getRest().getModpack(this, getBuild());
		} catch (RestfulAPIException e) {
			e.printStackTrace();

			File installed = new File(this.getBinDir(), "installed");
			if (installed.exists()) {
				return new FallbackModpack(getName(), getBuild());
			}

			return null;
		}
	}

	@Override
	public String toString() {
		return "{ RestInfo [name: " + name + ", recommended: " + recommended + ", latest: " + latest + ", builds: " + builds + "] }";
	}
}