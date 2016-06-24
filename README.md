
The backend is simple Spring Boot app exposing the request headers on `http://localhost:8080`

The `server.key`, `server.csr`, `server.crt` and `server.pem` where created following:

```
openssl genrsa -out server.key 2048
openssl req -new -key server.key -out server.csr
openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
cat server.crt server.key | tee server.pem
```

Start up the backend and verify that the following is working:
```
http://localhost:8080
```

Start up the haproxy in the `haproxy` folder

```
haproxy -f haproxy.cfg
```

Access with HAProxy with http

http://localhost:8081

Access with HAProxy with https:

```
https://localhost:8443/

```

The SSL client was created certificate:

```
openssl genrsa -out client.key 2048
openssl req -new -key client.key -out client.csr
openssl x509 -req -days 365 -in client.csr -CA server.crt -CAkey server.key -set_serial 01 -out client.crt

# Convert Client Key to PKCS
openssl pkcs12 -export -clcerts -in client.crt -inkey client.key -out client.p12
```


