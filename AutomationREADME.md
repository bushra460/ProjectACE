# Automation of ACE(Advance Conveyance Examination)

### Table of content
---
- Description
- Introduction
- How to use it


---
#### Description:
##### [ACE](https://cbsawikiasfc.omega.dce-eir.net/pages/viewpage.action?pageId=192906476) project in detail
---
#### Introduction:

The script automates the process of downloading images from EVOX ftp site, then upload them on AWS S3 bucket after renaming them according to the naming convention.

##### The script is written in python.
---
#### How to use it:
##### Requirements:
- laptop/desktop
- Wi-Fi
- [Python](https://www.python.org/downloads) installed
- [Thonny](https://thonny.org) installed
- Use package manager pip to install boto3
- Run the script called [ACE automates EVOX](https://github.com/bushra460/ProjectACE/commit/d0ae79a14001962c7227ab8a235bcb13ff87b554) which is available in the project directory
- Program asks the user to enter the VIF #
- When the user enters the VIF # the code will download the images of the vehical locally on your machine after renaming them, and refining the directory structure by deleting the empty files then uploading the images to AWS S3 bucket.
---
