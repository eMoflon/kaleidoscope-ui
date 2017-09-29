package com.kaleidoscope.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

public class BuildPath {

	private static final Logger logger = Logger.getLogger(WorkspaceHelper.class);

	private static void addContainerToBuildPath(final IJavaProject iJavaProject, final String container) {
		try {
			// Get current entries on the classpath
			Collection<IClasspathEntry> classpathEntries = new ArrayList<>(
					Arrays.asList(iJavaProject.getRawClasspath()));

			addContainerToBuildPath(classpathEntries, container);

			setBuildPath(iJavaProject, classpathEntries);
		} catch (JavaModelException e) {
			LogUtils.error(logger, e, "Unable to set classpath variable");
		}
	}

	private static void setBuildPath(final IJavaProject javaProject, final Collection<IClasspathEntry> entries,
			final IProgressMonitor monitor) throws JavaModelException {
		final SubMonitor subMon = SubMonitor.convert(monitor, "Set build path", 1);
		// Create new buildpath
		IClasspathEntry[] newEntries = new IClasspathEntry[entries.size()];
		entries.toArray(newEntries);

		// Set new classpath with added entries
		javaProject.setRawClasspath(newEntries, subMon.newChild(1));
	}

	private static void setBuildPath(final IJavaProject javaProject, final Collection<IClasspathEntry> entries)
			throws JavaModelException {
		setBuildPath(javaProject, entries, new NullProgressMonitor());
	}

	public static void addContainerToBuildPath(final IProject project, final String container) {
		addContainerToBuildPath(JavaCore.create(project), container);
	}

	private static void addContainerToBuildPath(final Collection<IClasspathEntry> classpathEntries,
			final String container) {
		IClasspathEntry entry = JavaCore.newContainerEntry(new Path(container));
		for (IClasspathEntry iClasspathEntry : classpathEntries) {
			if (iClasspathEntry.getPath().equals(entry.getPath())) {
				// No need to add variable - already on classpath
				return;
			}
		}

		classpathEntries.add(entry);
	}
}
