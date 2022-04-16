# Introduction

Scooter-Radar is a software application suite that allows you to track available scooters from various electric scooter sharing companies in your area.
This is the backend

This repository contains all code for the Scooter Radar Backend.

# Description

### E-scooter
Electric scooters (e-scooter) are defined as motorized bicycles propelled by human power or by a combination of human power and an electric motor. Electric scooters are easy to ride and let you breeze up and down hills without even breaking a drop of sweat.

Recently, electric kick scooters (e-scooters) have grown in popularity with the introduction of scooter-sharing systems that use apps allowing users to rent the scooters by the minute.

### Scooter-sharing system
A scooter-sharing system is a shared transport service in which electric motorized scooters (also referred to as e-scooters) are made available to use for short-term rentals. E-scooters are typically "dockless", meaning that they do not have a fixed home location and are dropped off and picked up from certain locations in the service area.

Scooter-sharing systems work towards providing the public with a fast and convenient mode of transport for last-mile mobility in urban areas. Due to the growing popularity of scooter-sharing, municipal governments have enforced regulations on e-scooters to increase rider and pedestrian safety while avoiding the accrual of visual pollution. Scooter-sharing systems are one of the least expensive and most popular micromobility options.

# Installation
Without a ssh key:
```
$ git clone https://github.com/Scooter-Radar/escooter-radar-backend.git
```

With a ssh key:
```
$ git clone git@github.com:Scooter-Radar/escooter-radar-backend.git
```

# Execution
You need gradle installed and added to your PATH environment variables.
If its not, download gradle <a href="https://gradle.org/install/">here</a>
```
$ gradle bootrun
```

# Usage
After running the command above go to https://localhost:8080/api/scooter/<**city**>
> **city** being the name of the area or city where you want to get the scooters.
  
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
