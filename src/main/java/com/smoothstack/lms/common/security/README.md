### LMS Common Security Package for Microservice



#### Configuration

File: `application.properties`

    # Configuration Identity Provider Server
    # Point it to Eureka
        
    identityprovider.url.host = <hostname-or-ip>
    identityprovider.url.port = <port-number>
    identityprovider.url.path = <part-as-same-as-request-mapping>  
      
    
See `CommonIdentityTenantService.java` for more information

