import os
import csv
import ftplib
import sys

## Remove the old viflist from our system
if os.path.exists('viflist.csv'):
    os.remove('viflist.csv')
    print('The file is removed')
else:
    print('The file does not exist')
  
##Connect to the remote server using the ftp protocol
ftp = ftplib.FTP('portal.evox.com')
ftp.login('ail_cbsa', '4Cc4aw6A')
print(ftp.getwelcome())
## Change directory
print(ftp.cwd('_ail_list'))
##show the list of all directories and files
#ftp.dir()
ftp.retrlines('LIST')
##thePath where we want to save the new uploaded viflist.csv file
thePath = '/users/waelhussein/Documents/'
path = '/users/waelhussein/Documents/AutomatedEvox'
#print("path")
fileName = 'viflist.csv'
#print("list")
## join various path components
localFileName = os.path.join(thePath, os.path.basename(fileName))
with open(localFileName, 'wb') as fHandle:
    ftp.retrbinary('RETR %s' % fileName, fHandle.write)
    print(fileName)    
## read the downloaded file  
with open('/users/waelhussein/Documents/viflist.csv', 'r', encoding = 'ISO-8859-1') as csvFile: 
    csvReader = csv.reader(csvFile, delimiter = ',')
    ##skip the header (the title row)
    next(csvFile)
   



       
    vif = []
    make = []
    model = []
    year = []
    theVIF = input("Enter the new vif#")
    for row in csvReader:
        VifNum = row[0]
        VifNum = VifNum.zfill(4)
        if theVIF == VifNum:
            #print("yes") 
            Make =row[4]
            Model = row[5]
            Yr = row[3]
        
            vif.append(VifNum)
            make.append(Make)
            model.append(Model)
            year.append(Yr)       
            #theRow = vif.index(theVIF)
            print(Yr, Make, Model)
    ## Now go to stills_0640 folder for access further required folders and files           
            initialFolder = "stills_0640/"
            folderToGo = initialFolder + "MY" + Yr
            folderToGo = folderToGo + "/" + VifNum
    ##Creat local directory structure
            targetDirectory = path +"/"+ Make + "/" + Model + "/" + Yr + "/" 
        
            if not os.path.exists(targetDirectory):
                os.makedirs(targetDirectory)
                 
            print ("targetDirectory in laptop:  " + targetDirectory)
            os.chdir(targetDirectory)
    ##COONECT to FTP on each folder because if not the web hosting closes the connection
             
            ftp = ftplib.FTP("portal.evox.com")  
            ftp.login("ail_cbsa", "4Cc4aw6A")
            
            print ("Folder to go in FTP:  " + folderToGo)
            ftp.cwd(folderToGo)
            
    ## Copy the Image 046
            FileImage = VifNum + "_st0640_046.jpg"                
            try:
                ftp.retrbinary("RETR " + FileImage, open(FileImage, 'wb').write) 
                fileDownloaded = targetDirectory + "/" + FileImage
                NewFileName = targetDirectory + "/" + Yr + "_" + Make + "_" + Model + "_" + "_st0640_046_ext.jpg"
                if os.path.exists(fileDownloaded):
                    os.rename(fileDownloaded, NewFileName)
                    
                    
            except:
                pass
            
    ## Copy the Image 089
            FileImage = VifNum + "_st0640_089.jpg"                
            try:
                ftp.retrbinary("RETR " + FileImage, open(FileImage, 'wb').write) 
                fileDownloaded = targetDirectory + "/" + FileImage
                NewFileName = targetDirectory + "/"  + Yr + "_" + Make + "_" + Model + "_"  + "_st0640_089_ext.jpg"
                if os.path.exists(fileDownloaded):
                    os.rename(fileDownloaded, NewFileName)
                    
            except:
                pass        

    ## Copy the Image 049
            FileImage =VifNum + "_st0640_049.jpg"                
            try:
                ftp.retrbinary("RETR " + FileImage, open(FileImage, 'wb').write) 
                fileDownloaded = targetDirectory + "/" + FileImage
                NewFileName = targetDirectory + "/"  + Yr + "_" + Make + "_" + Model + "_" + "_st0640_049_ext.jpg"
                if os.path.exists(fileDownloaded):
                    os.rename(fileDownloaded, NewFileName)
                    
            except:
                pass 

    ## Copy the Image 050
            FileImage = VifNum + "_st0640_050.jpg"                
            try:
                ftp.retrbinary("RETR " + FileImage, open(FileImage, 'wb').write) 
                fileDownloaded = targetDirectory + "/" + FileImage
                NewFileName = targetDirectory + "/"  + Yr + "_" + Make + "_" + Model + "_"  + "_st0640_050_ext.jpg"
                if os.path.exists(fileDownloaded):
                    os.rename(fileDownloaded, NewFileName)
                    
            except:
                pass
    ## Copy the Image 115
            FileImage = VifNum + "_st0640_115.jpg"                
            try:
                ftp.retrbinary("RETR " + FileImage, open(FileImage, 'wb').write) 
                fileDownloaded = targetDirectory + "/" + FileImage
                NewFileName = targetDirectory + "/"  + Yr + "_" + Make + "_" + Model + "_"  + "_st0640_115_ext.jpg"
                if os.path.exists(fileDownloaded):
                    os.rename(fileDownloaded, NewFileName)
                    
            except:
                pass

    ## Copy the Image 157
            FileImage = VifNum + "_st0640_157.jpg"                
            try:
                ftp.retrbinary("RETR " + FileImage, open(FileImage, 'wb').write) 
                fileDownloaded = targetDirectory + "/" + FileImage
                NewFileName = targetDirectory + "/"  + Yr + "_" + Make + "_" + Model + "_" + "_st0640_157_ext.jpg"
                if os.path.exists(fileDownloaded):
                    os.rename(fileDownloaded, NewFileName)
                    
            except:
                pass

    ## Copy the Image 173
            FileImage = VifNum + "_st0640_173.jpg"                
            try:
                ftp.retrbinary("RETR " + FileImage, open(FileImage, 'wb').write) 
                fileDownloaded = targetDirectory + "/" + FileImage
                NewFileName = targetDirectory + "/"  + Yr + "_" + Make + "_" + Model + "_"  + "_st0640_173_ext.jpg"
                if os.path.exists(fileDownloaded):
                    os.rename(fileDownloaded, NewFileName)
                    
            except:
                pass

    ## Copy the Image 052
            FileImage = VifNum + "_st0640_052.jpg"                
            try:
                ftp.retrbinary("RETR " + FileImage, open(FileImage, 'wb').write) 
                fileDownloaded = targetDirectory + "/" + FileImage
                NewFileName = targetDirectory + "/"  + Yr + "_" + Make + "_" + Model + "_"  + "_st0640_052_int.jpg"
                if os.path.exists(fileDownloaded):
                    os.rename(fileDownloaded, NewFileName)
                    
            except:
                pass            

    ## Copy the Image 053
            FileImage = VifNum + "_st0640_053.jpg"                
            try:
                ftp.retrbinary("RETR " + FileImage, open(FileImage, 'wb').write) 
                fileDownloaded = targetDirectory + "/" + FileImage
                NewFileName = targetDirectory + "/"  + Yr + "_" + Make + "_" + Model + "_"  + "_st0640_053_int.jpg"
                if os.path.exists(fileDownloaded):
                    os.rename(fileDownloaded, NewFileName)
                   
                   
            except:
                pass

    ## Copy the Image 059
            FileImage = VifNum + "_st0640_059.jpg"                
            try:
                ftp.retrbinary("RETR " + FileImage, open(FileImage, 'wb').write) 
                fileDownloaded = targetDirectory + "/" + FileImage
                NewFileName = targetDirectory + "/"  + Yr + "_" + Make + "_" + Model + "_" + "_st0640_059_int.jpg"
                if os.path.exists(fileDownloaded):
                    os.rename(fileDownloaded, NewFileName)
                       
            except:
                pass

for root, dirs, files in os.walk(path):
        for file in files:
            #print(name)
            fName = os.path.join(root, file)
            if os.path.getsize(fName) == 0:
                os.remove(fName)
                print(fName)                
               
## cloe FTP connection for each folder    
ftp.quit()
## close the file
csvFile.close()