package org.converter.jsontoxml

ant.copy(todir: project.build.outputDirectory ) {
	fileset(dir: project.build.sourceDirectory)
  }