# shortme
Short me api is used for shortening different links

# created ui for testing api 
https://mv8bsy.csb.app/

Used
- H2 DB for storing key value pairs
- Spring Schedule used for removing data from db (for better performance and free tier)
- Spring Boot as backend
- Material UI for frontend
- React library

# Note
- every url will be expired after 4 hours from created time

# endpoints

- api url -> https://shortme-api.herokuapp.com/

- ```api/shortUrl``` method=POST
- - request ```{"originalUrl": "your url goes here"}```
- ```/{shortUrl}``` method=GET
