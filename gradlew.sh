#!/bin/sh

# -----------------------------------------------------------------------------
# Gradle startup script for UNIX
# -----------------------------------------------------------------------------

# Copyright 2015 the original author or authors.
# Licensed under the Apache License, Version 2.0

# Set script directory
DIRNAME=$(dirname "$0")
APP_BASE_NAME=$(basename "$0")
APP_HOME=$(cd "$DIRNAME" && pwd)

# Default JVM options
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Check for JAVA_HOME
if [ -n "$JAVA_HOME" ]; then
  JAVA_EXE="$JAVA_HOME/bin/java"
  if [ ! -x "$JAVA_EXE" ]; then
    echo ""
    echo "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME"
    echo ""
    echo "Please set the JAVA_HOME variable in your environment to match the"
    echo "location of your Java installation."
    exit 1
  fi
else
  JAVA_EXE="java"
  if ! command -v "$JAVA_EXE" >/dev/null 2>&1; then
    echo ""
    echo "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH."
    echo ""
    echo "Please set the JAVA_HOME variable in your environment to match the"
    echo "location of your Java installation."
    exit 1
  fi
fi

# Set classpath to wrapper JAR
CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

# Execute Gradle Wrapper
exec "$JAVA_EXE" $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS \
  -Dorg.gradle.appname="$APP_BASE_NAME" \
  -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
