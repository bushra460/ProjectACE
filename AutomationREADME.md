# Automation of ACE(Advance Conveyance Examination)

### Table of content
---
- Description
- Introduction
- How to use it


---
#### Description:
---
ACE: The idea behind the project is to provide BSOs (Border Service Officers) the ability to search for concealment locations in any given vehicle just by entering thew make, model  and year of any vehicle  into an app which will make their  search fast and accurate.

---
#### Introduction:

The script automates the process of downloading images from EVOX ftp site, then upload them on AWS S3 bucket after renaming them according to the naming convention for BSOs to access the images from cloud.
The script is written in python.

---
#### How to use it:
##### Requirements:
- laptop/desktop
- Wi-Fi
- [Python](https://www.python.org/downloads) installed
- [Thonny](https://thonny.org) installed
- Use package manager pip to install boto3
- Run the script called [ACEautomatedEVOX](https://github.com/bushra460/ProjectACE/commit/d0ae79a14001962c7227ab8a235bcb13ff87b554) which is available in the project directory
- Clone project to desktop for further use
- Program will ask the user to enter the VIF #
- When the user enters the VIF # the code will download the images of the vehical locally on your machine after renaming them, and refining the directory structure by deleting the empty files then uploading the images to AWS S3 bucket.
---
