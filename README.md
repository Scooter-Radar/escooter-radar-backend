# Introduction

### Scooter-Radar 
Scooter Radar is a software application suite that allows you to track available scooters from various electric scooter sharing companies in your area.

### escooter-radar-backend
Escooter Radar Backend is the backend of Scooter Radar

# Description

### Electric Scooter
Electric scooters (e-scooter) are defined as motorized bicycles propelled by human power or by a combination of human power and an electric motor. Electric scooters are easy to ride and let you breeze up and down hills without even breaking a drop of sweat.

Recently, electric kick scooters (e-scooters) have grown in popularity with the introduction of scooter-sharing systems that use apps allowing users to rent the scooters by the minute.

### Scooter-Sharing System
A scooter-sharing system is a shared transport service in which electric motorized scooters (also referred to as e-scooters) are made available to use for short-term rentals. E-scooters are typically "dockless", meaning that they do not have a fixed home location and are dropped off and picked up from certain locations in the service area.

Scooter-sharing systems work towards providing the public with a fast and convenient mode of transport for last-mile mobility in urban areas. Due to the growing popularity of scooter-sharing, municipal governments have enforced regulations on e-scooters to increase rider and pedestrian safety while avoiding the accrual of visual pollution. Scooter-sharing systems are one of the least expensive and most popular micromobility options.

# API Manual

## API Base Url 
http://escooter-radar-backend-env.eba-gkj72vkn.eu-west-3.elasticbeanstalk.com/

## API Routes
- api/scooter/zone?city={city}
> **city** being the name of the area or city where you want to get the scooters.


- api/scooter/provider?company={company}
> **company** being the name of the company that you want to get the scooters from.


- api/scooter/location?latitude={lat}&longitude={lon}&degree={degree}
> **lat** being the latitude of where you want the scooters to be near.
> **lon** being the longitude of where you want the scooters to be near.
> **degree** being the degree in which you want the scooters to be.

## Endopoints Examples
**Getting the e-scooters in Brussels :**
> http://escooter-radar-backend-env.eba-gkj72vkn.eu-west-3.elasticbeanstalk.com/api/scooter/zone?city=brussels  


**Getting all Pony e-scooters :**
> http://escooter-radar-backend-env.eba-gkj72vkn.eu-west-3.elasticbeanstalk.com/api/scooter/provider?company=Pony  


**Getting all the e-scooters within a distance of 5,55 km from a location :**  
> http://escooter-radar-backend-env.eba-gkj72vkn.eu-west-3.elasticbeanstalk.com/api/scooter/location?latitude=50.85045&longitude=4.34878&degree=0.05

## Converting distance from kilometer to degree

|  Degrees  |Kilometers|
|-----------|---------|
| 1° 	      | 111 km	|
| 0.1°  	  | 11.1 km	|
| 0.01°  	  | 1.11 km	|
| 0.001°  	| 111 m	  |
| 0.0001°  	| 11.1 m	|
| 0.00001°  | 1.11 m	|
| 0.000001° | 0.11 m	|

# Test it yourself

## Installation
Without a ssh key:
```
$ git clone https://github.com/Scooter-Radar/escooter-radar-backend.git
```

With a ssh key:
```
$ git clone git@github.com:Scooter-Radar/escooter-radar-backend.git
```

Launch docker compose:  
You need docker installed and added to your PATH environment variables.
If its not, download docker <a href="https://docs.docker.com/get-docker/">here</a>
```
$ docker-compose up -d
```

## Execution
You need gradle installed and added to your PATH environment variables.
If its not, download gradle <a href="https://gradle.org/install/">here</a>

In the project directory, you can run:
```
$ gradle bootrun
```

## Usage
After running the command above go to one of the following endpoints

**To get the scooters located in a particular city:**  
https://localhost:8980/api/scooter/zone?city={city}

**To get the scooters from a certain escooter sharing company:**  
https://localhost:8980/api/scooter/provider?company={company}

**To get the scooters located around a certain location:**  
http://localhost:8980/api/scooter/location?latitude={lat}&longitude={lon}&degree={degree}

# Featured
  - **Lime**
  - **Pony**
  - **Bird**
  - **Spin**
  - maybe more in the future...
 
NB: I'm highly dependent on the data made available by the scooter suppliers, 
  so if the api doesn't show the scooters in your city, don't be harsh on me 😥
  
# Author
- **Ayoub Lahyaoui**
