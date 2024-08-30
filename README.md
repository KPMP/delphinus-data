# delphinus-data
This project provides the back-end service layer for the Digital Pathology Repository for the Kidney Precision Medicine Project.

## Generating the Linkage Script
To generate the script that creates the symbolic links to the DeepZoom assets:
5. Bash into the Spring container `docker exec -it delphinus-spring bash`
6. Rebuild the delphinus-data jar `./gradlew build`
7. Run the link script generator `java -cp build/libs/delphinus-data-1.0.jar -Dloader.main=org.kpmp.GenerateLinkScript org.springframework.boot.loader.PropertiesLauncher`
8. Exit the container and cp the file to where the DeepZoom links are kept `docker cp delphinus-spring:/home/gradle/link.sh /data//deepZoomImages`

# Build
`./gradlew build docker`
The default tag is the github branch name if no verison is provided
To pass a version when building the docker image execute
`./gradlew build docker -Ptag=<tagNumber>`
