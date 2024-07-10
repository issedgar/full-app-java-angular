
# Project structure
```
full-proyect 				(Carpeta principal)
├── README.md
├── docker-compose.yml
└── backendApp    			(Carpeta del backend - debe tener este nombre si se quiere usar la configuración del docker-compose)
	└── Dockerfile
	└── src
		└── main
			...	
└── frontend-app    		(Carpeta del frontend - debe tener este nombre si se quiere usar la configuración del docker-compose)
	└── Dockerfile
	└── src
		└── app
			...	
```



# Initialize all proyect
	`docker-compose up --build`

# User default Login

- username: admin
- password: 123456
