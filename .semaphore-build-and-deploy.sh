#!/bin/bash

echo "============================================"
echo "Java Info"
java -version
echo "--------------------------------------------"
echo "Scala Info"
scala -version
echo "--------------------------------------------"
echo "SBT Info"
sbt --version
echo "--------------------------------------------"
echo "Memory Info"
echo "--------------------------------------------"
echo "free -m"
free -m
echo "--------------------------------------------"
echo "grep MemTotal /proc/meminfo"
grep MemTotal /proc/meminfo
echo "--------------------------------------------"

./.build-packages.sh

export THIS_BRANCH="$BRANCH_NAME"
if [ "$THIS_BRANCH" == "release" ];
  then

  export VERSION_FILE="target/version.tmp"
  echo "Version File=$VERSION_FILE"

  if [ ! -f "$VERSION_FILE" ]
    then
    echo "NO version file, '$VERSION_FILE', exists so quit!"
    exit 1
  fi

  export PROJECT_VERSION=`cat $VERSION_FILE`
  echo "PROJECT_VERSION=$PROJECT_VERSION"

  if [ ! -n "$PROJECT_VERSION" ]
    then
    echo "NO PROJECT_VERSION is found so quit!"
    exit 1
  fi

  export GIT_TAG="v$PROJECT_VERSION"
  echo "GIT_TAG=$GIT_TAG"
  export PROJECT_BUILD_NAME="$GIT_TAG"
  echo "PROJECT_BUILD_NAME=$PROJECT_BUILD_NAME"

  echo "check git ls-remote --exit-code --tags origin $GIT_TAG 2>&1 > /dev/null"

  if git ls-remote --exit-code --tags origin $GIT_TAG 2>&1 > /dev/null ; then
    echo "the given tag '$GIT_TAG' already exists so skip it!"
  else
    export REPO_LOCATION="git@github.com:Kevin-Lee/skala.git"
    echo "REPO_LOCATION=$REPO_LOCATION"

    export USER_EMAIL="builder+github@lckymn.com"
    echo "USER_EMAIL=$USER_EMAIL"

    export USER_NAME="Kevin-App-Builder"
    echo "USER_NAME=$USER_NAME"

    echo "the given tag '$GIT_TAG' does not exist so run it!"
    git config --global user.email "$USER_EMAIL"
    git config --global user.name "$USER_NAME"

    git tag "$GIT_TAG" -a -m "Automatically generated tag by Semaphore CI for $GIT_TAG"
    git push "$REPO_LOCATION" --tags

  fi

  ./.release-files-copy.sh
  ./.deploy-to-github.sh

  echo "============================================"
  echo "Build and Deploy: Done"
  echo "============================================"
else
  echo "============================================"
  echo "It is not release branch so skip deployment."
  echo "Build: Done"
  echo "============================================"
fi
