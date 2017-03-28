#!/bin/bash

if [ -f "hybris/config/local.properties" ]
then
	echo "config file found."
else
	echo "config hybris development environment ..."
	cd hybris/bin/platform
	. setantenv.sh
	echo "develop" | ant clean
	cd ../../..
	cp lib/dbdriver/* hybris/bin/platform/lib/dbdriver/
	cp -r config/installer/recipes/ installer/recipes/
	cd installer
	./install.sh -r daimler
	cd ..
fi

cp config/develop/* hybris/config/
