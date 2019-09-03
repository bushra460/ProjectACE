import os
import boto3


thePath = '/users/waelhussein/Documents/AutomatedEvox/'

def upload_files(thePath):
    session = boto3.Session(
        aws_access_key_id= 'AKIAX66ZOA36SXAUEVER',
        aws_secret_access_key = '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',
        region_name = 'ca-central-1'
        )
    # to connect to the low-level client interface
    s3 = boto3.client('s3')

    # to connect to the high level interface
    s3 = session.resource('s3')
    bucket = s3.Bucket('ace-evox')

    for subdir, dirs,files in os.walk(thePath):
        for file in files:
            fullPath = os.path.join(subdir, file)
            print(file)
            with open(fullPath, 'rb') as data:
                bucket.put_object(Key = fullPath[len(thePath):], Body = data)
                #print("done")
            

upload_files('/users/waelhussein/Documents/AutomatedEvox/')
print ("Finish")