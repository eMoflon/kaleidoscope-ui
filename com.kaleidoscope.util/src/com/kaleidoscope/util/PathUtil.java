package com.kaleidoscope.util;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.URI;

public class PathUtil {

	public static String lastSegmentOf(final String name) {
		int startOfLastSegment = name.lastIndexOf(".");

		if (startOfLastSegment == -1)
			startOfLastSegment = 0;
		else
			startOfLastSegment++;

		return name.substring(startOfLastSegment);
	}

	public static URI getDefaultURIToEcoreFileInPlugin(final String pluginID) {
		return URI.createPlatformPluginURI("/" + pluginID + "/" + getDefaultPathToEcoreFileInProject(pluginID), true);
	}

	public static String getDefaultPathToEcoreFileInProject(final String projectName) {
		return getDefaultPathToFileInProject(projectName, ".ecore");
	}

	public static String getDefaultPathToFileInProject(final String projectName, final String ending) {
		return "model/" + getDefaultNameOfFileInProjectWithoutExtension(projectName) + ending;
	}

	public static String getDefaultNameOfFileInProjectWithoutExtension(final String projectName) {
		return PathUtil.lastCapitalizedSegmentOf(projectName);
	}

	public static String lastCapitalizedSegmentOf(final String name) {
		return StringUtils.capitalize(lastSegmentOf(name));
	}
}
