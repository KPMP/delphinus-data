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
