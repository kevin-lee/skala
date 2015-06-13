#!/bin/bash -e

echo "======================================================"
echo "Build packages"
echo "------------------------------------------------------"

echo ""
echo "======================================================"
echo "Run: sbt clean package"
echo "------------------------------------------------------"
if sbt clean package ; then
  echo "Done: sbt clean package"
  echo "======================================================"
else
  echo "Failed: sbt clean package" 1>&2
  echo "======================================================"
  exit 1
fi

echo ""
echo "======================================================"
echo "Building Packages: Done"
echo "======================================================"

sbt writeVersion
