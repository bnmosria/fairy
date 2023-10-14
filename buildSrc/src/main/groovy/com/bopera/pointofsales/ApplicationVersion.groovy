package com.bopera.pointofsales

class ApplicationVersion {
    Integer major
    Integer minor
    Integer patch
    Boolean release

    private String getRelease() {
        return this.release ? '' : '-SNAPSHOT'
    }

    String generateVersion(project) {

        def resourcesDir = project.sourceSets.main.output.resourcesDir

        def versionPropertiesFile = new File(resourcesDir, "version.properties")

        if(!versionPropertiesFile.exists()) {
            throw new Exception('No version.properties file found')
        }

        Properties versionProperties = new Properties()

        versionPropertiesFile.withInputStream { stream ->
            versionProperties.load(stream)
        }

        this.major = versionProperties.major.toInteger()
        this.minor = versionProperties.minor.toInteger()
        this.patch = versionProperties.patch.toInteger()
        this.release = versionProperties.release.toBoolean()

        return "$major.$minor.$patch" + this.getRelease()
    }
}
