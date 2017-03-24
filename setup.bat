@echo off

if exist hybris/config/local.properties (
	echo "config file found."
) else (
	echo "config hybris development environment ..."
	cd hybris/bin/platform
	call setantenv.bat
	call ant clean
	cd ../../..
	xcopy lib\dbdriver\*.* hybris\bin\platform\lib\dbdriver /s
)
xcopy config\develop\*.* hybris\config\ /s