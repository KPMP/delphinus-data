# generateDPRCommands.js

This script is used to generate commands for the Data Pathology Repository (DPR) machine to insert slides for our users. When the command is executed, a shell script will be created in the scripts folder named dprUpdate_<package_name>. Note that only these stain types will work with this script: PAS, FRZ, HE, SIL, TOL, TRI, CR, IHC

## How does this work?
When the script is executed, it makes a connection to the local MongoDB database. Then it searches the database for the matching package id supplied in the 1st argument of the command. When the package is found, the script will copy the names of the files and append the file type to the end of the command. Finally this populates a shell script with all the commands needed to populate the DPR.

## Prerequisites
Must have npm and Node v14 installed. A helpful package to install is nvm to manage different versions of node for each of our repositories.

## How to run the script
Make sure you have the name of the package containing all of the slides you want to insert in your clipboard. 

``` <repositories>/delphinus-data/scripts ```

``` npm i ```

``` node generateDPRCommands.js <package_name> ```

## Example
``` node generateDPRCommands.js 8712fa8e833798924c5c6205acdcd2d0```

# remove_slide.py

## How does this work?
When the script is executed, it makes a connection to the local MongoDB database. Then it searches the database for a matching slide. Then the script will remove any symbolic links that are linked to the slide that is being removed. Then the script will edit a link.sh file and remove the if block associated with the slide you are removing. Finally the slide will be removed from the MongoDB. This will completely remove the slide from the DPR and the only way to get the slide back is to run a slide processing script (see above).

## Prerequisites
Must have python 3 installed and the pymongo dependency installed. This can be installed using the requirements.txt file in this directory. It can be installed like this `pip3 install -r requirements.txt` 

## How to run this script
Make sure you have the name of the slide you want to remove

## Example 
`python3 remove_slide.py <slide_name>`

`python3 remove_slide.py S-1234-56789_SIL_2of2`