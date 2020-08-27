# PPM TOOL

Personal Project Management tool is a full stack application that allows a user to create and manage their projects, while also maintaining project backlogs for a particular project.

The Front End of the application has been built using React, Redux, HTML and CSS.
The Back end has been built using Java and Spring framnework.

The services of the projects include:

### Validation Service

It validates the request that is sent by the user and gives an error message if there is any exception. For example if a user is passing an empty value for a required parameter.

### User Datails Service

It is the authentication service which matches the credentials of the user for login and signup.

### Project Service

It handles all the crud operations for the project of a user.

### Project Task Service

It handles all the crud operations for the backlog of a project of a user.

### User Service

The security is provided by using this service by utilizing JWT tokens. For example a user cannot see the projects of another user.

##### Required Dependencies and installations:

* Spring Web
* Spring Data JPA
* Spring Boot DevTools
* Java Version 8
* React
* react-dom
* react-router
* Redux
* Spring Security


