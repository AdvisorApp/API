# AdvisorApp - API [![Build Status](https://api.travis-ci.org/AdvisorApp/API.svg?branch=master)](https://travis-ci.org/AdvisorApp/API)

## Contributing

In order to contribute, you must follow the guidelines described in [Contributing](./.github/CONTRIBUTING.md)
And update the [CHANGELOG](./CHANGELOG.md) on each tickets you close

## Architecture

The architecture followed these pictures : 

### On server
![Architecture](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/AdvisorApp/API/master/Architecture/servers_architecture.puml)

### On Model
![Architecture](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/AdvisorApp/API/master/Architecture/model_architecture.puml)

## Authentication & Authorisation
We chose to use [JWT](https://jwt.io/) for authentication.
 
### SignUp
To SignUp you have to do this request :

```
POST http://localhost:8090/api/auths/signup

Json body :
{
    "firstName": "firstName",
    "lastName": "lastName",
    "birthday": 1464294633131,
    "remoteId": 1234569,
    "email": "example@example.com",
    "password": "superSt0ngPassword"
}
```

It should return a `200` on success.

### SignIn
Once you signed-up, you will have to get a token (JWT). In order to to that, you have to do this request :

```
POST http://localhost:8090/api/auths/token

Json body :
{
    "email": "example@example.com",
    "password": "superSt0ngPassword"
}
```

It should return :

```
{
  "token": "json.web.token"
}
```

Then for each request you will do, you will have to attach the token in header. For instance :

```
GET http://localhost:8090/api/auths/hello

Header :
X-Authorization -> json.web.token
```