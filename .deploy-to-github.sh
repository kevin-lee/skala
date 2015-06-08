#!/bin/bash

echo "============================================"
echo "Deploying to GitHub"
if sbt checkGithubCredentials releaseOnGithub ; then
  echo "--------------------------------------------"
  echo "Deploying to GitHub: Done"
  echo "============================================"
#  echo "============================================"
#  echo "Uploading to S3"
#  if sbt s3-upload ; then
#    echo "--------------------------------------------"
#    echo "Uploading to S3: Done"
#    echo "============================================"
#  else
#    echo "============================================"
#    echo "Build and Deploy: Failed"
#    echo "============================================"
#    exit 1
#  fi
else
  echo "============================================"
  echo "Build and Deploy: Failed"
  echo "============================================"
  exit 1
fi